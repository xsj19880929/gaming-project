package com.ygccw.wechat.common.info.service.impl;


import com.ygccw.wechat.common.info.dao.InfoContentDao;
import com.ygccw.wechat.common.info.dao.InfoDao;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.entity.InfoContent;
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
    private InfoContentDao infoContentDao;
    @Inject
    private RecommendMappingDao recommendMappingDao;

    @Override
    @Transactional
    public void save(Info info) {
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
        info.setPublishTime(new Date());
        info.setStatus(1);
        info.setVerify(1);
        infoDao.save(info);
        InfoContent infoContent = new InfoContent();
        infoContent.setContent(info.getContent());
        infoContent.setUuid(info.getUuid());
        infoContent.setStatus(1);
        infoContent.setInfoId(info.getId());
        infoContentDao.save(infoContent);
    }

    @Override
    @Transactional
    public void saveOnly(Info info) {
        infoDao.save(info);
        InfoContent infoContent = new InfoContent();
        infoContent.setContent(info.getContent());
        infoContent.setInfoId(info.getId());
        infoContent.setUuid(info.getUuid());
        infoContent.setStatus(1);
        infoContentDao.save(infoContent);
    }

    @Override
    @Transactional
    public void update(Info info) {
        info.setUpdateTime(new Date());
        infoDao.update(info);
        InfoContent infoContent = infoContentDao.findByInfoId(info.getId());
        if (infoContent == null) {
            infoContent = new InfoContent();
            infoContent.setContent(info.getContent());
            infoContent.setInfoId(info.getId());
            infoContent.setUuid(info.getUuid());
            infoContent.setStatus(1);
            infoContentDao.save(infoContent);
        } else {
            if (core.framework.util.StringUtils.hasText(info.getContent())) {
                infoContent.setContent(info.getContent());
                infoContent.setUuid(info.getUuid());
                infoContentDao.update(infoContent);
            }
        }


    }

    @Override
    @Transactional
    public void delete(Long id) {
        infoDao.delete(id);
        infoContentDao.delete(id);
    }

    @Override
    public Info findById(Long id) {
        Info info = infoDao.findById(id);
        InfoContent infoContent = infoContentDao.findByInfoId(id);
        if (infoContent != null && infoContent.getContent() != null) {
            info.setContent(infoContent.getContent());
        }
        return info;
    }

    @Override
    @Transactional
    public void deleteStatus(Long id) {
        infoDao.deleteStatus(id);
        infoContentDao.delete(id);
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

    @Override
    public Info findByUuid(String uuid) {
        return infoDao.findByUuid(uuid);
    }

    @Override
    public Info findInfoById(Long id) {
        return infoDao.findById(id);
    }
}
