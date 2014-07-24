/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.webmvc.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zizhi.zhzzh
 *         Date: 7/23/14
 *         Time: 6:40 PM
 */
public class CSRFTokens {
    public final static String CSRF_TOKEN_KEY = "X-CSRF-Token";
    public final static String AUTHENTICITY_KEY = "authenticity_token";
    public final static Logger LOGGER = LoggerFactory.getLogger(CSRFTokens.class);
    private CSRFTokens(){}

    public static boolean validateCSRFToken(HttpServletRequest request, boolean allowNull){
        String csrfToken = request.getHeader(CSRF_TOKEN_KEY);
        if (csrfToken != null) {
            String currentCsrfToken = currentCsrfToken(request);
            LOGGER.info("CSRF TOKEN Check: Request({}) <-> Session({})", csrfToken, currentCsrfToken);
            return csrfToken.equals(currentCsrfToken);
        }
        return !allowNull && validateAuthenticityToken(request);
    }

    public static boolean validateCSRFToken(HttpServletRequest request){
        return validateCSRFToken(request, true);
    }

    public static boolean validateAuthenticityToken(HttpServletRequest request){
        String csrfToken = request.getParameter(AUTHENTICITY_KEY);
        if (csrfToken != null) {
            String currentCsrfToken = currentCsrfToken(request);
            LOGGER.info("CSRF TOKEN Check: Request({}) <-> Session({})", csrfToken, currentCsrfToken);
            return csrfToken.equals(currentCsrfToken);
        }
        return false;
    }

    public static String currentCsrfToken(HttpServletRequest request){
        return getUniqueToken(request);
    }

    public static String getUniqueToken(HttpServletRequest request) {
        HttpSession session =request.getSession();
        String tokenOfSession = StringUtils.trimWhitespace(((String) session.getAttribute(CSRF_TOKEN_KEY)));

        if (tokenOfSession == null || tokenOfSession.isEmpty() || isExpire(tokenOfSession)) {
            tokenOfSession = newToken();
            session.setAttribute(CSRF_TOKEN_KEY, tokenOfSession);
        }

        return hash(tokenOfSession);
    }

    private static String hash(String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(value.getBytes());
            byte[] bytes = sha256.digest();
            return hex(bytes);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("Can't find the algorithm for MessageDigest SHA-256.");
            return value;
        }
    }


    public static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String hexString;
        for (byte b : bytes) {
            hexString = Integer.toHexString(b & 0xFF);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    private static boolean isExpire(String tokenOfSession) {
        long longValue = 0L;
        try {
            longValue = Long.parseLong(tokenOfSession);
        } catch (NumberFormatException e) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("parse long error! tokenOfSession=" + tokenOfSession);
            }
            return true;
        }
        long nowValue = System.currentTimeMillis();
        long tmp = nowValue - longValue;
        //当前时间小于token的时间,过期
        if (tmp < 0L) {
            return true;
        }
        //当前时间大于token 24小时,过期
        return tmp > 24L * 60 * 60 * 1000;
    }

    private static String newToken() {
        long longValue = System.currentTimeMillis();
        return Long.toString(longValue);
    }
}
