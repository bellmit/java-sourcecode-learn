package com.niimbot.asset.service.impl;

import com.niimbot.asset.framework.constant.SmsEnum;
import com.niimbot.asset.framework.utils.NumberUtils;
import com.niimbot.asset.framework.utils.SmsUtils;
import com.niimbot.asset.system.model.AsSmsInfo;
import com.niimbot.asset.system.service.AsSmsInfoService;
import com.niimbot.asset.system.service.SmsCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dk
 */
@Service
public class SsoSmsCodeServiceImpl implements SmsCodeService {

    @Resource
    private SmsUtils smsUtil;

    @Resource
    private AsSmsInfoService smsInfoService;

    @Override
    public Boolean sendSmsCode(String mobile) {
        try {
            String code = NumberUtils.generateCode(4);
            long minutes = 5;
            Boolean aBoolean = smsUtil.sendSms(mobile, new String[]{code, String.valueOf(minutes)}, SmsEnum.SMS_TEMPLATE_01.getValue());
            if (aBoolean) {
                smsInfoService.save(new AsSmsInfo().setAddress(mobile).setCode(code).setCreateBy(2L));
            }

            return aBoolean;
        } catch (Exception e) {
            return false;
        }
    }

}
