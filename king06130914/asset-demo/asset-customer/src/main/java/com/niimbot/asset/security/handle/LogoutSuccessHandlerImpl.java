package com.niimbot.asset.security.handle;

import com.alibaba.fastjson.JSON;
import com.niimbot.asset.framework.service.AbstractTokenService;
import com.niimbot.asset.framework.utils.ServletUtils;
import com.niimbot.jf.core.result.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 */
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    private final AbstractTokenService tokenService;

    public LogoutSuccessHandlerImpl(AbstractTokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 退出处理
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        tokenService.delLoginUser(request);
        ServletUtils.renderString(response, JSON.toJSONString(Result.ofSuccess("退出成功")));
    }
}
