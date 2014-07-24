/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.webmvc.common;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

/**
 * 注意不要滥用，用于处理非正常流程外的业务.
 *
 * @author zizhi.zhzzh
 *         Date: 7/19/14
 *         Time: 11:31 AM
 */
public class WebflowException extends RuntimeException {

    private final String code;
    private final Map<String, Object> params = new HashMap<String, Object>();

    public WebflowException(String code) {
        super();
        this.code = code;
    }

    public WebflowException(String code, String message) {
        super(message);
        this.code = code;
    }

    public WebflowException addParam(String name, Object value) {
        params.put(name, value);
        return this;
    }

    public Object getParam(String name) {
        return params.get(name);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public <T> Optional<T> getParam(String name, Class<T> clazz) {
        Object obj = params.get(name);
        if (obj != null) {
            if (clazz.isAssignableFrom(obj.getClass())) {
                return Optional.of(clazz.cast(obj));
            } else if(Number.class.isAssignableFrom(clazz) && Number.class.isAssignableFrom(obj.getClass())) {
                T value = cast((Number)obj, clazz);
                if (value != null) {
                    return Optional.of(value);
                }
            }
        }
        return Optional.absent();
    }

    public <T> T getParamValue(String name, Class<T> clazz) {
        Optional<T> optional = getParam(name, clazz);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }


    public String getString(String name) {
        return getParamValue(name, String.class);
    }

    public String getCode() {
        return code;
    }

    /**
     * 优化异常性能，禁止使用异常栈
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    @SuppressWarnings("unchecked")
    private static <T> T cast(Number number, Class<T> type) {
        if (Long.class.isAssignableFrom(type)) {
            return (T)Long.valueOf(number.longValue());
        } else if (Integer.class.isAssignableFrom(type)) {
            return (T)Integer.valueOf(number.intValue());
        } else if (Short.class.isAssignableFrom(type)) {
            return (T)Short.valueOf(number.shortValue());
        } else if (Byte.class.isAssignableFrom(type)) {
            return (T)Byte.valueOf(number.byteValue());
        } else if (Float.class.isAssignableFrom(type)) {
            return (T)Float.valueOf(number.floatValue());
        } else if (Double.class.isAssignableFrom(type)) {
            return (T)Double.valueOf(number.doubleValue());
        }
        return null;
    }

}
