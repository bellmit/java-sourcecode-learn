package com.niimbot.asset.security.config;

/**
 * @author XieKong
 * @date 2021/1/15 11:14
 */
public class AccessConfig {
    public static final String[] ACCESS = {"/actuator/**",
            "/api/common/captchaImage",
            "/api/common/smsCode/**",
            "/api/common/emailCode/**",
            "/api/common/checkSmsCode",
            "/api/common/checkCode",
            "/api/common/industry/tree",
            "/api/common/register",
            "/api/pc/login",
            "/api/pc/login/sms",
            "/api/pc/login/qrCode",
            "/api/pc/login/qrCode/check",
            "/api/pc/login/rsa/encrypt",
            "/api/pc/login/rsa/decrypt",
            "/api/app/login",
            "/api/app/login/sms",
            "/api/app/login/password",
            "/api/pc/login/social",
            "/api/pc/login/banding",
            "/api/app/login/social",
            "/api/app/login/banding",
            "/api/common/file/{id}",
            "/api/common/employee/scanInvite/**",
            "/api/common/password",
            "/api/common/password/verifyMobile",
            "/api/common/userCenter/userFeedback",
            "/api/app/version/management/latestVersion/**",
            "/api/common/sale/order/notify",
            "/api/webSocket/**",
            "/api/common/lastPrivacyAgreement/**",
            "/api/common/thirdparty/callback/**",
            "/material/**", "/print/**", "/tags/**", "/toolbox/**", "/navigation/**"
    };
}
