package com.niimbot.asset.framework.web;

import com.niimbot.asset.framework.model.LoginUserDto;
import com.niimbot.asset.framework.service.AbstractTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * handlerMethod上获取LoginUserDto
 *
 * @author xie.wei
 * @Date 2020/11/18
 */
@Slf4j
public class LoginUserDtoMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final AbstractTokenService tokenService;

    public LoginUserDtoMethodArgumentResolver(AbstractTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(LoginUserDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return tokenService.getLoginUser(request);
    }
}
