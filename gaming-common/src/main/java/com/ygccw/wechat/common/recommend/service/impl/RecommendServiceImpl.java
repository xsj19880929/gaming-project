package com.ygccw.wechat.common.recommend.service.impl;


import com.ygccw.wechat.common.recommend.dao.RecommendDao;
import com.ygccw.wechat.common.recommend.entity.Recommend;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    @Inject
    private RecommendDao recommendDao;

    @Override
    public void save(Recommend recommend) {
        recommend.setCreateTime(new Date());
        recommend.setUpdateTime(new Date());
        recommend.setStatus(1);
        recommendDao.save(recommend);
    }

    @Override
    public void update(Recommend recommend) {
        recommend.setUpdateTime(new Date());
        recommendDao.update(recommend);
    }

    @Override
    public void delete(Long id) {
        recommendDao.delete(id);
    }

    @Override
    public Recommend findById(Long id) {
        return recommendDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        recommendDao.deleteStatus(id);
    }

    @Override
    public List<Recommend> list(Recommend recommend, int offset, int fetchSize) {
        return recommendDao.list(recommend, offset, fetchSize);
    }

    @Override
    public int listSize(Recommend recommend) {
        return recommendDao.listSize(recommend);
    }

    @Override
    public List<Recommend> listByType(RecommendType recommendType) {
        return recommendDao.listByType(recommendType);
    }
}
