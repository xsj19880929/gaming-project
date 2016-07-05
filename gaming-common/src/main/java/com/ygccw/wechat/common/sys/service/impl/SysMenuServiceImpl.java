package com.ygccw.wechat.common.sys.service.impl;

import com.ygccw.wechat.common.sys.dao.SysMenuDao;
import com.ygccw.wechat.common.sys.entity.SysMenu;
import com.ygccw.wechat.common.sys.service.SysMenuService;
import com.ygccw.wechat.common.utils.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author nick.guo
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Inject
    SysMenuDao sysMenuDao;


    public List<SysMenu> listByParentUuid(String parentUuid) {
        return sysMenuDao.listByParentUuid(parentUuid);
    }

    @Override
    public void save(SysMenu sysMenu) {
        Date date = new Date();
        sysMenu.setCreateTime(date);
        sysMenu.setUpdateTime(date);
        sysMenu.setStatus(1);
        sysMenu.setUuid(UUIDUtil.generate());
        sysMenuDao.save(sysMenu);
    }

    @Override
    public void update(SysMenu sysMenu) {
        Date date = new Date();
        sysMenu.setUpdateTime(date);
        sysMenuDao.update(sysMenu);
    }

    @Override
    @Transactional
    public void delete(String uuid) {
        SysMenu sysMenu = this.findByUuid(uuid);
        sysMenu.setStatus(0);
        this.update(sysMenu);
    }

    @Override
    public SysMenu findByUuid(String uuid) {
        return sysMenuDao.findByUuid(uuid);
    }

    @Override
    public List<SysMenu> loadMenuTree() {
        List<SysMenu> sysMenus = sysMenuDao.loadMenuByParentUuidAndLevel(null, 1);
        recursion(sysMenus);
        return sysMenus;
    }

    @Override
    public List<SysMenu> loadMenuByParentUuidAndLevel(String parentUuid, Integer level) {
        return null;
    }


    private void recursion(List<SysMenu> sysMenus) {
        if (CollectionUtils.isEmpty(sysMenus)) return;
        for (SysMenu sysMenu : sysMenus) {
            sysMenu.setChildren(sysMenuDao.loadMenuByParentUuidAndLevel(sysMenu.getUuid(), sysMenu.getLevel() + 1));
            recursion(sysMenu.getChildren());
        }
    }
}
