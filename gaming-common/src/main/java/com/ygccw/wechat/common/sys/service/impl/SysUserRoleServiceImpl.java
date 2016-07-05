package com.ygccw.wechat.common.sys.service.impl;

import com.ygccw.wechat.common.sys.dao.SysRoleDao;
import com.ygccw.wechat.common.sys.dao.SysUserDao;
import com.ygccw.wechat.common.sys.dao.SysUserRoleDao;
import com.ygccw.wechat.common.sys.entity.SysRole;
import com.ygccw.wechat.common.sys.entity.SysUserRole;
import com.ygccw.wechat.common.sys.service.SysUserRoleService;
import com.ygccw.wechat.common.utils.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author nick.guo
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Inject
    SysUserRoleDao sysUserRoleDao;
    @Inject
    SysUserDao sysUserDao;
    @Inject
    SysRoleDao sysRoleDao;

    @Override
    @Transactional
    public void batchSave(List<SysUserRole> sysUserRoles, String roleUuid) {
        sysUserRoleDao.deleteByRoleUuid(roleUuid);
        if (CollectionUtils.isEmpty(sysUserRoles)) return;

        for (SysUserRole sysUserRole : sysUserRoles) {
            this.save(sysUserRole);
        }
    }

    @Override
    public void save(SysUserRole sysUserRole) {
        Date date = new Date();
        sysUserRole.setCreateTime(date);
        sysUserRole.setUpdateTime(date);
        sysUserRole.setStatus(1);
        sysUserRole.setUuid(UUIDUtil.generate());
        sysUserRoleDao.save(sysUserRole);
    }

    @Override
    public void update(SysUserRole sysUserRole) {
        Date date = new Date();
        sysUserRole.setUpdateTime(date);
        sysUserRoleDao.update(sysUserRole);
    }

    @Override
    public void delete(String uuid) {
        SysUserRole sysUserRole = this.findByUuid(uuid);
        sysUserRole.setStatus(0);
        this.update(sysUserRole);
    }

    @Override
    public SysUserRole findByUuid(String uuid) {
        return sysUserRoleDao.findByUuid(uuid);
    }

    @Override
    public SysRole findSysRoleBySysUserUuid(String sysUserUuid) {
        SysUserRole sysUserRole = sysUserRoleDao.findBySysUserUuid(sysUserUuid);
        if (null == sysUserRole) return null;
        return sysRoleDao.findByUuid(sysUserRole.getRoleUuid());
    }

    @Override
    public SysUserRole findBySysUserUuid(String sysUserUuid) {
        return sysUserRoleDao.findBySysUserUuid(sysUserUuid);
    }
}
