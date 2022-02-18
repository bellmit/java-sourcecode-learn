package com.niimbot.asset.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.asset.framework.model.LoginUserDto;
import com.niimbot.asset.framework.service.AbstractTokenService;
import com.niimbot.asset.framework.utils.ResultUtils;
import com.niimbot.asset.framework.utils.ServletUtils;
import com.niimbot.asset.security.utils.SecurityUtils;
import com.niimbot.jf.core.result.FailureResult;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final AbstractTokenService tokenService;

    public JwtAuthenticationTokenFilter(AbstractTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        LoginUserDto loginUser = tokenService.getLoginUser(request);
        if (loginUser != null && SecurityUtils.getAuthentication() == null) {
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
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
