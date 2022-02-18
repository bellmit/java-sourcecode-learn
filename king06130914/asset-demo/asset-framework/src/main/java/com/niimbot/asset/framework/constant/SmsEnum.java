package com.niimbot.asset.framework.constant;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 短信模板
 *
 * @author dk
 */
public enum SmsEnum implements IEnum<Integer> {

    SMS_TEMPLATE_01(154680, 1, "您的验证码：{1}，{2}分钟内输入有效，请立即输入。"), // 验证码
    SMS_TEMPLATE_02(155275, 2, "尊敬的精臣资产管理平台用户，您有收到一个新的盘点任务，盘点单名称：{1}，请尽早完成盘点。"),// 盘点
    SMS_TEMPLATE_03(171025, 3, "精臣资产管理平台提醒您，您的账户已开通，请及时登录并修改密码。"), // 注册开通帐号
    SMS_TEMPLATE_04(171029, 3, "已为您开通精臣资产管理平台账号，更多信息请联系您的管理员。"),
    SMS_TEMPLATE_05(171031, 3, "尊敬的精臣资产管理平台用户，您的账号已被重新开启，可直接登录使用。"),
    SMS_TEMPLATE_06(171033, 3, "尊敬的精臣资产管理平台用户，您的账号已被停止使用， 请联系您的管理员。"),
    SMS_TEMPLATE_07(681599, 3, "尊敬的用户，您的系统使用期限将于{1}到期，如需续费或升级， 请联系在线客服或拨打客服热线：400-860-8800转2"),
    SMS_TEMPLATE_08(380216, 3, "尊敬的精臣资产管理平台用户，您的使用版本已升级为:{1}，敬请使用。"),
    SMS_TEMPLATE_09(380195, 3, "尊敬的精臣资产管理平台用户，您的账号：{1}已被注销， 需重新注册才可使用。"),
    SMS_TEMPLATE_10(462877, 3, "尊敬的精臣资产管理平台用户，您的账号：{1}已格式化完成。"),
    SMS_TEMPLATE_11(475038, 3, "尊敬的精臣资产管理平台用户，您的账号：{1}已重置密码， 新的密码是：{2}，请尽快登录修改。"),
    SMS_TEMPLATE_12(882978, 3, "精臣固定资产提醒您，贵司固定资产软件服务于{1}到期。因系统数据持续存储成本较大，将于{2}清空。如需续费请联系在线客服或拨打客服电话4008608800转2。");

    private final int templateId;
    private final int type;
    private final String content;

    SmsEnum(int templateId, int type, String content) {
        this.templateId = templateId;
        this.type = type;
        this.content = content;
    }

    @Override
    public Integer getValue() {
        return templateId;
    }
}
