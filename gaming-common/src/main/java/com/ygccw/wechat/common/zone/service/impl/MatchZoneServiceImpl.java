package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.info.dao.InfoDao;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.enums.PictureZoneType;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.recommend.dao.RecommendMappingDao;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.zone.dao.AnchorZoneMatchZoneMappingDao;
import com.ygccw.wechat.common.zone.dao.MatchTeamMappingDao;
import com.ygccw.wechat.common.zone.dao.MatchZoneAreaDao;
import com.ygccw.wechat.common.zone.dao.MatchZoneDao;
import com.ygccw.wechat.common.zone.dao.MatchZoneYearDao;
import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchZoneServiceImpl implements MatchZoneService {
    @Inject
    private MatchZoneDao matchZoneDao;
    @Inject
    private MatchZoneYearDao matchZoneYearDao;
    @Inject
    private MatchZoneAreaDao matchZoneAreaDao;
    @Inject
    private RecommendMappingDao recommendMappingDao;
    @Inject
    private InfoDao infoDao;
    @Inject
    private PictureService pictureService;
    @Inject
    private MatchTeamMappingDao matchTeamMappingDao;
    @Inject
    private AnchorZoneMatchZoneMappingDao anchorZoneMatchZoneMappingDao;

    @Override
    public void save(MatchZone matchZone) {
        saveAndUpdateSetDate(matchZone);
        matchZone.setCreateTime(new Date());
        matchZone.setUpdateTime(new Date());
        matchZone.setStatus(1);
        matchZoneDao.save(matchZone);
    }

    @Override
    public void update(MatchZone matchZone) {
        saveAndUpdateSetDate(matchZone);
        matchZone.setUpdateTime(new Date());
        matchZoneDao.update(matchZone);
    }

    private void saveAndUpdateSetDate(MatchZone matchZone) {
        if (matchZone.getMatchZoneYearId() != null) {
            matchZone.setMatchZoneYearName(matchZoneYearDao.findById(matchZone.getMatchZoneYearId()).getYearName());
        }
        if (matchZone.getMatchZoneAreaId() != null) {
            matchZone.setMatchZoneAreaName(matchZoneAreaDao.findById(matchZone.getMatchZoneAreaId()).getAreaName());
        }
        if (matchZone.getIfAnchorMatch() == null) {
            matchZone.setIfAnchorMatch(false);
        }
        if (matchZone.getIfStart() == null) {
            matchZone.setIfStart(false);
        }

    }

    @Override
    public void delete(Long id) {
        matchZoneDao.delete(id);
    }

    @Override
    public MatchZone findById(Long id) {
        return matchZoneDao.findById(id);
    }

    @Override
    @Transactional
    public void deleteStatus(Long id) {
        matchZoneDao.deleteStatus(id);
        List<RecommendMapping> recommendMappingList = recommendMappingDao.listByEntityIdAndType(id, RecommendType.matchZone);
        for (RecommendMapping recommendMapping : recommendMappingList) {
            recommendMappingDao.deleteStatus(recommendMapping.getId());
        }
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.matchZone);
        info.setZoneId(id);
        List<Info> infoList = infoDao.list(info, 0, 10000);
        for (Info info1 : infoList) {
            infoDao.deleteStatus(info1.getId());
            List<RecommendMapping> recommendMappingInfoList = recommendMappingDao.listByEntityIdAndType(info1.getId(), RecommendType.news);
            for (RecommendMapping recommendMapping : recommendMappingInfoList) {
                recommendMappingDao.deleteStatus(recommendMapping.getId());
            }
        }
        Picture picture = new Picture();
        picture.setPictureZoneType(PictureZoneType.matchZone);
        picture.setZoneId(id);
        List<Picture> pictureList = pictureService.list(picture, 0, 10000);
        for (Picture picture1 : pictureList) {
            pictureService.deleteStatus(picture1.getId());
            List<RecommendMapping> recommendMappingPictureList = recommendMappingDao.listByEntityIdAndType(picture1.getId(), RecommendType.picture);
            for (RecommendMapping recommendMapping : recommendMappingPictureList) {
                recommendMappingDao.deleteStatus(recommendMapping.getId());
            }
        }
        List<MatchTeamMapping> matchTeamMappingList = matchTeamMappingDao.listByMatchZoneId(id);
        for (MatchTeamMapping matchTeamMapping : matchTeamMappingList) {
            matchTeamMappingDao.delete(matchTeamMapping.getId());
        }
        anchorZoneMatchZoneMappingDao.deleteByMatchZoneId(id);

    }

    @Override
    public List<MatchZone> list(MatchZone matchZone, int offset, int fetchSize) {
        return matchZoneDao.list(matchZone, offset, fetchSize);
    }

    @Override
    public int listSize(MatchZone matchZone) {
        return matchZoneDao.listSize(matchZone);
    }

    @Override
    public List<MatchZone> listAll() {
        return matchZoneDao.listAll();
    }

    @Override
    public List<MatchZone> listByIfAnchorMatch() {
        return matchZoneDao.listByIfAnchorMatch();
    }

    public List<MatchZone> listOrderByVisit(int offset, int fetchSize) {
        return matchZoneDao.listOrderByVisit(offset, fetchSize);
    }

    @Override
    public void updateVisitCount(Long id) {
        matchZoneDao.updateVisitCount(id);
    }

    @Override
    public MatchZone findByUuid(String uuid) {
        return matchZoneDao.findByUuid(uuid);
    }
}
