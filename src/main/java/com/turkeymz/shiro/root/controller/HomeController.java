package com.turkeymz.shiro.root.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.turkeymz.shiro.core.bean.UserInfo;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: cmxu
 * @Description: 控制层.
 * @Date： create in 16:02 2018/3/3
 * @Modified By:
 */
@Controller
public class HomeController {

    @Autowired
    private Producer producer;

    @RequestMapping(value = {"/", "/index"})
    public String index(Map<String, Object> map) {
        //获取用户信息,shiro -> SecurityUtil.getSubject()获取userinfo
        Subject subject = SecurityUtils.getSubject();
        UserInfo userInfo = (UserInfo) subject.getPrincipal();
        map.put("userInfo", userInfo);
        return "/index";
    }

    //只能使用get方式进行请求. post:在点击登录的时候进行使用.
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "/login";
    }


    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(HttpServletRequest request, Map<String, Object> map) {

        //登录失败从request对象中可以获取shiro处理异常的细信息.
        //shiroLoginFailure.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                msg = "账号不存在";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                msg = "密码不正确";
            } else if (ExcessiveAttemptsException.class.getName().equals(exception)) {
                msg = "密码输入次数过多";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                msg = "验证码输入错误";
            } else {
                msg = "else -->" + exception;
            }
        }
        map.put("msg", msg);
        return "/login";
    }

    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        response.setDateHeader("Expires", 0);//失效时间
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");//缓存配置
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");//返回类型为图片
        //获取seesion
        HttpSession session = request.getSession();
        //获取验证码
        String captcha = producer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, captcha);

        ServletOutputStream out = null;
        try {
            //获取output
            out = response.getOutputStream();
            //获取Image对象
            BufferedImage image = producer.createImage(captcha);
            ImageIO.write(image, "jpg", out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
