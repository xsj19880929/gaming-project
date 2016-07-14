package com.ygccw.wechat.zone.service;

import com.ygccw.wechat.common.recommend.entity.Recommend;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendMappingService;
import com.ygccw.wechat.common.recommend.service.RecommendService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.AnchorZoneHonor;
import com.ygccw.wechat.common.zone.entity.AnchorZoneMatchZoneMapping;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneHonorService;
import com.ygccw.wechat.common.zone.service.AnchorZoneMatchZoneMappingService;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import com.ygccw.wechat.recommend.model.RecommendMappingModel;
import com.ygccw.wechat.recommend.service.RecommendMappingModelService;
import com.ygccw.wechat.zone.model.AnchorZoneMatchZoneMappingModel;
import com.ygccw.wechat.zone.model.AnchorZoneModel;
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
public class AnchorZoneModelService {
    @Inject
    private AnchorZoneService anchorZoneService;
    @Inject
    private AnchorZoneHonorService anchorZoneHonorService;
    @Inject
    private RecommendMappingService recommendMappingService;
    @Inject
    private RecommendService recommendService;
    @Inject
    private RecommendMappingModelService recommendMappingModelService;
    @Inject
    private AnchorZoneMatchZoneMappingService anchorZoneMatchZoneMappingService;
    @Inject
    private MatchZoneService matchZoneService;


    @Transactional
    public void save(AnchorZoneModel anchorZoneModel) {
        AnchorZone anchorZone = new AnchorZone();
        BeanUtils.copyProperties(anchorZoneModel, anchorZone);
        anchorZoneService.save(anchorZone);
        if (anchorZoneModel.getAnchorZoneHonorList() != null) {
            for (AnchorZoneHonor anchorZoneHonor : anchorZoneModel.getAnchorZoneHonorList()) {
                anchorZoneHonor.setAnchorZoneId(anchorZone.getId());
                anchorZoneHonorService.save(anchorZoneHonor);
            }
        }
        if (anchorZoneModel.getRecommendMappingModelList() != null) {
            for (RecommendMappingModel recommendMappingModel : anchorZoneModel.getRecommendMappingModelList()) {
                if (recommendMappingModel.getChecked()) {
                    RecommendMapping recommendMapping = new RecommendMapping();
                    BeanUtils.copyProperties(recommendMappingModel, recommendMapping);
                    recommendMapping.setEntityId(anchorZone.getId());
                    recommendMappingService.save(recommendMapping);
                }
            }
        }
        if (anchorZoneModel.getAnchorZoneMatchZoneMappingModelList() != null) {
            for (AnchorZoneMatchZoneMappingModel anchorZoneMatchZoneMappingModel : anchorZoneModel.getAnchorZoneMatchZoneMappingModelList()) {
                if (anchorZoneMatchZoneMappingModel.getChecked()) {
                    AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping = new AnchorZoneMatchZoneMapping();
                    BeanUtils.copyProperties(anchorZoneMatchZoneMappingModel, anchorZoneMatchZoneMapping);
                    anchorZoneMatchZoneMapping.setAnchorZoneId(anchorZone.getId());
                    anchorZoneMatchZoneMappingService.save(anchorZoneMatchZoneMapping);
                }
            }
        }
    }

    @Transactional
    public void update(AnchorZoneModel anchorZoneModel) {
        AnchorZone anchorZone = new AnchorZone();
        BeanUtils.copyProperties(anchorZoneModel, anchorZone);
        anchorZoneService.update(anchorZone);
        anchorZoneHonorService.deleteByAnchorZoneId(anchorZone.getId());
        if (anchorZoneModel.getAnchorZoneHonorList() != null) {
            for (AnchorZoneHonor anchorZoneHonor : anchorZoneModel.getAnchorZoneHonorList()) {
                anchorZoneHonor.setAnchorZoneId(anchorZone.getId());
                anchorZoneHonor.setId(null);
                anchorZoneHonorService.save(anchorZoneHonor);
            }
        }
        if (anchorZoneModel.getRecommendMappingModelList() != null) {
            saveOrUpdateRecommendMapping(anchorZoneModel.getRecommendMappingModelList());
        }
        if (anchorZoneModel.getAnchorZoneMatchZoneMappingModelList() != null) {
            saveOrUpdateAnchorZoneMatchZoneMapping(anchorZoneModel.getAnchorZoneMatchZoneMappingModelList());
        }
    }

    private void saveOrUpdateRecommendMapping(List<RecommendMappingModel> recommendMappingModelList) {
        for (RecommendMappingModel recommendMappingModel : recommendMappingModelList) {
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

    private void saveOrUpdateAnchorZoneMatchZoneMapping(List<AnchorZoneMatchZoneMappingModel> anchorZoneMatchZoneMappingModelList) {
        for (AnchorZoneMatchZoneMappingModel anchorZoneMatchZoneMappingModel : anchorZoneMatchZoneMappingModelList) {
            AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping = new AnchorZoneMatchZoneMapping();
            BeanUtils.copyProperties(anchorZoneMatchZoneMappingModel, anchorZoneMatchZoneMapping);
            if (anchorZoneMatchZoneMappingModel.getChecked()) {
                if (anchorZoneMatchZoneMapping.getId() == null) {
                    anchorZoneMatchZoneMappingService.save(anchorZoneMatchZoneMapping);
                } else {
                    anchorZoneMatchZoneMappingService.update(anchorZoneMatchZoneMapping);
                }
            } else {
                if (anchorZoneMatchZoneMapping.getId() != null) {
                    anchorZoneMatchZoneMappingService.delete(anchorZoneMatchZoneMapping.getId());
                }
            }
        }
    }


    public AnchorZoneModel findById(Long id) {
        AnchorZone anchorZone = anchorZoneService.findById(id);
        AnchorZoneModel anchorZoneModel = new AnchorZoneModel();
        BeanUtils.copyProperties(anchorZone, anchorZoneModel);
        anchorZoneModel.setAnchorZoneHonorList(anchorZoneHonorService.listByAnchorZoneId(anchorZone.getId()));
        anchorZoneModel.setRecommendMappingModelList(getRecommendMappingModelList(id));
        anchorZoneModel.setAnchorZoneMatchZoneMappingModelList(getAnchorZoneMatchZoneMappingModelList(id));
        return anchorZoneModel;

    }

    private List<RecommendMappingModel> getRecommendMappingModelList(Long id) {
        List<Recommend> recommendList = recommendService.listByType(RecommendType.anchorZone);
        List<RecommendMappingModel> recommendMappingModelList = recommendMappingModelService.listByEntityAndTyp(id, RecommendType.anchorZone);
        List<RecommendMappingModel> recommendMappingModelListNew = new ArrayList<>();
        for (Recommend recommend : recommendList) {
            RecommendMappingModel recommendMappingModelNew = new RecommendMappingModel();
            recommendMappingModelNew.setChecked(false);
            recommendMappingModelNew.setRecommendName(recommend.getName());
            recommendMappingModelNew.setEntityId(id);
            recommendMappingModelNew.setRecommendId(recommend.getId());
            recommendMappingModelNew.setRecommendType(RecommendType.anchorZone);
            for (RecommendMappingModel recommendMappingModel : recommendMappingModelList) {
                if (recommendMappingModel.getRecommendId().compareTo(recommend.getId()) == 0) {
                    BeanUtils.copyProperties(recommendMappingModel, recommendMappingModelNew);
                    recommendMappingModelNew.setChecked(true);
                }
            }
            recommendMappingModelListNew.add(recommendMappingModelNew);
        }
        return recommendMappingModelListNew;
    }

    private List<AnchorZoneMatchZoneMappingModel> getAnchorZoneMatchZoneMappingModelList(Long id) {
        List<MatchZone> matchZoneList = matchZoneService.listByIfAnchorMatch();
        List<AnchorZoneMatchZoneMapping> anchorZoneMatchZoneMappingList = anchorZoneMatchZoneMappingService.listByAnchorZoneId(id);
        List<AnchorZoneMatchZoneMappingModel> anchorZoneMatchZoneMappingModelList = new ArrayList<>();
        for (MatchZone matchZone : matchZoneList) {
            AnchorZoneMatchZoneMappingModel anchorZoneMatchZoneMappingModel = new AnchorZoneMatchZoneMappingModel();
            anchorZoneMatchZoneMappingModel.setChecked(false);
            anchorZoneMatchZoneMappingModel.setMatchZoneId(matchZone.getId());
            anchorZoneMatchZoneMappingModel.setAnchorZoneId(id);
            anchorZoneMatchZoneMappingModel.setMatchZoneName(matchZone.getName());
            for (AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping : anchorZoneMatchZoneMappingList) {
                if (anchorZoneMatchZoneMapping.getMatchZoneId().compareTo(matchZone.getId()) == 0) {
                    BeanUtils.copyProperties(anchorZoneMatchZoneMapping, anchorZoneMatchZoneMappingModel);
                    anchorZoneMatchZoneMappingModel.setChecked(true);
                    anchorZoneMatchZoneMappingModel.setMatchZoneName(matchZone.getName());
                }
            }
            anchorZoneMatchZoneMappingModelList.add(anchorZoneMatchZoneMappingModel);
        }

        return anchorZoneMatchZoneMappingModelList;
    }

    public List<AnchorZoneMatchZoneMappingModel> listAnchorZoneMatchZoneMappingModel() {
        List<MatchZone> matchZoneList = matchZoneService.listByIfAnchorMatch();
        List<AnchorZoneMatchZoneMappingModel> anchorZoneMatchZoneMappingModelList = new ArrayList<>();
        for (MatchZone matchZone : matchZoneList) {
            AnchorZoneMatchZoneMappingModel anchorZoneMatchZoneMappingModel = new AnchorZoneMatchZoneMappingModel();
            anchorZoneMatchZoneMappingModel.setChecked(false);
            anchorZoneMatchZoneMappingModel.setMatchZoneId(matchZone.getId());
            anchorZoneMatchZoneMappingModel.setMatchZoneName(matchZone.getName());
            anchorZoneMatchZoneMappingModelList.add(anchorZoneMatchZoneMappingModel);
        }

        return anchorZoneMatchZoneMappingModelList;
    }
}
