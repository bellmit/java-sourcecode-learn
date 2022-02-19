package com.niimbot.asset.framework.constant;

import java.util.Calendar;

public class RedisConstant {

    /**
     * 公司全局导入锁
     */
    public static String companyImportKey(String module, Long companyId) {
        return "company_import:" + companyId + ":" + module;
    }

    /**
     * 公司全局导入锁
     */
    public static String companyImportAll(Long companyId) {
        return "company_import:" + companyId;
    }

    /**
     * 【消息通道】-区域字典
     */
    public static final String AREA_DICT_CHANNEL = "area_dict_channel";

    public static String areaDictKey() {
        return BaseConstant.SYS_DICT_KEY + "area";
    }

    /**
     * 【消息通道】-分类字典
     */
    public static final String CATEGORY_DICT_CHANNEL = "category_dict_channel";

    public static String categoryDictKey() {
        return BaseConstant.SYS_DICT_KEY + "category";
    }

    /**
     * 【消息通道】-耗材分类字典
     */
    public static final String MATERIAL_CATEGORY_DICT_CHANNEL = "material_category_dict_channel";

    public static String materialCategoryDictKey() {
        return BaseConstant.SYS_DICT_KEY + "materialCategory";
    }

    /**
     * 【消息通道】-耗材仓库管理
     */
    public static final String MATERIAL_REPOSITORY_DICT_CHANNEL = "material_repository_dict_channel";

    public static String materialRepositoryDickey(){
        return BaseConstant.SYS_DICT_KEY + "materialRepository";
    }

    /**
     * 【消息通道】-组织字典
     */
    public static final String ORG_DICT_CHANNEL = "org_dict_channel";

    public static String orgDictKey() {
        return BaseConstant.SYS_DICT_KEY + "org";
    }

    /**
     * 【消息通道】-员工字典
     */
    public static final String EMPLOYEE_DICT_CHANNEL = "employee_dict_channel";

    public static String employeeDictKey() {
        return BaseConstant.SYS_DICT_KEY + "employee";
    }

    /**
     * 【消息通道】- 资产状态字典
     */
    public static String assetStatusDictKey() {
        return BaseConstant.SYS_DICT_KEY + "asset_status";
    }

    /**
     * 【消息通道】- 发送业务消息
     */
    public static final String SEND_MESSAGE_CHANNEL = "send_message_channel";

    public static final String SEND_MESSAGE_QUEUE = "send_message_queue";

    /**
     * 待采购清单
     */
    public static String purchaseWaitListLockKey(Long companyId) {
        return "purchase:wait_list:" + companyId;
    }

    /**
     * 采购单入库
     */
    public static String purchaseStoreLockKey(Long companyId) {
        return "purchase:store:" + companyId;
    }

    /**
     * 采购单入库
     */
    public static String unionIdKey(String phone) {
        return "uid:" + phone;
    }

    /**
     * 维修单
     */
    public static String repairOrderLockKey(Long repairOrderId) {
        return "repair:order:" + repairOrderId;
    }

    /**
     * 耗材入库
     */
    public static String materialRkOrderLockKey(Long orderId) {
        return "material:rk:order:" + orderId;
    }

    /**
     * 版本有效期key
     */
    public static final String VERSION_EXPIRY_TIME = "version_expiry_time";

    /**
     * 重建到期hash
     */
    public static final String RESET_EXPIRY_TIME = "reset_version_expiry_time";

    /**
     * 兑换码
     */
    public static String recordCodetKey(Long companyId) {
        return "record:" + companyId;
    }

    /**
     * 判断当前时间距离第二天凌晨的时间
     * @param unit
     * @return 返回值单位为[unit] 默认返回秒
     */
    public static Long getSecondsNextEarlyMorning(String unit) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if("H".equals(unit)){
            return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000/60/60;
        } else if("M".equals(unit)){
            return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000/60;
        }
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    /**
     * 处理已支付订单
     */
    public static String saleOrderPaidLockKey(String orderNo) {
        return "saleOrder:paid:" + orderNo;
    }

    /**
     * 【消息通道】- 版本等级更新
     */
    public static final String PM_VERSION_UPDATE_CHANNEL = "pm_version_update_channel";

    /**
     * 用户资产查询视图
     */
    public static String assetQueryView(Long userId) {
        return "asset_query_view:" + userId;
    }

    /**
     * 商品库缓存
     */
    public static final String PRODUCT_LIBRARY = "product_library";

    /**
     * 企业信息缓存
     */
    public static final String COMPANY_INFO = "company_info";
}

