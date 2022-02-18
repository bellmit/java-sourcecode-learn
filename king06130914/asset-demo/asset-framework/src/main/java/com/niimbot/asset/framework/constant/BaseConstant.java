package com.niimbot.asset.framework.constant;


public class BaseConstant {
    /** UTF-8 字符集 */
    public static final String UTF8 = "UTF-8";
    /** GBK 字符集 */
    public static final String GBK = "GBK";
    /** http请求 */
    public static final String HTTP = "http://";
    /**
     * https请求
     */
    public static final String HTTPS = "https://";
    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";
    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";
    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * SAAS
     */
    public static final String EDITION_SAAS = "saas";
    /**
     * LOCAL
     */
    public static final String EDITION_LOCAL = "local";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";
    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;
    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";
    /**
     * 字典管理 redis key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";
     /** 二维码 redis key */
    public static final String QR_CODE_LOGIN = "qr_code_login:";
    /** 二维码状态 redis key */
    public static final String QR_CODE_LOGIN_STATUS = "status";
    /** 二维码对应用户 redis key */
    public static final String QR_CODE_LOGIN_MOBILE = "mobile";
    /** SSO 二维码对应用户 redis key */
    public static final String QR_CODE_LOGIN_UNION_ID = "unionId";
    /** 用户id和名称关联 redis key */
    public static final String USER_ID_REF_NAME = "user_id_ref_name:";
    /** 体验账号登录标识 */
    public static final String TRIAL_LOGIN_FLAG = "login/trial";
    /**
     * 体验账号数据源key值
     */
    public static final String TRIAL_DATASOURCE_KEY = "TY";

    /**
     * 令牌
     */
    public static final String TOKEN = "token";
    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";
    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_PHONE_KEY = "login_user_phone_key";
    /**
     * 令牌前缀 失败次数
     */
    public static final String LOGIN_USER_ERROR_COUNT = "login_user_error:";
    /**
     * 注册前缀 失败次数
     */
    public static final String REGISTER_USER_ERROR_COUNT = "register_user_error:";
    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";
    /**
     * 超级管理员角色名
     */
    public final static String ADMIN_ROLE = "admin";
    /**
     * 资产管理员角色名
     */
    public final static String ASSET_ADMIN_ROLE = "assetAdmin";

    /**
     * 超级管理员角色
     */
    public final static Integer ADMIN_USER_TYPE = 1;
    /**
     * 资产管理员角色
     */
    public final static Integer ASSET_ADMIN_USER_TYPE = 2;

    /**
     * 员工角色名
     */
    public final static String COMMON_ROLE = "common";
    /**
     * 用户账号最大值
     */
    public final static String USER_ACCOUNT_MAX = "user_account_max";
    /**
     * 用户账号前缀配置,用于配置账号,环境变量配置前缀
     */
    public final static String USER_ACCOUNT_PREFIX = "asset.user.account.prefix";
    /**
     * 资产 联系关于我们
     */
    public final static String ASSET_ABOUT = "asset.about";


    /**
     * 公司全局导入锁
     */
    public static final String COMPANY_IMPORT = "company_import";

    /**
     * 关键字查询
     */
    public static final String KEY_WORD = "kw";
    /**
     * 当前页码
     */
    public static final String PAGE_NUM = "pageNum";
    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /** 升序 */
    public static final String ASC = "asc";

    /** 默认重置密码 */
    public static final String RESET_PASSWORD = "123456";
}
