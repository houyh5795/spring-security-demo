package cn.houyuehui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringDemoApplication {
    @GetMapping("/")
    public String hello(){
        return "hello,spring security";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(){
        return "登录成功";
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringDemoApplication.class, args);
    }

}
