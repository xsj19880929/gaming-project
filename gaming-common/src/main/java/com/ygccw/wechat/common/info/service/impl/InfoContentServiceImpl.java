package com.ygccw.wechat.common.info.service.impl;


import com.ygccw.wechat.common.info.dao.InfoContentDao;
import com.ygccw.wechat.common.info.entity.InfoContent;
import com.ygccw.wechat.common.info.service.InfoContentService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * @author soldier
 */
@Service
public class InfoContentServiceImpl implements InfoContentService {
    @Inject
    private InfoContentDao infoContentDaoDao;

    @Override
    public void save(InfoContent infoContent) {
        infoContent.setStatus(1);
        infoContentDaoDao.save(infoContent);
    }


    @Override
    public void update(InfoContent infoContent) {
        infoContentDaoDao.update(infoContent);
    }

    @Override
    public void delete(Long infoId) {
        infoContentDaoDao.delete(infoId);
    }

    @Override
    public InfoContent findByInfoId(Long infoId) {
        return infoContentDaoDao.findByInfoId(infoId);
    }

    @Override
    @Transactional
    public void deleteStatus(Long id) {
        infoContentDaoDao.deleteStatus(id);
    }


    @Override
    public InfoContent findByUuid(String uuid) {
        return infoContentDaoDao.findByUuid(uuid);
    }
}
