package com.ygccw.wechat.common.zone.service.impl;


import com.ygccw.wechat.common.zone.dao.AnchorZoneMatchZoneMappingDao;
import com.ygccw.wechat.common.zone.entity.AnchorZoneMatchZoneMapping;
import com.ygccw.wechat.common.zone.service.AnchorZoneMatchZoneMappingService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class AnchorZoneMatchZoneMappingServiceImpl implements AnchorZoneMatchZoneMappingService {
    @Inject
    private AnchorZoneMatchZoneMappingDao anchorZoneMatchZoneMappingDao;

    @Override
    public void save(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping) {
        anchorZoneMatchZoneMapping.setCreateTime(new Date());
        anchorZoneMatchZoneMapping.setUpdateTime(new Date());
        anchorZoneMatchZoneMapping.setStatus(1);
        anchorZoneMatchZoneMappingDao.save(anchorZoneMatchZoneMapping);
    }

    @Override
    public void update(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping) {
        anchorZoneMatchZoneMapping.setUpdateTime(new Date());
        anchorZoneMatchZoneMappingDao.update(anchorZoneMatchZoneMapping);
    }

    @Override
    public void delete(Long id) {
        anchorZoneMatchZoneMappingDao.delete(id);
    }

    @Override
    public AnchorZoneMatchZoneMapping findById(Long id) {
        return anchorZoneMatchZoneMappingDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        anchorZoneMatchZoneMappingDao.deleteStatus(id);
    }

    @Override
    public List<AnchorZoneMatchZoneMapping> list(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping, int offset, int fetchSize) {
        return anchorZoneMatchZoneMappingDao.list(anchorZoneMatchZoneMapping, offset, fetchSize);
    }

    @Override
    public int listSize(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping) {
        return anchorZoneMatchZoneMappingDao.listSize(anchorZoneMatchZoneMapping);
    }

    @Override
    public List<AnchorZoneMatchZoneMapping> listByAnchorZoneId(Long anchorZoneId) {
        return anchorZoneMatchZoneMappingDao.listByAnchorZoneId(anchorZoneId);
    }
}
