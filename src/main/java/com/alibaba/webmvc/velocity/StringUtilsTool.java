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
