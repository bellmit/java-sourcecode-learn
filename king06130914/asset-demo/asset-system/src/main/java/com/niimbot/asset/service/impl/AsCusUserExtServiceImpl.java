package com.niimbot.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niimbot.asset.mapper.AsCusUserExtMapper;
import com.niimbot.asset.system.model.AsCusUserExt;
import com.niimbot.asset.system.service.AsCusUserExtService;
import org.springframework.stereotype.Service;

@Service
public class AsCusUserExtServiceImpl extends ServiceImpl<AsCusUserExtMapper, AsCusUserExt>
        implements AsCusUserExtService {

    @Override
    public void updateLastLoginTime(Long userId) {
        this.getBaseMapper().updateLastLoginTime(userId);
    }

}
