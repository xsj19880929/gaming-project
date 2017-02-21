package com.ygccw.wechat.common.info.service.impl;


import com.ygccw.wechat.common.info.dao.InfoDao;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.recommend.dao.RecommendMappingDao;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class InfoServiceImpl implements InfoService {
    @Inject
    private InfoDao infoDao;
    @Inject
    private RecommendMappingDao recommendMappingDao;

    @Override
    public void save(Info info) {
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
        info.setPublishTime(new Date());
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
    @Transactional
    public void deleteStatus(Long id) {
        infoDao.deleteStatus(id);
        List<RecommendMapping> recommendMappingList = recommendMappingDao.listByEntityIdAndType(id, RecommendType.news);
        for (RecommendMapping recommendMapping : recommendMappingList) {
            recommendMappingDao.deleteStatus(recommendMapping.getId());
        }
    }

    @Override
    public List<Info> list(Info info, int offset, int fetchSize) {
        return infoDao.list(info, offset, fetchSize);
    }

    @Override
    public int listSize(Info info) {
        return infoDao.listSize(info);
    }

    @Override
    public Info lastInfo(Info info) {
        return infoDao.lastInfo(info);
    }

    @Override
    public Info nextInfo(Info info) {
        return infoDao.nextInfo(info);
    }

    @Override
    public void updateVisitCount(Long id) {
        infoDao.updateVisitCount(id);
    }
}
