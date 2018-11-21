package com.hanshow.wise.portal.eshop.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 权限拦截器
 */
public class SecurityKeyInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {
    static Logger logger = LoggerFactory.getLogger(SecurityKeyInterceptor.class);
    @Autowired(required = false)
    public static List<String> excludeUrls;// 不需要拦截的资源

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    /**
     * 完成页面的render后调用
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
        try {
            String requestUri = request.getRequestURI();
            String contextPath = request.getContextPath();
            String url = requestUri.substring(contextPath.length());
            HandlerMethod method = (HandlerMethod) object;
        } catch (Exception e) {
            logger.error("afterCompletion", e);
        }
    }

    /**
     * 在调用controller具体方法后拦截
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
		try {
			HandlerMethod method=(HandlerMethod) object;
		} catch (Exception e) {
            logger.error("postHandle", e);
		}
    }

    /**
     * 在调用controller具体方法前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            HandlerMethod method=(HandlerMethod) handler;
        } catch (Exception e) {
            logger.error("preHandle", e);
        }
       return true;
    }



    public String getRequestClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
