package com.ygccw.wechat.common.info.service.impl;


import com.ygccw.wechat.common.info.dao.InfoDao;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.service.InfoService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class InfoServiceImpl implements InfoService {
    @Inject
    private InfoDao infoDao;

    @Override
    public void save(Info info) {
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
        info.setStatus(1);
        info.setVerify(1);
        infoDao.save(info);
    }

    @Override
    public void update(Info info) {
        info.setUpdateTime(new Date());
        infoDao.update(info);
    }

    @Override
    public void delete(Long id) {
        infoDao.delete(id);
    }

    @Override
    public Info findById(Long id) {
        return infoDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        infoDao.deleteStatus(id);
    }

    @Override
    public List<Info> list(Info info, int offset, int fetchSize) {
        return infoDao.list(info, offset, fetchSize);
    }

    @Override
    public int listSize(Info info) {
        return infoDao.listSize(info);
    }

}
