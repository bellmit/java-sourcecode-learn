package com.niimbot.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niimbot.asset.framework.model.CusUserDto;
import com.niimbot.asset.mapper.AsCusUserMapper;
import com.niimbot.asset.system.model.AsCusUser;
import com.niimbot.asset.system.model.AsCusUserExt;
import com.niimbot.asset.system.service.AsCusUserExtService;
import com.niimbot.asset.system.service.CusUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author jc
 * @since 2020/10/30
 */
@Service
public class CusUserServiceImpl extends ServiceImpl<AsCusUserMapper, AsCusUser> implements CusUserService {

    @Resource
    private AsCusUserExtService cusUserExtService;

    /**
     * 根据用户名查询用户信息
     *
     * @param account 登录名称
     * @return 用户信息
     */
    @Override
    public CusUserDto selectUserByAccount(String account) {
        return this.getBaseMapper().queryUserByAccount(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void loginAfterRecord(Long userId) {
        // 记录用户上一次登陆时间
        cusUserExtService.updateLastLoginTime(userId);
        // 记录用户当前登陆时间
        cusUserExtService.update(new UpdateWrapper<AsCusUserExt>().lambda()
                .set(AsCusUserExt::getCurrentLoginTime, new Date())
                .eq(AsCusUserExt::getId, userId));
        // 添加redis登录记录
    }

}
