package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.zone.dao.AnchorZoneDao;
import com.ygccw.wechat.common.zone.dao.AnchorZonePlatformDao;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class AnchorZoneServiceImpl implements AnchorZoneService {
    @Inject
    private AnchorZoneDao anchorZoneDao;
    @Inject
    private AnchorZonePlatformDao anchorZonePlatformDao;

    @Override
    public void save(AnchorZone anchorZone) {
        saveAndUpdateSetDate(anchorZone);
        anchorZone.setCreateTime(new Date());
        anchorZone.setUpdateTime(new Date());
        anchorZone.setStatus(1);
        anchorZoneDao.save(anchorZone);
    }

    @Override
    public void update(AnchorZone anchorZone) {
        saveAndUpdateSetDate(anchorZone);
        anchorZone.setUpdateTime(new Date());
        anchorZoneDao.update(anchorZone);
    }

    private void saveAndUpdateSetDate(AnchorZone anchorZone) {
        if (anchorZone.getPlatformId() != null) {
            anchorZone.setPlatformName(anchorZonePlatformDao.findById(anchorZone.getPlatformId()).getName());
        }

    }

    @Override
    public void delete(Long id) {
        anchorZoneDao.delete(id);
    }

    @Override
    public AnchorZone findById(Long id) {
        return anchorZoneDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        anchorZoneDao.deleteStatus(id);
    }

    @Override
    public List<AnchorZone> list(AnchorZone anchorZone, int offset, int fetchSize) {
        return anchorZoneDao.list(anchorZone, offset, fetchSize);
    }

    @Override
    public int listSize(AnchorZone anchorZone) {
        return anchorZoneDao.listSize(anchorZone);
    }

    @Override
    public List<AnchorZone> listAll() {
        return anchorZoneDao.listAll();
    }
}
