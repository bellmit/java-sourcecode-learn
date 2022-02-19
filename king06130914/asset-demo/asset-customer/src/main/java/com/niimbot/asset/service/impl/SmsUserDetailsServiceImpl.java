package com.niimbot.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.niimbot.asset.framework.constant.DictConstant;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.asset.framework.model.CusUserDto;
import com.niimbot.asset.model.LoginUser;
import com.niimbot.asset.security.service.SysPermissionService;
import com.niimbot.asset.service.CusUserService;
import com.niimbot.asset.service.feign.CusEmployeeFeignClient;
import com.niimbot.jf.core.exception.category.BusinessException;
import com.niimbot.system.CusEmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SmsUserDetailsServiceImpl {

    private final CusUserService userService;

    private final CusEmployeeFeignClient employeeFeignClient;

    private final SysPermissionService sysPermissionService;

    @Autowired
    public SmsUserDetailsServiceImpl(CusUserService userService,
                                     CusEmployeeFeignClient employeeFeignClient,
                                     SysPermissionService sysPermissionService) {
        this.userService = userService;
        this.employeeFeignClient = employeeFeignClient;
        this.sysPermissionService = sysPermissionService;
    }

    public UserDetails loadUserByUsername(String account) {
        CusUserDto cusUser = checkMobile(account);
        return createLoginUser(cusUser);
    }

    public CusUserDto checkMobile(String mobile) {
        // 获取账户信息
        CusUserDto cusUser = userService.selectUserByAccount(mobile);
        // 账户信息不存在
        if (cusUser == null) {
            CusEmployeeDto emp = employeeFeignClient.checkMobile(mobile);
            // 判断是否存在用户信息
            if (ObjectUtil.isNotEmpty(emp)) {
                throw new BusinessException(SystemResultCode.USER_REGISTER_WITHOUT_ACCOUNT);
            } else {
                log.info("登录用户：{} 不存在.", mobile);
                throw new BusinessException(SystemResultCode.USER_PHONE_NOT_EXIST);
            }
        } else if (cusUser.getStatus().shortValue() == DictConstant.SYS_DISABLE) {
            log.info("登录用户：{} 已被停用.", mobile);
            throw new BusinessException(SystemResultCode.USER_ACCOUNT_FORBIDDEN);
        } else if (ObjectUtil.isNull(cusUser.getCompanyId())) {
            throw new BusinessException(SystemResultCode.USER_COMPANY_NOT_REGISTER);
        }/* else if (ObjectUtil.isNull(cusUser.getOrgId())) {
            throw new BusinessException(SystemResultCode.USER_HAS_NO_ROLE);
        }*/
        return cusUser;
    }

    public UserDetails createLoginUser(CusUserDto cusUser) {
        return new LoginUser(cusUser, sysPermissionService.getMenuPermission(cusUser));
    }
}
