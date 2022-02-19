package com.niimbot.asset.service;

import cn.hutool.core.convert.Convert;
import com.niimbot.asset.framework.constant.BaseConstant;
import com.niimbot.asset.framework.core.enums.SystemResultCode;
import com.niimbot.asset.framework.service.RedisService;
import com.niimbot.asset.service.feign.DataAuthorityFeignClient;
import com.niimbot.jf.core.exception.category.BusinessException;

/**
 * @author XieKong
 * @date 2021/1/15 09:33
 */
public abstract class AbstractLoginService {

    private final DataAuthorityFeignClient dataAuthorityFeignClient;
    protected final RedisService redisService;

    public AbstractLoginService(DataAuthorityFeignClient dataAuthorityFeignClient,
                                RedisService redisService) {
        this.dataAuthorityFeignClient = dataAuthorityFeignClient;
        this.redisService = redisService;
    }

//    protected List<ModelDataScope> buildDataScope(LoginUserDto loginUser) {
//        CusUserDto user = loginUser.getCusUser();
//        // 用户Id
//        Long userId = user.getId();
//        // 租户ID
//        Long tenantId = user.getCompanyId();
//        // 构建 DataScope
//        Short dataScope = user.getDataScope();
//        // 构建数据权限Scope
//        List<ModelDataScope> modelDataScopeList = new ArrayList<>();
//
//        // 0、查看全部，1、仅查看当前用户，4、自定义部门列表
//        if (dataScope.intValue() == DataPermRuleType.TYPE_ALL.getValue()) {
//            ModelDataScope modelDataScope = new ModelDataScope(tenantId, userId);
//            modelDataScope.setRuleType(EnumUtil.getEnumAt(DataPermRuleType.class, dataScope));
//            modelDataScope.setRuleStrategy(DataPermRuleStrategy.APPROVE);
//            modelDataScopeList.add(modelDataScope);
//        } else if (dataScope.intValue() == DataPermRuleType.TYPE_CUSTOM_DEPT_LIST.getValue()) {
//            // Feign查询水平权限
//            List<DataAuthorityDto> authorities = dataAuthorityFeignClient.selectAuthorityById(userId);
//            Map<Integer, ModelDataScope> modelDataScopeMap = new HashMap<>(16);
//            // 设置权限粒度
//            authorities.forEach(authority -> {
//                String code = authority.getAuthorityCode();
//                Integer group = authority.getAuthorityGroup();
//                // 数据范围
//                ModelDataScope scope;
//                if (modelDataScopeMap.containsKey(group)) {
//                    scope = modelDataScopeMap.get(group);
//                } else {
//                    scope = new ModelDataScope(tenantId, userId);
//                    scope.setRuleType(EnumUtil.getEnumAt(DataPermRuleType.class, dataScope));
//                    scope.setRuleStrategy(DataPermRuleStrategy.APPROVE);
//                    modelDataScopeMap.put(group, scope);
//                }
//                Set<Long> auth = new HashSet<>(authority.getAuthorityData());
//                switch (code) {
//                    case AssetConstant.AUTHORITY_DEPTS:
//                        // 设置部门
//                        scope.setDeptIds(auth);
//                        break;
//                    case AssetConstant.AUTHORITY_AREAS:
//                        // 设置区域
//                        scope.setAreaIds(auth);
//                        break;
//                    case AssetConstant.AUTHORITY_STORE:
//                        // 设置仓库
//                        scope.setStoreIds(auth);
//                        break;
//                    case AssetConstant.AUTHORITY_CATE:
//                        // 设置分类
//                        scope.setCateIds(auth);
//                        break;
//                    default:
//                        break;
//                }
//            });
//            modelDataScopeMap.forEach((k, v) -> modelDataScopeList.add(v));
//            if (CollUtil.isEmpty(modelDataScopeList)) {
//                ModelDataScope modelDataScope = new ModelDataScope(tenantId, userId);
//                modelDataScope.setRuleType(EnumUtil.getEnumAt(DataPermRuleType.class, dataScope));
//                modelDataScope.setRuleStrategy(DataPermRuleStrategy.APPROVE);
//                modelDataScopeList.add(modelDataScope);
//            }
//        } else {
//            ModelDataScope modelDataScope = new ModelDataScope(tenantId, userId);
//            modelDataScope.setRuleType(EnumUtil.getEnumAt(DataPermRuleType.class, dataScope));
//            modelDataScope.setRuleStrategy(DataPermRuleStrategy.APPROVE);
//            modelDataScopeList.add(modelDataScope);
//        }
//        return modelDataScopeList;
//    }

    /**
     * 判断用户是否能登录，重试次数大于10不可登录。
     *
     * @param account 用户名称 created by chen.y in 2020/10/30 11:30
     */
    protected void verifyLoginCanLogin(String account) {
        String userErrorCount = BaseConstant.LOGIN_USER_ERROR_COUNT + account;
        Long count = Convert.toLong(redisService.get(userErrorCount));
        // 24小时内登录错误次数大于10，锁定账号密码登录
        if (count != null && count >= 10L) {
            throw new BusinessException(SystemResultCode.USER_ACCOUNT_LOCKED);
        }
    }

    /**
     * @param account 用户名称 created by chen.y in 2020/10/30 17:30
     */
    protected void delLoginCount(String account) {
        redisService.del(BaseConstant.LOGIN_USER_ERROR_COUNT + account);
    }
}
