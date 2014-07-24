/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.webmvc.extension;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zizhi.zhzzh
 *         Date: 7/24/14
 *         Time: 11:38 PM
 */
public final class JSONs {
    private JSONs() {}

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public static final String DEFAULT_CONTENT_TYPE = "application/json";
    public static final String DATA = "data";
    public static final String SUCCESS = "success";

    @SuppressWarnings("unchecked")
    public static void writeTo(OutputStream out, Object value, Charset charset, boolean prettyPrint){
        Map result = new HashMap();
        if (value instanceof Map) {
            Map target = (Map)value;
            if (target.containsKey(SUCCESS)) {
                result.putAll(target);
            } else if(target.containsKey(DATA)) {
                result.put(SUCCESS, true);
                result.put(DATA, target.get(DATA));
            } else {
                result.put(SUCCESS, true);
                if (target.size() == 1) {
                    result.put(DATA, Iterables.get(target.values(), 0));
                } else {
                    result.put(DATA, target);
                }
            }
        } else {
            result.put(DATA, value);
            result.put(SUCCESS, true);
        }
        if (prettyPrint) {
            JSON.writeJSONStringTo(result, new OutputStreamWriter(out, charset), SerializerFeature.PrettyFormat);
        } else {
            JSON.writeJSONStringTo(result, new OutputStreamWriter(out, charset));
        }
    }

}