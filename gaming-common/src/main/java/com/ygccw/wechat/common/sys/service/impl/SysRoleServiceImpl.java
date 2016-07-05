package com.ygccw.wechat.common.sys.service.impl;

import com.google.common.base.Preconditions;
import com.ygccw.wechat.common.sys.dao.SysRoleDao;
import com.ygccw.wechat.common.sys.entity.SysRole;
import com.ygccw.wechat.common.sys.enums.UserType;
import com.ygccw.wechat.common.sys.service.SysRoleService;
import com.ygccw.wechat.common.utils.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author nick.guo
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Inject
    SysRoleDao sysRoleDao;

    @Override
    public void save(SysRole sysRole) {
        SysRole origRole = findByName(sysRole.getName());
        Preconditions.checkState(null == origRole, " [role_name=" + sysRole.getName() + "] is exist");
        Date date = new Date();
        sysRole.setCreateTime(date);
        sysRole.setUpdateTime(date);
        sysRole.setStatus(1);
        sysRole.setUuid(UUIDUtil.generate());
        sysRoleDao.save(sysRole);
    }


    @Override
    public void update(SysRole sysRole) {
        SysRole origRole = sysRoleDao.findByNameExcludeUuid(sysRole.getName(), sysRole.getUuid());
        Preconditions.checkState(null == origRole, " [role_name=" + sysRole.getName() + "] is exist");
        Date date = new Date();
        sysRole.setUpdateTime(date);
        sysRoleDao.update(sysRole);
    }

    @Override
    public void delete(String uuid) {
        SysRole sysRole = this.findByUuid(uuid);
        sysRole.setStatus(0);
        this.update(sysRole);
    }

    @Override
    public SysRole findByUuid(String uuid) {
        return sysRoleDao.findByUuid(uuid);
    }

    @Override
    public SysRole findByName(String name) {
        return sysRoleDao.findByName(name);
    }

    @Override
    public List<SysRole> list(int offset, int fetchSize) {
        return sysRoleDao.list(offset, fetchSize);
    }

    @Override
    public List<SysRole> list(UserType userType) {
        return sysRoleDao.findByUserType(userType);
    }
}
