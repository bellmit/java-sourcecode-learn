package com.niimbot.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.niimbot.asset.framework.constant.DictConstant;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.asset.framework.model.CusUserDto;
import com.niimbot.asset.model.LoginUser;
import com.niimbot.asset.security.service.SysPermissionService;
import com.niimbot.asset.service.CusUserService;
import com.niimbot.jf.core.exception.category.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CusUserService userService;
    private final SysPermissionService sysPermissionService;

    @Autowired
    public UserDetailsServiceImpl(CusUserService userService, SysPermissionService sysPermissionService) {
        this.userService = userService;
        this.sysPermissionService = sysPermissionService;
    }

    @Override
    public UserDetails loadUserByUsername(String account) {
        // 获取账户信息
        CusUserDto cusUser = userService.selectUserByAccount(account);
        if (cusUser == null) {
            log.info("登录用户：{} 不存在.", account);
            throw new BusinessException(SystemResultCode.USER_LOGIN_ERROR);
        } else if (cusUser.getStatus().shortValue() == DictConstant.SYS_DISABLE) {
            log.info("登录用户：{} 已被停用.", account);
            throw new BusinessException(SystemResultCode.USER_ACCOUNT_FORBIDDEN);
        } else if (ObjectUtil.isNull(cusUser.getCompanyId())) {
            throw new BusinessException(SystemResultCode.USER_COMPANY_NOT_REGISTER);
        } /* else if (ObjectUtil.isNull(cusUser.getOrgId())) {
            throw new BusinessException(SystemResultCode.USER_HAS_NO_ROLE);
        }*/
        return createLoginUser(cusUser);
    }

    public UserDetails createLoginUser(CusUserDto cusUser) {
        return new LoginUser(cusUser, sysPermissionService.getMenuPermission(cusUser));
    }
}
