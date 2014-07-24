package com.alibaba.webmvc.extension;

import com.alibaba.webmvc.annotation.CSRFValidate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zizhi.zhzzh
 *         Date: 7/23/14
 *         Time: 5:01 PM
 */
public class CSRFInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            CSRFValidate csrfValidate = ((HandlerMethod) handler).getMethodAnnotation(CSRFValidate.class);
            return !(csrfValidate != null && csrfValidate.value()) || CSRFTokens.validateCSRFToken(request, false);
        }
        return CSRFTokens.validateCSRFToken(request);
    }

}