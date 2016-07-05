package com.ygccw.wechat.common.sys.service.impl;

import com.ygccw.wechat.common.sys.dao.SysRoleMenuDao;
import com.ygccw.wechat.common.sys.entity.SysRoleMenu;
import com.ygccw.wechat.common.sys.service.SysRoleMenuService;
import com.ygccw.wechat.common.utils.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author nick.guo
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Inject
    SysRoleMenuDao sysRoleMenuDao;

    @Override
    public void batchSave(List<SysRoleMenu> sysRoleMenus, String roleUuid) {
        sysRoleMenuDao.deleteByRoleUuid(roleUuid);
        if (CollectionUtils.isEmpty(sysRoleMenus)) return;

        for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
            this.save(sysRoleMenu);
        }
    }

    @Override
    public void save(SysRoleMenu sysRoleMenu) {
        Date date = new Date();
        sysRoleMenu.setCreateTime(date);
        sysRoleMenu.setUpdateTime(date);
        sysRoleMenu.setStatus(1);
        sysRoleMenu.setUuid(UUIDUtil.generate());
        sysRoleMenuDao.save(sysRoleMenu);
    }

    @Override
    public void update(SysRoleMenu sysRoleMenu) {
        Date date = new Date();
        sysRoleMenu.setUpdateTime(date);
        sysRoleMenuDao.update(sysRoleMenu);
    }

    @Override
    public void delete(String uuid) {
        SysRoleMenu sysRoleMenu = this.findByUuid(uuid);
        sysRoleMenu.setStatus(0);
        this.update(sysRoleMenu);
    }

    @Override
    public SysRoleMenu findByUuid(String uuid) {
        return sysRoleMenuDao.findByUuid(uuid);
    }

    @Override
    public List<SysRoleMenu> findByRoleUuid(String roleUuid) {
        return sysRoleMenuDao.findByRoleUuid(roleUuid);
    }
}
