package com.niimbot.asset.framework.web.advice;

import com.niimbot.asset.framework.annotation.RepeatSubmit;
import com.niimbot.asset.framework.autoconfig.AssetConfig;
import com.niimbot.asset.framework.service.RedisService;
import com.niimbot.jf.core.exception.category.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

@Slf4j
@Order(1)
@ControllerAdvice
public class RepeatableAdvice implements RequestBodyAdvice {

    @Autowired
    private RedisService redisService;

    @Autowired
    private AssetConfig assetConfig;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = methodParameter.getMethod();
        if (Objects.isNull(method)) {
            return false;
        }
        return method.isAnnotationPresent(RepeatSubmit.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            return new RepeatableHttpInputMessage(inputMessage, redisService, assetConfig.getRepeatTime());
        } catch (BusinessException bus) {
            throw bus;
        } catch (Exception e) {
            log.error("防重复提交异常");
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
