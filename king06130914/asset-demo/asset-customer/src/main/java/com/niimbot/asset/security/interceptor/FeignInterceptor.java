package com.niimbot.asset.security.interceptor;

import com.niimbot.asset.framework.utils.ThreadLocalUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * created by chen.y on 2020/10/21 8:39
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String, String> headerMap = ThreadLocalUtil.get();
        for (String key : headerMap.keySet()) {
            requestTemplate.header(key, headerMap.get(key));
            // 体验账号登录设置参数 【待处理】
            /*if (key.equalsIgnoreCase(BaseConstant.TRIAL_LOGIN_FLAG)) {
                requestTemplate.query(BaseConstant.TRIAL_LOGIN_FLAG, "true");
            }*/
        }
    }
}
