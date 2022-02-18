package com.niimbot.asset.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.niimbot.asset.framework.autoconfig.AssetConfig;
import com.niimbot.asset.framework.constant.BaseConstant;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.asset.framework.model.LoginUserDto;
import com.niimbot.asset.framework.service.AbstractTokenService;
import com.niimbot.asset.framework.service.RedisService;
import com.niimbot.asset.framework.utils.ServletUtils;
import com.niimbot.asset.model.LoginUser;
import com.niimbot.asset.security.authentication.SmsAuthenticationToken;
import com.niimbot.asset.security.enmu.QrCodeEnum;
import com.niimbot.asset.security.utils.SecurityUtils;
import com.niimbot.asset.service.AbstractDefaultLoginService;
import com.niimbot.asset.service.CusLoginService;
import com.niimbot.asset.service.feign.CusUserFeignClient;
import com.niimbot.asset.service.feign.DataAuthorityFeignClient;
import com.niimbot.jf.core.exception.category.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "asset", name = "edition", havingValue = "local")
public class CusLoginServiceImpl extends AbstractDefaultLoginService implements CusLoginService {

    private final AbstractTokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final AssetConfig assetConfig;

    @Autowired
    public CusLoginServiceImpl(AbstractTokenService tokenService,
                               AuthenticationManager authenticationManager,
                               AssetConfig assetConfig,
                               DataAuthorityFeignClient dataAuthorityFeignClient,
                               CusUserFeignClient userFeignClient,
                               RedisService redisService) {
        super(dataAuthorityFeignClient, redisService, userFeignClient);
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.assetConfig = assetConfig;
    }

    /**
     * 登录验证
     *
     * @param account  用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @param terminal 设备
     * @return 结果
     */
    @Override
    public String loginByPwd(String account, String password, String code, String uuid, String terminal, String pushId) {
        // 本地部署需要验证码校验
        if (BaseConstant.EDITION_LOCAL.equalsIgnoreCase(assetConfig.getEdition())) {
            // 判断错误次数，是否需要使用验证码
            verifyLoginCanLogin(account);
            // 存在验证码校验验证码
            if (StrUtil.isNotEmpty(code) && StrUtil.isNotEmpty(uuid)) {
                // 从缓存读取验证码
                String verifyKey = BaseConstant.CAPTCHA_CODE_KEY + uuid;
                String captcha = String.valueOf(redisService.get(verifyKey));
                // 判断后台验证码是否为空
                if (captcha == null) {
                    throw new BusinessException(SystemResultCode.USER_VERIFY_EXPIRE);
                }
                // 判断校验码是否正确
                if (!code.equalsIgnoreCase(captcha)) {
                    throw new BusinessException(SystemResultCode.USER_VERIFY_ERROR);
                }
            }
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 调用登录
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(account, SecurityUtils.decryptPassword(password)));
        } catch (Exception e) {
            // 密码错误异常抛出处理，需要统计失败次数
            if (e instanceof BadCredentialsException) {
                // 本地部署版本需要验证码控制
                if (BaseConstant.EDITION_LOCAL.equalsIgnoreCase(assetConfig.getEdition())) {
                    String userErrorCount = BaseConstant.LOGIN_USER_ERROR_COUNT + account;
                    // 失败次数加一
                    Long incr = redisService.incr(userErrorCount, 1);
                    // 设置失效时间24小时
                    redisService.expire(userErrorCount, 24, TimeUnit.HOURS);
                    // 登录次数错误大于3次，需要验证码
                    if (incr > 3L) {
                        throw new BusinessException(SystemResultCode.USER_ACCOUNT_CAPTCHA);
                    } else {
                        throw new BusinessException(SystemResultCode.USER_LOGIN_ERROR);
                    }
                } else {
                    throw new BusinessException(SystemResultCode.USER_LOGIN_ERROR);
                }
            }
            if (e.getCause().getClass().isAssignableFrom(BusinessException.class)) {
                throw (BusinessException) e.getCause();
            } else {
                throw new BusinessException(SystemResultCode.USER_LOGIN_ERROR);
            }
        }
        // 获取登录信息
        LoginUser user = (LoginUser) authentication.getPrincipal();
        LoginUserDto loginUser = BeanUtil.copyProperties(user, LoginUserDto.class);
        // 注册推送
//        if (StringUtils.isNotBlank(pushId)) {
//            try {
//                messageBuilder.registerAddress(new JPushAddress(String.valueOf(user.getCusUser().getId()), pushId));
//            } catch (Exception e) {
//                log.error("推送服务id绑定失败", e);
//            }
//        }
        // 写入设备
        loginUser.setTerminal(terminal);
        // 登录成功删除错误次数记录
        delLoginCount(account);
        // 登录成功时，添加全局水平权限配置
//        List<ModelDataScope> dataScopes = buildDataScope(loginUser);
//        loginUser.setModelDataScopes(dataScopes);

        log.info("用户 {} 登录成功", loginUser.getCusUser().getAccount());
        super.loginAfterRecord(user.getCusUser().getId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 手机号登录验证
     *
     * @param mobile   用户名
     * @param sms      验证码
     * @param terminal 设备
     * @return 结果
     */
    @Override
    public String loginByMobile(String mobile, String sms, String terminal, String pushId) {
        // 用户验证
        Authentication authentication = null;
        try {
            // 调用手机验证码登录
            authentication = authenticationManager
                    .authenticate(new SmsAuthenticationToken(mobile, sms));
        } catch (Exception e) {
            if (e.getClass().isAssignableFrom(BusinessException.class)) {
                throw (BusinessException) e;
            } else {
                // 异常抛出处理
                throw new BusinessException(SystemResultCode.USER_LOGIN_ERROR);
            }
        }
        LoginUser user = (LoginUser) authentication.getPrincipal();
        LoginUserDto loginUser = BeanUtil.copyProperties(user, LoginUserDto.class);
        // 注册推送
//        if (StringUtils.isNotBlank(pushId)) {
//            try {
//                messageBuilder.registerAddress(new JPushAddress(String.valueOf(user.getCusUser().getId()), pushId));
//            } catch (Exception e) {
//                log.error("推送服务id绑定失败", e);
//            }
//        }
        // 写入设备
        loginUser.setTerminal(terminal);
        // 登录成功时，添加全局水平权限配置
//        List<ModelDataScope> dataScopes = buildDataScope(loginUser);
//        loginUser.setModelDataScopes(dataScopes);

        log.info("用户 {} 登录成功", loginUser.getCusUser().getAccount());
        super.loginAfterRecord(user.getCusUser().getId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 获取登录用户信息
     *
     * @return 登录用户
     */
    @Override
    public LoginUserDto getLoginUser() {
        return tokenService.getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 通过用户名查询用户
     *
     * @return 二维码
     */
    @Override
    public String getQrCode() {
        // 保存验证码信息
        String uuid = UUID.fastUUID().toString();
        String qrCodeKey = BaseConstant.QR_CODE_LOGIN + uuid;
        redisService.hSet(qrCodeKey, BaseConstant.QR_CODE_LOGIN_STATUS, QrCodeEnum.NOT_SCAN.getCode(), assetConfig.getQrCodeExpireTime());
        return uuid;
    }

    @Override
    public void kickOffLoginUser() {
        tokenService.delLoginUser(ServletUtils.getRequest());
    }

}
