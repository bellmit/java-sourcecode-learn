package com.niimbot.asset.framework.interceptor;

import com.niimbot.asset.framework.autoconfig.TokenConfig;
import com.niimbot.asset.framework.utils.ThreadLocalUtil;

import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by chen.y on 2021/4/8 11:49
 */
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenConfig tokenConfig;

    public TokenInterceptor(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截请求，设置header到ThreadLocal中
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            if (name.equalsIgnoreCase(tokenConfig.getHeader())) {
                ThreadLocalUtil.set(name, request.getHeader(name));
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }

}
