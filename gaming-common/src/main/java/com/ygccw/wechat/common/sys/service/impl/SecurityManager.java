package com.ygccw.wechat.common.sys.service.impl;

import com.ygccw.wechat.common.sys.dao.SecurityManagerDao;
import com.ygccw.wechat.common.sys.entity.SysMenu;
import com.ygccw.wechat.common.sys.entity.SysUser;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author nick.guo
 */
@Service
public class SecurityManager {
    @Inject
    SecurityManagerDao securityManagerDao;

    public List<SysMenu> loadMenu(SysUser user, String parentUuid) {
        List<SysMenu> sysMenus = securityManagerDao.loadMenuSecurity(user.getUuid(), parentUuid);
        for (SysMenu sysMenu : sysMenus) {
            sysMenu.setChildren(securityManagerDao.loadMenuSecurity(user.getUuid(), sysMenu.getUuid()));
        }
        return sysMenus;
    }

    public List<SysMenu> loadTopMenu(SysUser user) {
        return securityManagerDao.loadMenuSecurityByLevel(user.getUuid(), 1);
    }



}
