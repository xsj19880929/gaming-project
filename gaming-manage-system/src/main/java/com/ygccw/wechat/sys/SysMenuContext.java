package com.ygccw.wechat.sys;

import com.google.common.base.Strings;
import com.ygccw.wechat.common.sys.entity.SysMenu;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nick.guo
 */
public class SysMenuContext {
    private Set<String> menus = new HashSet<>();

    public SysMenuContext(Collection<SysMenu> menus) {
        add(menus);
    }

    private void add(Collection<SysMenu> menus) {
        for (SysMenu sysMenu : menus) {
            if (!Strings.isNullOrEmpty(sysMenu.getUrl()))
                this.menus.add(sysMenu.getUrl());
            if (!CollectionUtils.isEmpty(sysMenu.getChildren())) {
                add(sysMenu.getChildren());
            }
        }
    }

    public SysMenuContext() {
    }

    public Set<String> getMenus() {
        return menus;
    }

    public void setMenus(Set<String> menus) {
        this.menus = menus;
    }

    public Boolean checkPermission(String... urls) {
        if (CollectionUtils.isEmpty(menus)) return true;
        for (String url : urls) {
            if (menus.contains(url)) return true;
        }

        return false;
    }


}
