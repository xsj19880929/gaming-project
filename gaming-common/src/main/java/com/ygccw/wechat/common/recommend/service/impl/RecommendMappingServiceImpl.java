package com.ygccw.wechat.common.recommend.service.impl;


import com.ygccw.wechat.common.recommend.dao.RecommendMappingDao;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendMappingService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class RecommendMappingServiceImpl implements RecommendMappingService {
    @Inject
    private RecommendMappingDao recommendMappingDao;

    @Override
    public void save(RecommendMapping recommendMapping) {
        recommendMapping.setCreateTime(new Date());
        recommendMapping.setUpdateTime(new Date());
        recommendMapping.setStatus(1);
        recommendMappingDao.save(recommendMapping);
    }

    @Override
    public void update(RecommendMapping recommendMapping) {
        recommendMapping.setUpdateTime(new Date());
        recommendMappingDao.update(recommendMapping);
    }

    @Override
    public void delete(Long id) {
        recommendMappingDao.delete(id);
    }

    @Override
    public RecommendMapping findById(Long id) {
        return recommendMappingDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        recommendMappingDao.deleteStatus(id);
    }

    @Override
    public List<RecommendMapping> list(RecommendMapping recommendMapping, int offset, int fetchSize) {
        return recommendMappingDao.list(recommendMapping, offset, fetchSize);
    }

    @Override
    public int listSize(RecommendMapping recommendMapping) {
        return recommendMappingDao.listSize(recommendMapping);
    }

    @Override
    public List<RecommendMapping> listByEntityIdAndType(Long entityId, RecommendType recommendType) {
        return recommendMappingDao.listByEntityIdAndType(entityId, recommendType);
    }
}
