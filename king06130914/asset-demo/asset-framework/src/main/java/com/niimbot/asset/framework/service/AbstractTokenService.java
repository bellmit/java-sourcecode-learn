package com.niimbot.asset.framework.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.niimbot.asset.framework.autoconfig.TokenConfig;
import com.niimbot.asset.framework.constant.AssetConstant;
import com.niimbot.asset.framework.constant.BaseConstant;
import com.niimbot.asset.framework.model.LoginUserDto;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTokenService {
    protected final TokenConfig tokenConfig;
    protected final RedisService redisService;
    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    protected static final long MILLIS_HOUR = 60 * MILLIS_MINUTE;
    protected static final Long MILLIS_HOURS_TEN = 10 * MILLIS_HOUR;

    public AbstractTokenService(TokenConfig tokenConfig,
                                RedisService redisService) {
        this.tokenConfig = tokenConfig;
        this.redisService = redisService;
    }

    /**
     * 获取当担登录用户
     * @param request
     * @return LoginUserDto
     */
    public abstract LoginUserDto getLoginUser(HttpServletRequest request);

    /**
     * 获取 CusUser 企业id
     * @param request
     * @return 企业id
     */
    protected final Long getCusUserCompanyId(HttpServletRequest request) {
        LoginUserDto loginUser = getLoginUser(request);
        if (loginUser != null) {
            return loginUser.getCusUser().getCompanyId();
        } else {
            return null;
        }
    }

    /**
     * 解析token
     * @param token
     * @return map
     */
    protected abstract Map<String, Object> parseToken(String token);

    /**
     * 删除当前用户
     * @param request
     */
    public final void delLoginUser(HttpServletRequest request) {
        LoginUserDto loginUser = getLoginUser(request);
        if (ObjectUtil.isNotNull(loginUser)) {
            String userKey = getTokenKey(loginUser);
            redisService.del(userKey);
        }
    }

    /**
     * 删除当前用户
     * @param loginUser
     */
    public final void delLoginUser(LoginUserDto loginUser) {
        String userKey = getTokenKey(loginUser);
        redisService.del(userKey);
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public abstract String createToken(LoginUserDto loginUser);

    /**
     * 获取token的redis key
     * @param loginUser
     * @return
     */
    protected abstract String getTokenKey(LoginUserDto loginUser);

    public final void verifyToken(LoginUserDto loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_HOURS_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public final void refreshToken(LoginUserDto loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        int expireTime = tokenConfig.getExpireTime();
        if (AssetConstant.TERMINAL_APP.equalsIgnoreCase(loginUser.getTerminal())) {
            expireTime = tokenConfig.getAppExpireTime();
        }
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_HOUR);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser);
        redisService.set(userKey, loginUser, expireTime, TimeUnit.HOURS);
        // 建立uuid和用户id的联系，提供单人登录功能
        if (AssetConstant.TERMINAL_PC.equals(loginUser.getTerminal())) {
            redisService.hSet(BaseConstant.LOGIN_USER_KEY, Convert.toStr(loginUser.getCusUser().getId()), loginUser.getToken());
        }
    }

    /**
     * 判断cusUser单人登录
     *
     * @param loginUser 令牌
     * @return 用户名
     */
    public final boolean verifyCusUserSingleLogin(LoginUserDto loginUser) {
        // 判断PC是否最新token
        if (AssetConstant.TERMINAL_PC.equals(loginUser.getTerminal())) {
            String uuidToken = (String) redisService.hGet(BaseConstant.LOGIN_USER_KEY, Convert.toStr(loginUser.getCusUser().getId()));
            return loginUser.getToken().equals(uuidToken);
        } else {
            return true;
        }
    }

    /**
     * 获取请求token
     *
     * @return token
     */
    protected final String getToken(HttpServletRequest request) {
        String token = request.getHeader(tokenConfig.getHeader());
        if (StrUtil.isNotEmpty(token) && token.startsWith(BaseConstant.TOKEN_PREFIX)) {
            token = token.replace(BaseConstant.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 获取request
     * @return
     */
    protected final HttpServletRequest getRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }

    public boolean getKickout() {
        return this.tokenConfig.isKickout();
    }
}
