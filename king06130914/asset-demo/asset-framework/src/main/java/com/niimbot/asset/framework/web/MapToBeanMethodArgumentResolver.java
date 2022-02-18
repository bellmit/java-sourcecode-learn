package com.niimbot.asset.framework.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.niimbot.asset.framework.annotation.MapEntity;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 自定义map转bean参数解析器
 * ---
 * 用于对controller返回值是list或IPage的对象类型转换
 * ---
 *
 * @author xie.wei
 * @Date 2020/11/10
 */
public class MapToBeanMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(MapEntity.class)) {
            Method method = parameter.getMethod();
            Class<?> returnType = method.getReturnType();
            if (returnType.isAssignableFrom(List.class) || returnType.isAssignableFrom(IPage.class)) {
                ParameterizedTypeImpl genericReturnType = (ParameterizedTypeImpl) method.getGenericReturnType();
                Class<?> rawType = genericReturnType.getRawType();
                Class<?> parameterType = parameter.getParameterType();
                return rawType == parameterType;
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        ParameterizedTypeImpl genericReturnType = (ParameterizedTypeImpl) parameter.getMethod().getGenericReturnType();
        return BeanUtil.mapToBean(parameterMap, genericReturnType.getRawType(), true, CopyOptions.create());
    }
}
