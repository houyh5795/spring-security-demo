package cn.houyuehui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jsonhou
 * @Date: 2019/10/30 7:52
 */
@RestController
@RequestMapping("/user/api")
public class UserController {
    @GetMapping("hello")
    public String hello(){
        return "hello,user";
    }
}
