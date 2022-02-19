package com.niimbot.asset.framework.autoconfig;

import com.niimbot.asset.framework.constant.BaseConstant;
import com.niimbot.asset.framework.interceptor.LoginUserInterceptor;
import com.niimbot.asset.framework.service.AbstractTokenService;
import com.niimbot.asset.framework.service.RedisService;
import com.niimbot.asset.framework.service.TokenService;
import com.niimbot.asset.framework.web.LoginUserDtoMethodArgumentResolver;
import com.niimbot.asset.framework.web.MapToBeanMethodArgumentResolver;
import com.niimbot.asset.framework.web.MapToPageMethodArgumentResolver;
import com.niimbot.asset.framework.web.RequestJsonHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 通用配置
 */
@Configuration
@ConditionalOnProperty(prefix = "asset", name = "edition", havingValue = "local")
public class ResourcesConfig implements WebMvcConfigurer {

    @Autowired
    private AssetConfig assetConfig;
    @Autowired
    private TokenConfig tokenConfig;
    @Autowired
    private RedisService redisService;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 本地文件上传路径 */
        registry.addResourceHandler(BaseConstant.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + assetConfig.getUploadPath() + "/");

        /** Knife4j配置 */
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginUserInterceptor(tokenService(), assetConfig))
                .addPathPatterns("/server/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MapToBeanMethodArgumentResolver());
        resolvers.add(new MapToPageMethodArgumentResolver());
        resolvers.add(new RequestJsonHandlerMethodArgumentResolver());
        resolvers.add(new LoginUserDtoMethodArgumentResolver(tokenService()));
    }

    @Bean
    public AbstractTokenService tokenService() {
        return new TokenService(tokenConfig, redisService);
    }
}