package com.hanshow.wise.portal.eshop.exception;


/**
 * <p>Title: LoginValidateException</p>
 * <p>Description: securityKey自定义错误类 只可以使用RuntimeException,否则无法获取正确获取错误类</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.hanshow.com</p>
 * @author guolin
 * @version 1.0
 * @date 2018-06-09 14:04
 */
public class LoginValidateException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginValidateException(String message) {
        super();
        this.message = message;
    }
}
