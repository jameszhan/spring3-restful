/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.webmvc.common;

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