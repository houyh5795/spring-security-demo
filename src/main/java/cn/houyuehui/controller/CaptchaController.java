package cn.houyuehui.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author: jsonhou
 * @Date: 2019/10/31 15:18
 */
@Controller
public class CaptchaController {
    @Autowired
    private Producer captchaProducer;

    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        String captchaText=captchaProducer.createText();
        request.getSession().setAttribute("captcha",captchaText);
        BufferedImage bufferedImage=captchaProducer.createImage(captchaText);
        ServletOutputStream out=response.getOutputStream();
        ImageIO.write(bufferedImage,"jpg",out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
