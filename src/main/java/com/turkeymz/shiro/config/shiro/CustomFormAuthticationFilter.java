package com.turkeymz.shiro.config.shiro;

import com.google.code.kaptcha.Constants;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: cmxu
 * @Description: 验证码拦截器,用于验证码校验
 * @Date： create in 0:40 2018/3/4
 * @Modified By:
 */
public class CustomFormAuthticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        if(isLoginSubmission(request, response)){//是登录请求提交.
            String received = request.getParameter("kaptchaValidate");//获取前端输入的参数
            if(received != null){
                //从sessoin获取进行校验.
                HttpServletRequest request2 = (HttpServletRequest)request;
                String captcha = (String)request2.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
                boolean b = received.equalsIgnoreCase(captcha);
                if(!b){//校验失败
                    request.setAttribute("shiroLoginFailure","kaptchaValidateFailed");
                    return true;
                }
            }
        }
        return super.onAccessDenied(request, response);
    }
}
