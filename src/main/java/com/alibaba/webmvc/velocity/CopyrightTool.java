package com.alibaba.webmvc.velocity;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
/**
 * Created with IntelliJ IDEA.
 *
 * @author zizhi.zhzzh
 *         Date: 7/23/14
 *         Time: 7:59 PM
 */
public class CopyrightTool {

    private HttpServletRequest request;

    public String getText(){
        return "This is just Free + ." + request;
    }


    public void init(Object obj) {
        if ((obj instanceof ServletContext)) {
           //
        }
    }



}
