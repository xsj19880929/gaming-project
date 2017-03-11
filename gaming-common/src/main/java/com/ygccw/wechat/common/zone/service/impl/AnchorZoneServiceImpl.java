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
import com.ygccw.wechat.common.zone.dao.AnchorZoneDao;
import com.ygccw.wechat.common.zone.dao.AnchorZoneMatchZoneMappingDao;
import com.ygccw.wechat.common.zone.dao.AnchorZonePlatformDao;
import com.ygccw.wechat.common.zone.dao.MatchTeamDao;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
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
    @Inject
    private RecommendMappingDao recommendMappingDao;
    @Inject
    private InfoDao infoDao;
    @Inject
    private PictureService pictureService;
    @Inject
    private AnchorZoneMatchZoneMappingDao anchorZoneMatchZoneMappingDao;
    @Inject
    private MatchTeamDao matchTeamDao;

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
        if (anchorZone.getMatchTeamId() != null) {
            anchorZone.setMatchTeamName(matchTeamDao.findById(anchorZone.getMatchTeamId()).getName());
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
    @Transactional
    public void deleteStatus(Long id) {
        anchorZoneDao.deleteStatus(id);
        List<RecommendMapping> recommendMappingList = recommendMappingDao.listByEntityIdAndType(id, RecommendType.anchorZone);
        for (RecommendMapping recommendMapping : recommendMappingList) {
            recommendMappingDao.deleteStatus(recommendMapping.getId());
        }
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.anchorZone);
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
        picture.setPictureZoneType(PictureZoneType.anchorZone);
        picture.setZoneId(id);
        List<Picture> pictureList = pictureService.list(picture, 0, 10000);
        for (Picture picture1 : pictureList) {
            pictureService.deleteStatus(picture1.getId());
            List<RecommendMapping> recommendMappingPictureList = recommendMappingDao.listByEntityIdAndType(picture1.getId(), RecommendType.picture);
            for (RecommendMapping recommendMapping : recommendMappingPictureList) {
                recommendMappingDao.deleteStatus(recommendMapping.getId());
            }
        }
        anchorZoneMatchZoneMappingDao.deleteByAnchorZoneId(id);

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

    @Override
    public void updateVisitCount(Long id) {
        anchorZoneDao.updateVisitCount(id);
    }

    @Override
    public AnchorZone findByUuId(String uuid) {
        return anchorZoneDao.findByUuid(uuid);
    }
}
