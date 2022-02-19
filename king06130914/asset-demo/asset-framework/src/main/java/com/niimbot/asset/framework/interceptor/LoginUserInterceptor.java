package com.niimbot.asset.framework.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.niimbot.asset.framework.autoconfig.AssetConfig;
import com.niimbot.asset.framework.model.LoginUserDto;
import com.niimbot.asset.framework.service.AbstractTokenService;
import com.niimbot.asset.framework.web.LoginUserThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取用户信息的拦截器
 *
 * @author xie.wei
 * @Date 2020/11/18
 */
public class LoginUserInterceptor extends HandlerInterceptorAdapter {
    private final AbstractTokenService tokenService;
    private final AssetConfig config;

    public LoginUserInterceptor(AbstractTokenService tokenService, AssetConfig config) {
        this.tokenService = tokenService;
        this.config = config;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String trialFlag = request.getParameter(BaseConstant.TRIAL_LOGIN_FLAG);
        // 体验账号登录,切换体验账号数据源
//        if (ObjectUtil.isNotNull(trialFlag)) {
//            DynamicDataSourceContextHolder.push(BaseConstant.TRIAL_DATASOURCE_KEY);
//        }
        LoginUserDto loginUser = tokenService.getLoginUser(request);
        if (ObjectUtil.isNotNull(loginUser)) {
            LoginUserThreadLocal.set(loginUser);
            //切换体验账号数据源
//            if (ObjectUtil.isNotNull(loginUser.getCusUser())
//                    && StringUtils.equals(config.getAccount(), loginUser.getCusUser().getAccount())) {
//                DynamicDataSourceContextHolder.push(BaseConstant.TRIAL_DATASOURCE_KEY);
//            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserThreadLocal.remove();
//        DynamicDataSourceContextHolder.poll();
        super.afterCompletion(request, response, handler, ex);
    }
}
