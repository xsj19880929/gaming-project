package com.ygccw.wechat.zone.model;

import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.AnchorZoneHonor;
import com.ygccw.wechat.recommend.model.RecommendMappingModel;

import java.util.List;

/**
 * @author soldier
 */
public class AnchorZoneModel extends AnchorZone {
    private List<AnchorZoneHonor> anchorZoneHonorList;
    private List<RecommendMappingModel> recommendMappingModelList;
    private List<AnchorZoneMatchZoneMappingModel> anchorZoneMatchZoneMappingModelList;

    public List<RecommendMappingModel> getRecommendMappingModelList() {
        return recommendMappingModelList;
    }

    public void setRecommendMappingModelList(List<RecommendMappingModel> recommendMappingModelList) {
        this.recommendMappingModelList = recommendMappingModelList;
    }

    public List<AnchorZoneHonor> getAnchorZoneHonorList() {
        return anchorZoneHonorList;
    }

    public void setAnchorZoneHonorList(List<AnchorZoneHonor> anchorZoneHonorList) {
        this.anchorZoneHonorList = anchorZoneHonorList;
    }

    public List<AnchorZoneMatchZoneMappingModel> getAnchorZoneMatchZoneMappingModelList() {
        return anchorZoneMatchZoneMappingModelList;
    }

    public void setAnchorZoneMatchZoneMappingModelList(List<AnchorZoneMatchZoneMappingModel> anchorZoneMatchZoneMappingModelList) {
        this.anchorZoneMatchZoneMappingModelList = anchorZoneMatchZoneMappingModelList;
    }
}
