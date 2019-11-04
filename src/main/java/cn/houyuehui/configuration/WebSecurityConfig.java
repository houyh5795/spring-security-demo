package cn.houyuehui.configuration;

import cn.houyuehui.authentication.MyInvalidSessionStrategy;
import cn.houyuehui.common.handler.MyAuthenticationFailureHandler;
import cn.houyuehui.component.MyAuthenticationProvider;
import cn.houyuehui.repository.JdbcTokenReposityImpl;
import cn.houyuehui.service.impl.MyUserDetailServiceImpl;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * @Author: jsonhou
 * @Date: 2019/10/29 10:15
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public Producer captcha() {
        //验证码
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "150");
        properties.setProperty("kaptcha.image.height", "50");
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> myWebAuthenticationDetails;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;
    @Autowired
    private JdbcTokenReposityImpl jdbcTokenReposity;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //指定登录成功的处理方式
        http.authorizeRequests().antMatchers(HttpMethod.GET,
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/images/**",
                "/*.jpg",
                "/swagger-resources/**",
                "/v2/api-docs/**")
                .permitAll()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .authenticationDetailsSource(myWebAuthenticationDetails)
                .loginPage("/myLogin.html")
                .loginProcessingUrl("/auth/form")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("{\"error_code\":\"0\",\"message\":\"欢迎登录系统\"}");
                })
                .failureHandler(new MyAuthenticationFailureHandler()
                )
                .permitAll()
                .and()
                .rememberMe().userDetailsService(myUserDetailService)
                .tokenRepository(jdbcTokenReposity)
                .and()
                .logout()
                .logoutUrl("/myLogout")
                .logoutSuccessUrl("/")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("{\"error_code\":\"0\",\"message\":\"注销系统成功\"}");
                }).invalidateHttpSession(true)
                .deleteCookies("cookies1")
                .addLogoutHandler((httpServletRequest, httpServletResponse, authentication) -> {
                }).and()
               // .sessionManagement()
                //.invalidSessionStrategy(new MyInvalidSessionStrategy())
               // .and()
                .csrf().disable()
                .sessionManagement()
                .maximumSessions(1);
        //简单的增加过滤器的方式
        // http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
    }

    /*
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password(passwordEncoder().encode("123")).roles("USER").build());
        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("123")).roles("ADMIN").build());
        return manager;
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        JdbcUserDetailsManager manager=new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        if (!manager.userExists("user")) {
            manager.createUser(User.withUsername("user").password(passwordEncoder().encode("123")).roles("USER").build());
        }
        if (!manager.userExists("admin")) {
            manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("123")).roles("ADMIN").build());
        }
        return manager;
    }
*/
}
