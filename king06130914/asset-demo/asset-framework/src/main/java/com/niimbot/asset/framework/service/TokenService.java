package com.niimbot.asset.framework.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.niimbot.asset.framework.autoconfig.TokenConfig;
import com.niimbot.asset.framework.constant.BaseConstant;
import com.niimbot.asset.framework.model.LoginUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenService extends AbstractTokenService {
    public TokenService(TokenConfig tokenConfig, RedisService redisService) {
        super(tokenConfig, redisService);
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
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(BaseConstant.LOGIN_USER_KEY);
            String mobile = (String) claims.get(BaseConstant.LOGIN_USER_PHONE_KEY);

            String userKey = getTokenKey(uuid, mobile);
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
        Claims body = new DefaultClaims();
        try {
            body = Jwts.parser()
                    .setSigningKey(this.tokenConfig.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("token parse error ==> {}", token);
        }
        return body;

    }

    @Override
    public String createToken(LoginUserDto loginUser) {
        String token = IdUtil.fastUUID();
        loginUser.setToken(token);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>(2);
        claims.put(BaseConstant.LOGIN_USER_KEY, token);
        claims.put(BaseConstant.LOGIN_USER_PHONE_KEY, loginUser.getCusUser().getMobile());
        return createToken(claims);
    }

    @Override
    protected String getTokenKey(LoginUserDto loginUser) {
        return BaseConstant.LOGIN_TOKEN_KEY + loginUser.getCusUser().getMobile() + ":" + loginUser.getToken();
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, this.tokenConfig.getSecret()).compact();
    }

    /**
     * 拼接token
     *
     * @return token
     */
    private String getTokenKey(String uuid, String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return BaseConstant.LOGIN_TOKEN_KEY + uuid;
        }
        return BaseConstant.LOGIN_TOKEN_KEY + mobile + ":" + uuid;
    }
}
