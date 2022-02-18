package com.niimbot.asset.framework.web;

import com.alibaba.fastjson.JSONObject;
import com.niimbot.asset.framework.annotation.RequestJson;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * created by chen.y on 2021/1/22 10:53
 */
public class RequestJsonHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestJson.class);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestJson requestJson = parameter.getParameterAnnotation(RequestJson.class);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null || requestJson == null) {
            return null;
        }
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        int rd;
        while ((rd = reader.read(buf)) != -1) {
            sb.append(buf, 0, rd);
        }
        JSONObject jsonObject = parseObject(sb.toString());
        String value = requestJson.value();
        return jsonObject.get(value);
    }
}
