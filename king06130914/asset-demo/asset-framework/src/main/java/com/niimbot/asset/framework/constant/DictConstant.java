package com.niimbot.asset.framework.constant;

/**
 * 字典常量 created by chen.y on 2021/3/5 14:43
 */
public interface DictConstant {

    default void check() {
        // ignore
    }

    /**
     * 通用是否 是
     */
    String SYS_YES = "1";
    /**
     * 通用是否 否
     */
    String SYS_NO = "0";

    /**
     * 通用启停 正常
     */
    Short SYS_ENABLE = 1;
    String SYS_ENABLE_TEXT = "启用";
    /**
     * 通用启停 禁用
     */
    Short SYS_DISABLE = 2;
    String SYS_DISABLE_TEXT = "禁用";

    /**
     * 菜单类型 —— 目录
     */
    String MENU_TYPE_CATALOG = "C";
    /**
     * 菜单类型 —— 菜单
     */
    String MENU_TYPE_MENU = "M";
    /**
     * 菜单类型 —— 按钮
     */
    String MENU_TYPE_BUTTON = "B";
    /**
     * 菜单类型 —— APP
     */
    String MENU_TYPE_APP = "A";

    /**
     * 待审批
     */
    Short WAIT_APPROVE = 1;
    /**
     * 已驳回
     */
    Short REJECTED = 2;
    /**
     * 已同意
     */
    Short APPROVED = 3;
    /**
     * 已撤销
     */
    Short REVOKED = 4;
    /**
     * 已转办
     */
    Short FORWARD = 5;
    /**
     * 日期范围————年
     */
    String DATE_RANGE_YEAR = "YEAR";
    /**
     * 日期范围————月
     */
    String DATE_RANGE_MON = "MON";
    /**
     * 日期范围————周
     */
    String DATE_RANGE_WEEK = "WEEK";
    /**
     * 日期范围————天
     */
    String DATE_RANGE_DAY = "DAY";

    /**
     * 采购方式
     */
    Short PURCHASE_MODE_APPLY = 1;
    Short PURCHASE_MODE_CUSTOM = 2;

    /**
     * 采购状态
     */
    Short PURCHASE_DOING = 1;
    Short PURCHASE_PARTIAL_STORAGE = 2;
    Short PURCHASE_FINISH = 3;
    Short PURCHASE_CANCEL = 4;

    /**
     * 资产来源
     */
    Short ASSET_ORIGIN_BUY = 1;
    String ASSET_ORIGIN_BUY_TEXT = "购入";

    Short MAINTAIN_STATUS_NORMAL = 1;
    Short MAINTAIN_STATUS_WAIT = 2;

    int REPAIR_STATUS_REPAIRING = 1;
    int REPAIR_STATUS_FINISH = 2;

    String FREQUENCY_UNIT_DAY = "DAY";
    String FREQUENCY_UNIT_MON = "MON";

    Integer IMPORT_TYPE_ASSET = 1;
    Integer IMPORT_TYPE_MATERIAL = 2;
    Integer IMPORT_TYPE_EMPLOYEE = 3;
    Integer IMPORT_TYPE_ORG = 4;
    Integer IMPORT_TYPE_ASSET_CATEGORY = 5;
    Integer IMPORT_TYPE_MATERIAL_CATEGORY = 6;
    Integer IMPORT_TYPE_AREA = 7;

    Integer GRANT_TYPE_WAIT = 1;
    Integer GRANT_TYPE_PART = 2;
    Integer GRANT_TYPE_COMPLETE = 3;

    Integer CHANGE_TYPE_EDIT = 1;
    Integer CHANGE_TYPE_DELETE = 2;

    /**
     * 线索同步状态
     */
    int CRM_CLUE_SYNC_STATUS_WAIT = 0;
    int CRM_CLUE_SYNC_STATUS_SUCC = 1;
    int CRM_CLUE_SYNC_STATUS_FAIL = 2;

    /**
     * 客户同步状态
     */
    int CRM_CUS_SYNC_STATUS_WAIT = 0;
    int CRM_CUS_SYNC_STATUS_SUCC = 1;
    int CRM_CUS_SYNC_STATUS_FAIL = 2;

    /**
     * CRM同步错误日志类型
     */
    int CRM_SYNC_ERROR_TYPE_CLUE = 1;
    int CRM_SYNC_ERROR_TYPE_CUS = 2;
    int CRM_SYNC_ERROR_TYPE_BUS = 3;

    /**
     * 转移所管理资产
     */
    String TRANS_EMP_MANAGE_ASSET = "assetUnderManagement";
    /**
     * 转移使用资产
     */
    String TRANS_EMP_USE_ASSET = "assetUser";
    /**
     * 转移盘点任务
     */
    String TRANS_EMP_INVENTORY_ASSET = "assetReportTask";
    /**
     * 转移资产上报任务
     */
    String TRANS_EMP_REPORT_TASK = "inventoryTask";
    /**
     * 转移审批任务
     */
    String TRANS_EMP_APPROVAL_TASK = "approvalTask";

    /**
     * 订单明细-商品类型
     */
    int PRODUCT_TYPE_VERSION = 1;
    int PRODUCT_TYPE_ASSET_PACKAGE = 2;

    /**
     * 打印分隔符 1-英文冒号（:），2-中文冒号（：），3-中划线（-），4-空格（ ），5-无分隔符
     */
    int PRINT_DELIMITER_ENGLISH_COLON = 1;
    int PRINT_DELIMITER_CHINESE_COLON = 2;
    int PRINT_DELIMITER_LINE_THROUGH = 3;
    int PRINT_DELIMITER_SPACE = 4;
    int PRINT_DELIMITER_NO_SEPARATOR = 5;

    /**
     * 打印类型
     */
    short PRINT_TYPE_ASSET = 1;
    short PRINT_TYPE_MATERIAL = 2;
}
