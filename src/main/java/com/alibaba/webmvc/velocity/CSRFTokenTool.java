package com.alibaba.webmvc.velocity;

import com.alibaba.webmvc.extension.CSRFTokens;
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
