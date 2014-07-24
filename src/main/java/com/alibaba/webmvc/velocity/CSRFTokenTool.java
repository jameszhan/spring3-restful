/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.webmvc.velocity;

import com.alibaba.webmvc.common.CSRFTokens;
import org.apache.velocity.tools.view.ViewContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zizhi.zhzzh
 *         Date: 7/23/14
 *         Time: 8:35 PM
 */
public class CSRFTokenTool {

    private HttpServletRequest request;

    public void init(Object obj){
        if (obj instanceof ViewContext) {
            this.request = ((ViewContext) obj).getRequest();
        }
    }

    public String getUniqueToken(){
        return CSRFTokens.getUniqueToken(request);
    }
}
