package com.hanshow.wise.portal.eshop.resolver;

import com.hanshow.wise.portal.eshop.exception.LoginValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: CustomExceptionResolver</p>
 * <p>Description: 全局异常处理类</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.hanshow.com</p>
 * @author guolin
 * @version 1.0
 * @date 2018-06-09 14:10
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
    private static Logger logger = LoggerFactory.getLogger(CustomExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        ModelAndView modelAndView=new ModelAndView();
        response.setStatus(HttpStatus.OK.value()); //设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        response.setCharacterEncoding("UTF-8"); //避免乱码
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        //如果该 异常类型是系统 自定义的异常，直接取出异常信息。
       try{
           if (exception instanceof LoginValidateException) {
               LoginValidateException loginValidateException = (LoginValidateException) exception;
               response.getWriter().write(loginValidateException.getMessage());
           } else {
               response.getWriter().write(exception.getMessage());
           }
       }catch (Exception e){
            logger.error("全局异常处理失败",e);
       }
        return modelAndView;
    }
}
