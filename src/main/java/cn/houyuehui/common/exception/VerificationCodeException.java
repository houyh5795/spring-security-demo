package cn.houyuehui.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: jsonhou
 * @Date: 2019/10/31 15:26
 */
public class VerificationCodeException extends AuthenticationException {
    public  VerificationCodeException(){
        super("图形验证码校验失败");
    }
}
