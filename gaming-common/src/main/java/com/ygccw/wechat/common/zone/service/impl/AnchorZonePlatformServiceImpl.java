package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.zone.dao.AnchorZonePlatformDao;
import com.ygccw.wechat.common.zone.entity.AnchorZonePlatform;
import com.ygccw.wechat.common.zone.service.AnchorZonePlatformService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class AnchorZonePlatformServiceImpl implements AnchorZonePlatformService {
    @Inject
    private AnchorZonePlatformDao anchorZonePlatformDao;

    @Override
    public void save(AnchorZonePlatform anchorZonePlatform) {
        anchorZonePlatform.setCreateTime(new Date());
        anchorZonePlatform.setUpdateTime(new Date());
        anchorZonePlatform.setStatus(1);
        anchorZonePlatformDao.save(anchorZonePlatform);
    }

    @Override
    public void update(AnchorZonePlatform anchorZonePlatform) {
        anchorZonePlatform.setUpdateTime(new Date());
        anchorZonePlatformDao.update(anchorZonePlatform);
    }

    @Override
    public void delete(Long id) {
        anchorZonePlatformDao.delete(id);
    }

    @Override
    public AnchorZonePlatform findById(Long id) {
        return anchorZonePlatformDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        anchorZonePlatformDao.deleteStatus(id);
    }

    @Override
    public List<AnchorZonePlatform> list(AnchorZonePlatform anchorZonePlatform, int offset, int fetchSize) {
        return anchorZonePlatformDao.list(anchorZonePlatform, offset, fetchSize);
    }

    @Override
    public int listSize(AnchorZonePlatform anchorZonePlatform) {
        return anchorZonePlatformDao.listSize(anchorZonePlatform);
    }

    @Override
    public List<AnchorZonePlatform> listAll() {
        return anchorZonePlatformDao.listAll();
    }
}
