package com.niimbot.asset.framework.web;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.niimbot.asset.framework.model.AdminUserDto;
import com.niimbot.asset.framework.model.CusUserDto;
import com.niimbot.asset.framework.model.LoginUserDto;

import java.util.Set;

/**
 * 获取登录用户信息
 *
 * @author xie.wei
 * @Date 2020/11/18
 */
public final class LoginUserThreadLocal {
    private LoginUserThreadLocal() {
    }

    private static ThreadLocal<LoginUserDto> userThread = new TransmittableThreadLocal<>();

    public static void set(LoginUserDto user) {
        userThread.set(user);
    }

    public static LoginUserDto get() {
        return userThread.get();
    }

    public static void remove() {
        userThread.remove();
    }

    /**
     * 获取当前用户对象
     *
     * @return CusUserDto
     */
    public static CusUserDto getCusUser() {
        return get().getCusUser();
    }

    /**
     * 获取当前用户所在公司
     *
     * @return companyId
     */
    public static Long getCompanyId() {
        CusUserDto cusUser = get().getCusUser();
        if (null == cusUser) {
            AdminUserDto adminUser = get().getAdminUser();
            if (adminUser != null) {
                return 0L;
            }
            return null;
        }
        return cusUser.getCompanyId();
    }

    /**
     * 获取当前用户所在组织
     *
     * @return orgId
     */
    public static Long getOrgId() {
        return get().getCusUser().getOrgId();
    }

    /**
     * 获取当前用户权限列表
     *
     * @return ModelDataScope
     */
    public static Set<String> getPermissions() {
        return get().getPermissions();
    }


    /**
     * 获取当前用户id
     *
     * @return userId
     */
    public static Long getCurrentUserId() {
        return get().getCusUser().getId();
    }

    /**
     * 获取当前用户id
     *
     * @return userId
     */
    public static Long getCurrentAdminUserId() {
        return get().getAdminUser().getId();
    }

    /**
     * 获取向前用户unionId
     * @return
     */
    public static String getCurrentUnionId() {
        return get().getCusUser().getUnionId();
    }

}
