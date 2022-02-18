package com.niimbot.asset.security.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 扫码登陆鉴权 Provider
 * created by chen.y on 2020/11/2 20:27
 */
@Component
public class QrAuthenticationProvider implements AuthenticationProvider {

    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户Id
        QrAuthenticationToken authenticationToken = (QrAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
        QrAuthenticationToken authenticationResult = new QrAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断 authentication 是不是 QrAuthenticationProvider 的子类或子接口
        return QrAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
