package com.niimbot.asset.security.authentication;


import com.niimbot.asset.service.impl.SmsUserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 短信登陆鉴权 Provider
 * created by chen.y on 2020/11/2 20:27
 */
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    /**
     * 自定义用户认证逻辑
     */
    private final SmsUserDetailsServiceImpl userDetailsService;

    public SmsAuthenticationProvider(SmsUserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取手机号码
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String smsCode = (String) authenticationToken.getCredentials();
        // 校验短信验证码
        checkSmsCode(mobile, smsCode);
        // smsCodeService.checkSmsCode(mobile,smsCode);
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断 authentication 是不是 SmsAuthenticationToken 的子类或子接口
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 校验手机号和验证码
     *
     * @param mobile  手机号
     * @param smsCode 验证码
     *                created by chen.y in 2020/11/3 16:07
     */
    private void checkSmsCode(String mobile, String smsCode) {
        //if (smsCode == null) {
        //throw new BadCredentialsException("未检测到申请验证码");
        //}

        /*String applyMobile = (String) smsCode.get("mobile");
        int code = (int) smsCode.get("code");

        if(!applyMobile.equals(mobile)) {
            throw new BadCredentialsException("申请的手机号码与登录手机号码不一致");
        }
        if(code != Integer.parseInt(inputCode)) {
            throw new BadCredentialsException("验证码错误");
        }*/
    }
}
