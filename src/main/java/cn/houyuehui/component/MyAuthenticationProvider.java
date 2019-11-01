package cn.houyuehui.component;

import cn.houyuehui.authentication.MyWebAuthenticationDetails;
import cn.houyuehui.common.exception.VerificationCodeException;
import cn.houyuehui.service.impl.MyUserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义认证提供者
 * @Author: jsonhou
 * @Date: 2019/11/1 9:19
 */
@Component
public class MyAuthenticationProvider  extends DaoAuthenticationProvider {

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public MyAuthenticationProvider(MyUserDetailServiceImpl myUserDetailService){
        this.setUserDetailsService(myUserDetailService);
        this.setPasswordEncoder(passwordEncoder());
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        MyWebAuthenticationDetails myWebAuthenticationDetails= (MyWebAuthenticationDetails) authentication.getDetails();
        if (!myWebAuthenticationDetails.getImageCodeIsRight()){
            throw new VerificationCodeException();
        }
        //实现图形验证码的逻辑
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
