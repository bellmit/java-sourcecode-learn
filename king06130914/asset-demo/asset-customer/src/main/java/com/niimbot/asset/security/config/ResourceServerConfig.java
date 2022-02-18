package com.niimbot.asset.security.config;

import com.niimbot.asset.framework.service.AbstractTokenService;
import com.niimbot.asset.security.filter.JwtOAuth2AuthenticationTokenFilter;
import com.niimbot.asset.security.filter.OAuth2AccessTokenToBearerTypeFilter;
import com.niimbot.asset.security.handle.AuthenticationEntryPointImpl;
import com.niimbot.asset.security.handle.LogoutSuccessHandlerImpl;
import com.niimbot.asset.security.handle.SecurityAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.CorsFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author XieKong
 * @date 2021/1/14 11:38
 */
@Slf4j
@Configuration
@EnableResourceServer
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(prefix = "asset", name = "edition", havingValue = "saas")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.application.name}")
    private String resourceId;
    @Autowired
    private AbstractTokenService tokenService;
    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;
    /**
     * 拒绝访问拦截
     */
    @Autowired
    private SecurityAccessDeniedHandler securityAccessDeniedHandler;
    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 配置不需要安全拦截url
                .antMatchers(AccessConfig.ACCESS).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.svg",
                        "/**/*.png"
                ).permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.svg",
                        "/**/*.png"
                ).permitAll()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/common/download/**").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                .anyRequest().authenticated()
                .and().csrf().disable();
        http.logout().logoutUrl("/logout").logoutSuccessHandler(new LogoutSuccessHandlerImpl(tokenService));
        // 添加CORS filter
        http.addFilterBefore(new OAuth2AccessTokenToBearerTypeFilter(), AbstractPreAuthenticatedProcessingFilter.class);
        http.addFilterAfter(new JwtOAuth2AuthenticationTokenFilter(tokenService), AbstractPreAuthenticatedProcessingFilter.class);

        http.addFilterBefore(corsFilter, OAuth2AccessTokenToBearerTypeFilter.class);
        http.addFilterBefore(corsFilter, LogoutFilter.class);

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore())
                .resourceId(resourceId)
                // 认证失败统一处理
                .authenticationEntryPoint(unauthorizedHandler)
                .accessDeniedHandler(securityAccessDeniedHandler)
                .stateless(true);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        ClassPathResource resource = new ClassPathResource("keys/public.cert");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String publicKey = bufferedReader.lines().collect(Collectors.joining("\n"));
            converter.setVerifierKey(publicKey);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return converter;
    }
}
