package com.niimbot.asset.framework.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niimbot.asset.framework.autoconfig.TokenConfig;
import com.niimbot.asset.framework.constant.BaseConstant;
import com.niimbot.asset.framework.model.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class SsoTokenService extends AbstractTokenService implements InitializingBean {
    private static final String UNION_ID_KEY = "user_name";
    private final String verifierKey;
    private SignatureVerifier verifier;

    public SsoTokenService(TokenConfig tokenConfig, RedisService redisService, String verifierKey) {
        super(tokenConfig, redisService);
        this.verifierKey = verifierKey;
    }

    @Override
    public LoginUserDto getLoginUser(HttpServletRequest request) {
        if (request == null) {
            request = getRequest();
        }
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StrUtil.isNotEmpty(token)) {
            Map<String, Object> claims = parseToken(token);
            if (!claims.containsKey(UNION_ID_KEY)) {
                return null;
            }
            // 解析对应的权限以及用户信息
            String unionId = (String) claims.get(UNION_ID_KEY);
            String userKey = getTokenKey(unionId, token);
            if (!redisService.hasKey(userKey).booleanValue()) {
                return null;
            }
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(redisService.get(userKey)));
            if (ObjectUtil.isEmpty(jsonObject)) {
                return null;
            }
            jsonObject.remove("@type");
            return jsonObject.toJavaObject(LoginUserDto.class);
        }
        return null;
    }

    @Override
    protected Map<String, Object> parseToken(String token) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Jwt jwt = JwtHelper.decodeAndVerify(token, verifier);
            String claimsStr = jwt.getClaims();
            return mapper.readValue(claimsStr, Map.class);
        } catch (Exception e) {
            log.debug("token parse error ==> {}", token);
        }
        return Collections.emptyMap();
    }

    @Override
    public String createToken(LoginUserDto loginUser) {
        refreshToken(loginUser);
        return loginUser.getToken();
    }

    @Override
    protected String getTokenKey(LoginUserDto loginUser) {
        return BaseConstant.LOGIN_TOKEN_KEY + loginUser.getCusUser().getUnionId() + ":" + loginUser.getToken();
    }

    /**
     * 拼接token
     *
     * @return token
     */
    private String getTokenKey(String unionId, String token) {
        return BaseConstant.LOGIN_TOKEN_KEY + unionId + ":" + token;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (verifier != null) {
            // Assume signer also set independently if needed
            return;
        }
        SignatureVerifier verifier = new MacSigner(verifierKey);
        try {
            verifier = new RsaVerifier(verifierKey);
        } catch (Exception e) {
            log.warn("Unable to create an RSA verifier from verifierKey (ignoreable if using MAC)");
        }
        this.verifier = verifier;
    }
}
