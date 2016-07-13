package com.ygccw.wechat.recommend.service;

import com.ygccw.wechat.common.recommend.entity.Recommend;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendMappingService;
import com.ygccw.wechat.common.recommend.service.RecommendService;
import com.ygccw.wechat.recommend.model.RecommendMappingModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class RecommendMappingModelService {
    @Inject
    private RecommendMappingService recommendMappingService;
    @Inject
    private RecommendService recommendService;

    public List<RecommendMappingModel> listByEntityAndTyp(Long entityId, RecommendType recommendType) {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByEntityIdAndType(entityId, recommendType);
        List<RecommendMappingModel> recommendMappingModelList = new ArrayList<>();
        for (RecommendMapping recommendMapping : recommendMappingList) {
            RecommendMappingModel recommendMappingModel = new RecommendMappingModel();
            BeanUtils.copyProperties(recommendMapping, recommendMappingModel);
            Recommend recommend = recommendService.findById(recommendMapping.getRecommendId());
            recommendMappingModel.setRecommendName(recommend.getName());
            recommendMappingModelList.add(recommendMappingModel);
        }
        return recommendMappingModelList;

    }

    public List<RecommendMappingModel> listRecommendMapping(RecommendType recommendType) {
        List<Recommend> recommendList = recommendService.listByType(recommendType);
        List<RecommendMappingModel> recommendMappingModelListNew = new ArrayList<>();
        for (Recommend recommend : recommendList) {
            RecommendMappingModel recommendMappingModelNew = new RecommendMappingModel();
            recommendMappingModelNew.setChecked(false);
            recommendMappingModelNew.setRecommendName(recommend.getName());
            recommendMappingModelNew.setRecommendId(recommend.getId());
            recommendMappingModelNew.setRecommendType(recommendType);
            recommendMappingModelListNew.add(recommendMappingModelNew);
        }
        return recommendMappingModelListNew;

    }

}
