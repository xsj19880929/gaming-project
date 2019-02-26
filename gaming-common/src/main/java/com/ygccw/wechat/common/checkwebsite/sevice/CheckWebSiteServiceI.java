package com.ygccw.wechat.common.checkwebsite.sevice;


import com.ygccw.wechat.common.checkwebsite.entity.CheckWebSite;

import java.util.List;

/**
 * @author soldier
 */
public interface CheckWebSiteServiceI {
    void save(CheckWebSite checkWebSite);

    void update(CheckWebSite checkWebSite);

    void delete(Long id);

    CheckWebSite findById(Long id);

    void deleteStatus(Long id);

    List<CheckWebSite> list(CheckWebSite checkWebSite, int offset, int fetchSize);

    int listSize(CheckWebSite checkWebSite);
}
