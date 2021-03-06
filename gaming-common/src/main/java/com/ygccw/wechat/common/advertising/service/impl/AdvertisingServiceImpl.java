package com.ygccw.wechat.common.advertising.service.impl;


import com.ygccw.wechat.common.advertising.dao.AdvertisingDao;
import com.ygccw.wechat.common.advertising.entity.Advertising;
import com.ygccw.wechat.common.advertising.enums.AdvType;
import com.ygccw.wechat.common.advertising.service.AdvertisingService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class AdvertisingServiceImpl implements AdvertisingService {
    @Inject
    private AdvertisingDao advertisingDao;

    @Override
    public void save(Advertising advertising) {
        advertising.setCreateTime(new Date());
        advertising.setUpdateTime(new Date());
        advertising.setStatus(1);
        advertisingDao.save(advertising);
    }

    @Override
    public void update(Advertising advertising) {
        advertising.setUpdateTime(new Date());
        advertisingDao.update(advertising);
    }

    @Override
    public void delete(Long id) {
        advertisingDao.delete(id);
    }

    @Override
    public Advertising findById(Long id) {
        return advertisingDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        advertisingDao.deleteStatus(id);
    }

    @Override
    public List<Advertising> list(Advertising advertising, int offset, int fetchSize) {
        return advertisingDao.list(advertising, offset, fetchSize);
    }

    @Override
    public int listSize(Advertising advertising) {
        return advertisingDao.listSize(advertising);
    }

    @Override
    public Advertising findByAdvType(AdvType advType) {
        Advertising advertising = new Advertising();
        advertising.setAdvType(advType);
        List<Advertising> list = advertisingDao.list(advertising, 0, 1);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
