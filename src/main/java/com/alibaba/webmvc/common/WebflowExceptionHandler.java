package com.alibaba.webmvc.common;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zizhi.zhzzh
 *         Date: 7/24/14
 *         Time: 1:03 AM
 */
public class WebflowExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("success", false);
        model.put("errorMessage", ex.getMessage());
        if (ex instanceof WebflowException) {
            model.put("errorCode", ((WebflowException) ex).getCode());
            model.put("params", ((WebflowException) ex).getParams());
        }
        return new ModelAndView("exception", model);
    }

}