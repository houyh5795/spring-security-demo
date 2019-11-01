package cn.houyuehui.authentication;

import cn.houyuehui.common.MyConst;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: jsonhou
 * @Date: 2019/11/1 9:34
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {
    private boolean imageCodeIsRight;

    public boolean getImageCodeIsRight() {
        return this.imageCodeIsRight;
    }

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String imageCode = request.getParameter(MyConst.CAPTCHA);
        HttpSession session = request.getSession();
        String savedImageCode = (String) session.getAttribute(MyConst.CAPTCHA);
        if (!StringUtils.isEmpty(savedImageCode)) {
            session.removeAttribute(MyConst.CAPTCHA);
        }
        if (!StringUtils.isEmpty(imageCode) && imageCode.equals(savedImageCode)) {
            this.imageCodeIsRight = true;
        }

    }
}
