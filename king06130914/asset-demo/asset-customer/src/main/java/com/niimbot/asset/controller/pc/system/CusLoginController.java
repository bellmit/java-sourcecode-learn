package com.niimbot.asset.controller.pc.system;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.niimbot.asset.framework.autoconfig.AssetConfig;
import com.niimbot.asset.framework.constant.AssetConstant;
import com.niimbot.asset.model.LoginBody;
import com.niimbot.asset.model.MobileBody;
import com.niimbot.asset.security.utils.SecurityUtils;
import com.niimbot.asset.service.CusLoginService;
import com.niimbot.jf.core.component.annotation.ResultController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统登录模块
 */
@Api(tags = {"用户登录接口"})
@ResultController
@RequestMapping("api/pc")
public class CusLoginController {

    @Autowired
    private CusLoginService loginService;

    @Autowired
    private AssetConfig assetConfig;

    /**
     * 账号密码验证码登录
     *
     * @param loginBody 登录信息
     * @return token
     */
    @PostMapping("/login")
    @ApiOperation(value = "账号密码登录", notes = "使用账号密码登录")
    public Map<String, Object> login(@Validated @RequestBody LoginBody loginBody) {
        Map<String, Object> map = new HashMap<>();
        String token = loginService.loginByPwd(loginBody.getAccount(),
                loginBody.getPassword(),
                loginBody.getCode(),
                loginBody.getUuid(),
                AssetConstant.TERMINAL_PC,
                null);
        map.put(OAuth2AccessToken.ACCESS_TOKEN, token);
        return map;
    }

    /**
     * 手机短信登录
     *
     * @param mobileBody 登录信息
     * @return token
     */
    @PostMapping("/login/sms")
    @ApiOperation(value = "短信验证码登录", notes = "使用短信验证码登录")
    public Map<String, Object> loginByPhone(@Validated @RequestBody MobileBody mobileBody) {
        Map<String, Object> map = new HashMap<>();
        String token = loginService.loginByMobile(mobileBody.getMobile(), mobileBody.getSmsCode(), AssetConstant.TERMINAL_PC,
                null);
        map.put(OAuth2AccessToken.ACCESS_TOKEN, token);
        return map;
    }


    @GetMapping("/login/rsa/encrypt")
    @ApiOperation(value = "使用RSA加密密码", notes = "仅测试使用")
    public String rsaEncode(@RequestParam("password") String password) {
        RSA rsa = SecureUtil.rsa(null, assetConfig.getRsaPublicKey());
        byte[] encrypt = rsa.encrypt(password, CharsetUtil.UTF_8, KeyType.PublicKey);
        return Base64.encode(encrypt);
    }

    @GetMapping("/login/rsa/decrypt")
    @ApiOperation(value = "使用RSA解密密码", notes = "仅测试使用")
    public String rsaDecode(@RequestParam("password") String password) {
        return SecurityUtils.decryptPassword(password);
    }

}
