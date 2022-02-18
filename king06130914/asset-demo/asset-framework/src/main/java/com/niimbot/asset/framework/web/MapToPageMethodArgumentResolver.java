package com.niimbot.asset.framework.web;

import com.niimbot.asset.framework.annotation.MapPage;
import com.niimbot.asset.framework.constant.BaseConstant;
import com.niimbot.asset.framework.utils.Query;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义map转page分页参数
 * curPage
 * limit
 * 用于对controller返回值是list或IPage的对象类型转换
 *
 * @author xie.wei
 * @Date 2020/11/10
 */
public class MapToPageMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MapPage.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put(BaseConstant.PAGE_NUM, webRequest.getParameter(BaseConstant.PAGE_NUM));
        params.put(BaseConstant.PAGE_SIZE, webRequest.getParameter(BaseConstant.PAGE_SIZE));
        params.put(BaseConstant.ORDER_FIELD, webRequest.getParameter(BaseConstant.ORDER_FIELD));
        params.put(BaseConstant.ORDER, webRequest.getParameter(BaseConstant.ORDER));
        return new Query<>().getPage(params);
    }
}
