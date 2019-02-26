package com.ygccw.wechat.common.checkwebsite.sevice.impl;


import com.ygccw.wechat.common.checkwebsite.dao.CheckWebSiteDao;
import com.ygccw.wechat.common.checkwebsite.entity.CheckWebSite;
import com.ygccw.wechat.common.checkwebsite.sevice.CheckWebSiteServiceI;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class CheckWebSiteServiceImpl implements CheckWebSiteServiceI {
    @Inject
    private CheckWebSiteDao checkWebSiteDao;

    @Override
    public void save(CheckWebSite checkWebSite) {
        checkWebSite.setCreateTime(new Date());
        checkWebSite.setUpdateTime(new Date());
        checkWebSite.setStatus(1);
        checkWebSiteDao.save(checkWebSite);
    }

    @Override
    public void update(CheckWebSite checkWebSite) {
        checkWebSite.setUpdateTime(new Date());
        checkWebSiteDao.update(checkWebSite);
    }

    @Override
    public void delete(Long id) {
        checkWebSiteDao.delete(id);
    }

    @Override
    public CheckWebSite findById(Long id) {
        return checkWebSiteDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        checkWebSiteDao.deleteStatus(id);
    }

    @Override
    public List<CheckWebSite> list(CheckWebSite checkWebSite, int offset, int fetchSize) {
        return checkWebSiteDao.list(checkWebSite, offset, fetchSize);
    }

    @Override
    public int listSize(CheckWebSite checkWebSite) {
        return checkWebSiteDao.listSize(checkWebSite);
    }
}
