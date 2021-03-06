/*
 * Copyright (c)  2018
 * All rights reserved.
 * 2018-08-08 14:06:03
 */
package com.ueboot.shiro.service.resources.impl;

import com.ueboot.core.repository.BaseRepository;
import com.ueboot.core.service.impl.BaseServiceImpl;
import com.ueboot.shiro.entity.Permission;
import com.ueboot.shiro.entity.Resources;
import com.ueboot.shiro.entity.UserRole;
import com.ueboot.shiro.repository.permission.PermissionRepository;
import com.ueboot.shiro.repository.permission.bo.PermissionBo;
import com.ueboot.shiro.repository.resources.ResourcesRepository;
import com.ueboot.shiro.repository.userrole.UserRoleRepository;
import com.ueboot.shiro.service.resources.ResourcesService;
import com.ueboot.shiro.shiro.ShiroEventListener;
import com.ueboot.shiro.shiro.ShiroService;
import com.ueboot.shiro.shiro.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created on 2018-08-08 14:06:03
 *
 * @author yangkui
 * @since 2.1.0 by ueboot-generator
 */
@Slf4j
@Service
@ConditionalOnMissingBean(name = "resourcesService")
public class ResourcesServiceImpl extends BaseServiceImpl<Resources> implements ResourcesService {
    @Resource
    private ResourcesRepository resourcesRepository;

    @Resource
    private ShiroEventListener shiroEventListener;


    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private ShiroService shiroService;

    @Resource
    private UserRoleRepository userRoleRepository;


    @Override
    protected BaseRepository getBaseRepository() {
        return resourcesRepository;
    }


    /**
     * ????????????????????????????????????
     *
     * @param resourcesType ????????????
     * @return ????????????
     */
    @Override
    public List<Resources> findByResourceType(String resourcesType) {
        return resourcesRepository.findByResourceType(resourcesType);
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param username ?????????
     * @return ????????????????????????????????????
     */
    @Override
    public Collection<PermissionBo> getUserResources(String username) {
        //root?????????????????????????????????root?????????????????????????????????
        if (UserRealm.SUPER_USER.equals(username)) {
            final Collection<PermissionBo> resources = new ArrayList<>();
            this.resourcesRepository.findAll().forEach((r) -> {
                PermissionBo bo = new PermissionBo();
                BeanUtils.copyProperties(r, bo);
                bo.setResourceId(r.getId());
                bo.setResourceName(r.getName());
                bo.setParentId(r.getParent()!=null?r.getParent().getId():null);
                resources.add(bo);
            });
            return resources;
        }

        //?????????????????????????????????????????????????????????
        //????????????shiroService????????????????????????????????????????????????????????????????????????
        Set<String> roleNames = shiroService.getUserRoleNames(username);
        List<PermissionBo> permissions = permissionRepository.findPermissionsByRoleNameIn(roleNames);
        return permissions;
    }

    @Override
    public Resources findById(Long id) {
        return resourcesRepository.getOne(id);
    }

    /**
     * ??????parentId??????????????????
     *
     * @param pageable ????????????
     * @param parentId parentId
     * @return Page<Resources>
     */
    @Override
    public Page<Resources> findByParentId(Pageable pageable, Long parentId) {
        return resourcesRepository.findByParentId(pageable, parentId);
    }

    /**
     * ????????????
     * ??????????????????????????????????????????????????????
     *
     * @param ids ???????????????ID??????
     */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 60, propagation = Propagation.REQUIRED)
    public void deleteResource(Long[] ids) {
        for (Long i : ids) {
            //????????????
            List<Permission> permissions = this.permissionRepository.findByResourceId(i);
            if (!permissions.isEmpty()) {
                this.permissionRepository.deleteAll(permissions);
            }
            //???????????????
            List<Resources> resources = this.resourcesRepository.findByParentId(i);
            if (!resources.isEmpty()) {
                this.resourcesRepository.deleteAll(resources);
            }
            //????????????
            this.resourcesRepository.deleteById(i);


            // ????????????????????????
            String optUserName = (String) SecurityUtils.getSubject().getPrincipal();
            this.shiroEventListener.deleteResourceEvent(optUserName, ids);
        }
    }
}
