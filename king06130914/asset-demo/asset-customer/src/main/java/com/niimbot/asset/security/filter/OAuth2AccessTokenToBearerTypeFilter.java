package com.niimbot.asset.security.filter;

import com.niimbot.asset.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XieKong
 * @date 2021/1/14 13:39
 */
@Slf4j
public class OAuth2AccessTokenToBearerTypeFilter extends OncePerRequestFilter {
    private final static String AUTHORIZATION = "authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final HttpHeaderMapRequest mapRequest = new HttpHeaderMapRequest(request);
        String token = request.getHeader(AUTHORIZATION);
        if (StringUtils.isNotBlank(token)
                && !(token.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
            mapRequest.addHeader(AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE + " " + token);
        }
        filterChain.doFilter(mapRequest, response);
    }

    static class HttpHeaderMapRequest extends HttpServletRequestWrapper {
        private Map<String, String> headerMap = new ConcurrentHashMap<>();


        public HttpHeaderMapRequest(HttpServletRequest request) {
            super(request);
        }

        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            if (name.toLowerCase().equals(AUTHORIZATION)) {
                return headerMap.getOrDefault(AUTHORIZATION, "");
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (name.toLowerCase().equals(AUTHORIZATION)) {
                return Collections.enumeration(Collections.singletonList(headerMap.getOrDefault(AUTHORIZATION, "")));
            }
            return super.getHeaders(name);
        }
    }

}
