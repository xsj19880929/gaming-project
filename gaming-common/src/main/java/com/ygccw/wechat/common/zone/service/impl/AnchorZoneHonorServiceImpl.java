package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.zone.dao.AnchorZoneHonorDao;
import com.ygccw.wechat.common.zone.entity.AnchorZoneHonor;
import com.ygccw.wechat.common.zone.service.AnchorZoneHonorService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class AnchorZoneHonorServiceImpl implements AnchorZoneHonorService {
    @Inject
    private AnchorZoneHonorDao anchorZoneHonorDao;

    @Override
    public void save(AnchorZoneHonor anchorZoneHonor) {
        anchorZoneHonor.setCreateTime(new Date());
        anchorZoneHonor.setUpdateTime(new Date());
        anchorZoneHonor.setStatus(1);
        anchorZoneHonorDao.save(anchorZoneHonor);
    }

    @Override
    public void update(AnchorZoneHonor anchorZoneHonor) {
        anchorZoneHonor.setUpdateTime(new Date());
        anchorZoneHonorDao.update(anchorZoneHonor);
    }

    @Override
    public AnchorZoneHonor findById(Long id) {
        return anchorZoneHonorDao.findById(id);
    }


    @Override
    public List<AnchorZoneHonor> listByAnchorZoneId(Long anchorZoneId) {
        return anchorZoneHonorDao.listByAnchorZoneId(anchorZoneId);
    }

    @Override
    public void deleteByAnchorZoneId(Long anchorZoneId) {
        anchorZoneHonorDao.deleteByAnchorZoneId(anchorZoneId);
    }

    @Override
    public void saveList(List<AnchorZoneHonor> anchorZoneHonorList) {
        for (AnchorZoneHonor anchorZoneHonor : anchorZoneHonorList) {
            anchorZoneHonor.setCreateTime(new Date());
            anchorZoneHonor.setUpdateTime(new Date());
            anchorZoneHonor.setStatus(1);
            anchorZoneHonorDao.save(anchorZoneHonor);
        }

    }
}
