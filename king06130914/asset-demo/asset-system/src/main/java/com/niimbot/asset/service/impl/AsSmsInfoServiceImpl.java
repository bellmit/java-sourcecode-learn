package com.niimbot.asset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niimbot.asset.mapper.AsSmsInfoMapper;
import com.niimbot.asset.system.model.AsSmsInfo;
import com.niimbot.asset.system.service.AsSmsInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信发送信息记录表 服务实现类
 * </p>
 *
 * @author dk
 * @since 2021-03-26
 */
@Service
public class AsSmsInfoServiceImpl extends ServiceImpl<AsSmsInfoMapper, AsSmsInfo> implements AsSmsInfoService {

}
