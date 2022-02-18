package com.niimbot.asset.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.asset.framework.model.LoginUserDto;
import com.niimbot.asset.framework.service.AbstractTokenService;
import com.niimbot.asset.framework.utils.ResultUtils;
import com.niimbot.asset.framework.utils.ServletUtils;
import com.niimbot.asset.security.utils.SecurityUtils;
import com.niimbot.jf.core.result.FailureResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 */
@Slf4j
public class JwtOAuth2AuthenticationTokenFilter extends OncePerRequestFilter {
    private final AbstractTokenService tokenService;

    public JwtOAuth2AuthenticationTokenFilter(AbstractTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Authentication authentication = SecurityUtils.getAuthentication();
        LoginUserDto loginUser = tokenService.getLoginUser(request);
        if (authentication != null && loginUser != null && loginUser.getCusUser() != null
                && authentication.getPrincipal().equals(loginUser.getCusUser().getUnionId())) {
            // 角色信息变化
            if (loginUser.getCusUser() != null && loginUser.getCusUser().isRoleChanged()) {
                FailureResult result = ResultUtils.parseFailure(SystemResultCode.PERMISSION_CHANGED, request, response);
                // 使用Jackson序列化
                ObjectMapper mapper = new ObjectMapper();
                ServletUtils.renderString(response, mapper.writeValueAsString(result));
                return;
            }

            // 判断缓存中当前用户是否最新
            if (tokenService.getKickout() && !tokenService.verifyCusUserSingleLogin(loginUser)) {
                FailureResult result = ResultUtils.parseFailure(SystemResultCode.USER_ACCOUNT_KICKOUT, request, response);
                // 使用Jackson序列化
                ObjectMapper mapper = new ObjectMapper();
                ServletUtils.renderString(response, mapper.writeValueAsString(result));
                return;
            }
            // 自动续签
            tokenService.verifyToken(loginUser);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        chain.doFilter(request, response);
    }
}
