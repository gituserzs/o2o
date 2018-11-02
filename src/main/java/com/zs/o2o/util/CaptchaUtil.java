package com.zs.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CaptchaUtil {
    public static boolean checkCaptcha(HttpServletRequest request){
        String captchaExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String captcha = HttpServletRequestUtil.getString(request, "captcha");
        captchaExpected = captchaExpected.toLowerCase();
        captcha = captcha.toLowerCase();
        if (captcha == null || !captcha.equals(captchaExpected)){
            return false;
        }
        return true;
    }
}
