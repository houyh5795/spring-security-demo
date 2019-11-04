package cn.houyuehui.authentication;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: jsonhou
 * @Date: 2019/11/2 15:57
 */
public class MyInvalidSessionStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write("session 无效");
    }
}
