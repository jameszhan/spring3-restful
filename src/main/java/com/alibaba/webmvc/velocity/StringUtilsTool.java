/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.webmvc.velocity;

import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zizhi.zhzzh
 *         Date: 7/23/14
 *         Time: 11:03 PM
 */
public class StringUtilsTool {

    public String defaultIfBlank(String... values) {
        if (values == null) {
            return "";
        }
        for (String value : values) {
            if (StringUtils.hasText(value)) {
                return StringUtils.trimWhitespace(value);
            }
        }
        return "";
    }

    public String trim(String value){
        return StringUtils.trimWhitespace(value);
    }

}
