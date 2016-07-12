package com.ygccw.wechat.zone.service;

import com.ygccw.wechat.common.recommend.entity.Recommend;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendMappingService;
import com.ygccw.wechat.common.recommend.service.RecommendService;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.entity.MatchZoneBonus;
import com.ygccw.wechat.common.zone.entity.MatchZoneCalendar;
import com.ygccw.wechat.common.zone.service.MatchZoneBonusService;
import com.ygccw.wechat.common.zone.service.MatchZoneCalendarService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import com.ygccw.wechat.recommend.model.RecommendMappingModel;
import com.ygccw.wechat.recommend.service.RecommendMappingModelService;
import com.ygccw.wechat.zone.model.MatchZoneModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchZoneModelService {
    @Inject
    private MatchZoneService matchZoneService;
    @Inject
    private MatchZoneBonusService matchZoneBonusService;
    @Inject
    private MatchZoneCalendarService matchZoneCalendarService;
    @Inject
    private RecommendMappingModelService recommendMappingModelService;
    @Inject
    private RecommendService recommendService;
    @Inject
    private RecommendMappingService recommendMappingService;


    @Transactional
    public void save(MatchZoneModel matchZoneModel) {
        MatchZone matchZone = new MatchZone();
        BeanUtils.copyProperties(matchZoneModel, matchZone);
        matchZoneService.save(matchZone);
        if (matchZoneModel.getMatchZoneBonusList() != null) {
            for (MatchZoneBonus matchZoneBonus : matchZoneModel.getMatchZoneBonusList()) {
                matchZoneBonus.setMatchZoneId(matchZone.getId());
                matchZoneBonusService.save(matchZoneBonus);
            }
        }
        if (matchZoneModel.getMatchZoneCalendarList() != null) {
            for (MatchZoneCalendar matchZoneCalendar : matchZoneModel.getMatchZoneCalendarList()) {
                matchZoneCalendar.setMatchZoneId(matchZone.getId());
                matchZoneCalendarService.save(matchZoneCalendar);
            }
        }
        if (matchZoneModel.getRecommendMappingModelList() != null) {
            for (RecommendMappingModel recommendMappingModel : matchZoneModel.getRecommendMappingModelList()) {
                if (recommendMappingModel.getChecked()) {
                    RecommendMapping recommendMapping = new RecommendMapping();
                    BeanUtils.copyProperties(recommendMappingModel, recommendMapping);
                    recommendMapping.setEntityId(matchZone.getId());
                    recommendMappingService.save(recommendMapping);
                }
            }
        }
    }

    @Transactional
    public void update(MatchZoneModel matchZoneModel) {
        MatchZone matchZone = new MatchZone();
        BeanUtils.copyProperties(matchZoneModel, matchZone);
        matchZoneService.update(matchZone);
        matchZoneBonusService.deleteByMatchZoneId(matchZone.getId());
        if (matchZoneModel.getMatchZoneBonusList() != null) {
            for (MatchZoneBonus matchZoneBonus : matchZoneModel.getMatchZoneBonusList()) {
                matchZoneBonus.setMatchZoneId(matchZone.getId());
                matchZoneBonus.setId(null);
                matchZoneBonusService.save(matchZoneBonus);
            }
        }
        if (matchZoneModel.getMatchZoneCalendarList() != null) {
            matchZoneCalendarService.deleteByMatchZoneId(matchZone.getId());
            for (MatchZoneCalendar matchZoneCalendar : matchZoneModel.getMatchZoneCalendarList()) {
                matchZoneCalendar.setMatchZoneId(matchZone.getId());
                matchZoneCalendar.setId(null);
                matchZoneCalendarService.save(matchZoneCalendar);
            }
        }
        if (matchZoneModel.getRecommendMappingModelList() != null) {
            for (RecommendMappingModel recommendMappingModel : matchZoneModel.getRecommendMappingModelList()) {
                RecommendMapping recommendMapping = new RecommendMapping();
                BeanUtils.copyProperties(recommendMappingModel, recommendMapping);
                if (recommendMappingModel.getChecked()) {
                    if (recommendMapping.getId() == null) {
                        recommendMappingService.save(recommendMapping);
                    } else {
                        recommendMappingService.update(recommendMapping);
                    }
                } else {
                    if (recommendMapping.getId() != null) {
                        recommendMappingService.delete(recommendMapping.getId());
                    }
                }
            }
        }
    }

    public List<MatchZoneModel> list(MatchZone matchZoneRequest, int offset, int fetchSize) {
        List<MatchZone> list = matchZoneService.list(matchZoneRequest, offset, fetchSize);
        List<MatchZoneModel> modelList = new ArrayList<>();
        for (MatchZone matchZone : list) {
            MatchZoneModel matchZoneModel = new MatchZoneModel();
            BeanUtils.copyProperties(matchZone, matchZoneModel);
            matchZoneModel.setMatchZoneBonusList(matchZoneBonusService.listByMatchZoneId(matchZone.getId()));
            matchZoneModel.setMatchZoneCalendarList(matchZoneCalendarService.listByMatchZoneId(matchZone.getId()));
            modelList.add(matchZoneModel);
        }
        return modelList;
    }

    public MatchZoneModel findById(Long id) {
        MatchZone matchZone = matchZoneService.findById(id);
        MatchZoneModel matchZoneModel = new MatchZoneModel();
        BeanUtils.copyProperties(matchZone, matchZoneModel);
        matchZoneModel.setMatchZoneBonusList(matchZoneBonusService.listByMatchZoneId(matchZone.getId()));
        matchZoneModel.setMatchZoneCalendarList(matchZoneCalendarService.listByMatchZoneId(matchZone.getId()));
        List<Recommend> recommendList = recommendService.listByType(RecommendType.matchZone);
        List<RecommendMappingModel> recommendMappingModelList = recommendMappingModelService.listByEntityAndTyp(id, RecommendType.matchZone);
        List<RecommendMappingModel> recommendMappingModelListNew = new ArrayList<>();
        for (Recommend recommend : recommendList) {
            RecommendMappingModel recommendMappingModelNew = new RecommendMappingModel();
            recommendMappingModelNew.setChecked(false);
            recommendMappingModelNew.setRecommendName(recommend.getName());
            recommendMappingModelNew.setEntityId(id);
            recommendMappingModelNew.setRecommendId(recommend.getId());
            recommendMappingModelNew.setRecommendType(RecommendType.matchZone);
            for (RecommendMappingModel recommendMappingModel : recommendMappingModelList) {
                if (recommendMappingModel.getRecommendId().compareTo(recommend.getId()) == 0) {
                    BeanUtils.copyProperties(recommendMappingModel, recommendMappingModelNew);
                    recommendMappingModelNew.setChecked(true);
                }
            }
            recommendMappingModelListNew.add(recommendMappingModelNew);
        }
        matchZoneModel.setRecommendMappingModelList(recommendMappingModelListNew);
        return matchZoneModel;

    }
}
