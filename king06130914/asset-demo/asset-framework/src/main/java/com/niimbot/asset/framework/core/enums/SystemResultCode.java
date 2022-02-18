package com.niimbot.asset.framework.core.enums;

import com.niimbot.jf.core.exception.error.details.ResultCode;

import org.springframework.http.HttpStatus;

public enum SystemResultCode implements ResultCode {


    /* 参数错误：500 */
    INTERNAL_SERVER_ERROR(500, "系统异常，请稍后重试") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    PARAM_IS_BLANK(10002, "参数为空") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    PARAM_NOT_COMPLETE(10004, "参数缺失") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    PARAM_REPEAT_SUBMIT(10005, "不允许重复提交，请稍后再试") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    PARAM_EXISTS(10006, "%s%s已存在，请重新输入") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    SQL_INJECT(10006, "包含非法字符，请重新输入") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    OPT_SAVE_FAIL(10007, "新增失败，请稍后再试") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    OPT_EDIT_FAIL(10007, "更新失败，请稍后再试") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    OPT_DELETE_FAIL(10007, "删除失败，请稍后再试") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_UPLOAD_INVALID(10008, "文件上传参数不合法，请检查") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_UPLOAD_DISABLED(10009, "文件上传功能未开启，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_UPLOAD_MULTI_NOT_ALLOWED(10010, "多文件上传不支持，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_UPLOAD_FILENAME_INVALID(10011, "文件命名不合法，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_UPLOAD_FILENAME_LENGTH_EXCEED(10012, "文件名过长，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_UPLOAD_FILE_SIZE_EXCEED(10013, "文件过大，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_UPLOAD_FILE_EXTENSION_INVALID(10014, "不支持该文件类型上传，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_UPLOAD_FILE_COUNT_EXCEED(10015, "上传文件数过多，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_DOWNLOAD_NOT_FOUND(10016, "下载文件不存在") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_NAME_ENCODED_FAIL(10017, "文件名称编码失败") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    FILE_DOWNLOAD_MULTI_EXCEED(10018, "批量下载文件数过多，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ASSET_ABOUT_UNSET(10019, "关于我们未配置，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ASSET_NOT_ALLOW_DELETE(10019, "维修、审核资产不能删除") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    LAST_RECORD_DELETE_FAIL(10020, "不能删除") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    EXPORT_ERROR(10021, "导出失败") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    IMPORT_ERROR(10022, "导入异常") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    IMPORT_FILE_NULL(10023, "没有导入文件") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },

    /* 系统模块：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.FORBIDDEN;
        }
    },
    USER_LOGIN_ERROR(20002, "账号或密码错误，请重新输入") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.FORBIDDEN;
        }
    },
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    // 需要验证码
    USER_ACCOUNT_CAPTCHA(20004, "账号或密码错误，请重新输入") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    // 需要验证码
    USER_ACCOUNT_LOCKED(20005, "您当前账号登录失败次数已达上限，账号已锁定，您仍可通过验证码登录，或重置密码") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    USER_ACCOUNT_KICKOUT(20006, "您的账号在其它设备登录，如果这不是您的操作，请及时修改您的登录密码") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    // 需要验证码
    USER_NOT_EXIST(20007, "用户不存在") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    USER_PHONE_NOT_EXIST(20008, "手机号未在平台注册") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.FORBIDDEN;
        }
    },
    USER_VERIFY_EXPIRE(20009, "验证码已过期，请重新获取") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    USER_VERIFY_ERROR(20010, "验证码错误") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    USER_VERIFY_ERROR_OVERTIME(20011, "操作频繁，请稍后重试") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    USER_PHONE_ERROR(20012, "手机号格式不正确") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_REGISTER_COMPLETE(20013, "手机号已在平台注册") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_PHONE_BIND(20014, "当前账号已绑定手机号，不需绑定") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_REGISTER_WITHOUT_ACCOUNT(20016, "手机号已注册但未开通账号，请联系贵公司管理员开通") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_REGISTER_WITH_ACCOUNT(20016, "您的手机号已注册过，请登录") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_REGISTER_UN_COMPLETE(20016, "该企业名已注册,请联系公司管理员开通账号") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_REGISTER_NOT_LEGAL(20016, "注册用户id不合法,请正确填写") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_PASSWORD_DECRYPT_ERROR(20017, "解密失败") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_QR_INVALID(20018, "二维码已失效，请重新获取") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_QR_SCANNED(20019, "二维码已被扫描，请重新获取") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_QR_MESSAGE_FAILED(20020, "获取信息失败，请重新获取") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_QR_CANCEL(20021, "用户已取消二维码登录") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    USER_REGISTER_ERROR_OVERTIME(20022, "注册失败多次，请明天再试") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    USER_PASSWORD_ERROR(20023, "密码格式不正确，密码支持格式：8-20位，必须包含数字，字母") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    USER_OLD_PASSWORD_ERROR(20024, "原密码不正确") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    USER_PASSWORD_ALREADY_SET(20024, "您的密码已设置，请修改密码") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    USER_PASSWORD_CONFIRM_ERROR(20024, "密码、确认密码不一致") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_HAS_ASSET(20025, "员工账号下有资产，无法删除，请先核查") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_NO_EXISTS(20026, "员工工号已存在，请检查") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_NO_NOT_VALIDATE(20026, "工号不合法，格式支持：2-20位，可包含任意字母、数字、符号“-_”") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_POSITION_NOT_VALIDATE(20026, "职位不合法，格式支持：职位：2-20位，可包含中文,数字,字母,符号") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_ACCOUNT_OPEN_MOBILE_NOT_NULL(20026, "员工账号已开通，不允许清空手机号") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_ACCOUNT_OPEN(20027, "员工账号已开通，请检查") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_ACCOUNT_LIMITED(20027, "账号数已达到公司账号数上限，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_NUM_LIMITED(20027, "员工数已达到公司员工数上限，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_ACCOUNT_CANT_SET_SELF(20027, "您不能修改自己的账号信息") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_MOBILE_NOT_SET(20027, "员工未绑定手机号，请先绑定手机号") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_ACCOUNT_NOT_EXISTS(20028, "员工账号不存在，请检查") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_ACCOUNT_AUTHORIZE_DATA_EMPTY(20028, "自定义部门数据权限范围为空，请检查") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    EMP_ORG_NOT_SET(20029, "员工所在组织未设置") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    VERSION_OVER_MAX(20030, "子公司/员工已达到版本设定数量上限") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    VERSION_NOT_EXIST(20030, "版本不存在") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    LEVEL_OVER_MAX(20031, "层级不得超过7层") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    TEMPLATE_NOT_EXISTS(20032, "模板不存在") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    TEMPLATE_EXISTS(20033, "该分类下已存在同名称模板") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    TEMPLATE_EXISTS_ASSET(20034, "该模板下有资产信息，无法删除") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    TEMPLATE_MANAGE_EXISTS(20035, "模板库存在该名称的资产模板，是否查看") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    TEMPLATE_ATTR_REPEAT(20036, "属性名称不可重复") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    TEMPLATE_ATTR_VALUE_REPEAT(20037, "属性值不可重复") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ASSET_ATTR_CANT_EDIT(20038, "当前属性不可编辑") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ASSET_ATTR_REPEAT(20039, "显示名称不可重复") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ASSET_ATTR_NOT_ROLE(20040, "非常抱歉，您暂时无此权限！请联系客服开通") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    PM_VERSION_NOT_EXISTS(20041, "版本不存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    PM_VERSION_HAS_CONTRACT(20042, "版本下有合同，无法删除") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    CUS_USER_SETTING_NOT_EXISTS(20043, "用户配置信息不存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    TAG_NOT_EXISTS(20044, "标签模板不存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    TAG_SIZE_NOT_EXISTS(20045, "标签尺寸不存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    CONTRACT_NOT_EXISTS(20046, "合同不存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    USER_COMPANY_NOT_REGISTER(20047, "注册尚未完成，请注册公司") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.FORBIDDEN;
        }
    },
    USER_HAS_NO_ROLE(20048, "当前未分配权限，请联系管理员") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ORDER_SETTING_INIT_FAIL(20049, "用户表单配置数据同步失败") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ASSET_CODE_RULE_NOT_EXISTS(20050, "资产编码规则不存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ASSET_CODE_RULE_MAX_LIMIT(20051, "资产编码规则属性个数不能大于%s个") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    ASSET_CODE_NUM_LIMIT(20052, "您当前使用了6位编码且资产数已超过10万个，请选用6位流水号") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    ASSET_FIELD_NOT_SETTING(20053, "您的资产中未启用【%s】该字段，请设置") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    ORDER_NEW_ASSET_TYPE_DB_ERROR(20053, "所选资产不属于调出组织，请确认") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    ORDER_NEW_ASSET_TYPE_ERROR(20053, "新建%s，所选择资产状态错误,资产状态必须为%s") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    ORDER_SETTING_AND_DATA_INCONSISTENT(20053, "数据与表单配置不一致") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    ORDER_DATA_REQUIRED(20054, "%s为必填项, 不能为空") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    ORDER_DATA_INCORRECT(20055, "%s, %s") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    TAG_ATTR_MAX_LIMIT(20056, "标签可选属性数量不能大于%s个") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    TAG_ATTR_NOT_EXISTS(20057, "标签属性 %s 不存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    TAG_ATTR_LENGTH_LIMIT(20058, "标签属性 %s 长度不能超过%s位") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    USER_TAG_LIMIT(20059, "您当前使用的软件版本不支持创建自定义模板") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    USER_TAG_MAX_LIMIT(20060, "企业用户最多可创建%s个标签模板") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    TAG_MATERIAL_NOT_EXISTS(20061, "标签材质不存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ORDER_FIELD_NO_EDIT(20062, "此字段不可编辑展示/必录") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    EQUIPMENT_NOT_EXISTS(20063, "设备不存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    EQUIPMENT_NOT_NEED_AUTHORIZE(20064, "此设备已授权过,不需要再次授权") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    EQUIPMENT_NUMBER_NOT_AUTHORIZE(20065, "此序列号不能授权") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    EQUIPMENT_NUMBER_ALREADY_AUTHORIZE(20066, "此设备序列号已授权过其它企业,需解绑后再授权") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    STANDARD_REPEAT(20067, "标准品编码或名称不可重复") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    STANDARD_TPL_EXISTS(20068, "该标准品下存在模板，无法删除") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    TEMPLATE_CODE_EXISTS(20069, "模板编码不可重复") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    TEMPLATE_NAME_EXISTS(20070, "模板名称不可重复") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ASSET_TPL_MAX_LIMIT(20071, "企业用户最多可创建%s个资产模板") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ORG_ADD_ERROR(20072, "部门下不可添加公司") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ORG_COMPANY_OVER_MAX(20030, "公司已达到版本设定数量上限") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },

    /* 公共属性：30001-39999 如：部门、区域、组织*/
    AREA_SUB_EXISTS(30001, "区域节点下有子区域节点，不能删除") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    AREA_EXISTS(30002, "当前节点区域已存在，请重新输入") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    },
    ORG_EXISTS(30003, "该组织下有%s信息，无法删除") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    ROLE_EXISTS_USER(30004, "该角色下有账号，无法删除，请检查") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    CANT_DELETE_ADMIN(30005, "超级管理员不可删除") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    CANT_EDIT_ADMIN(30005, "超级管理员不可编辑") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    CANT_DELETE_ROLE_COMMON(30005, "员工不可删除") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    CANT_ADD_ROOT(30006, "不可添加根节点") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    INDUSTRY_CODE_ROOT(30007, "已存在相同编码") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    INDUSTRY_NAME_ROOT(30007, "已存在相同名称") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    INDUSTRY_ADD_ROOT(30008, "已存在相同编码或名称") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    INDUSTRY_DELETE_ROOT(30009, "行业下面关联的有资产分类，不可删除") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.FORBIDDEN; }
    },
    CATEGORY_EXISTS(30010, "改资产分类下有%s信息，无法删除") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    DISPOSE_TYPE_MAX_OVER_LIMIT(30011, "处置类型最多只能添加%s条") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    DISPOSE_TYPE_USED(30012, "处置类型已被使用") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },
    HOME_LAYOUT_CONTAINS_NOT_EXISTS_PAGE(30013, "首页布局包含不存在的页面") {
        @Override
        public HttpStatus getHttpStatus() { return HttpStatus.INTERNAL_SERVER_ERROR; }
    },


    /* 权限错误：70001-39999 */
    PERMISSION_FORBIDDEN(70001, "权限不足") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.FORBIDDEN;
        }
    },
    PERMISSION_NO_ACCESS(70002, "无访问权限") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.UNAUTHORIZED;
        }
    },
    TOKEN_EXPIRE(70003, "登录已失效，请重新登录") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.UNAUTHORIZED;
        }
    },
    RESOURCE_EXISTED(70004, "资源已存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.UNAUTHORIZED;
        }
    },
    RESOURCE_NOT_EXISTED(70005, "资源不存在") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.UNAUTHORIZED;
        }
    },
    PERMISSION_CHANGED(70006, "权限已被修改，请重新登录") {
        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.UNAUTHORIZED;
        }
    },
    ;

    private final int code;
    private final String message;

    SystemResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
