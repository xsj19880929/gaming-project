package com.ygccw.wechat.info.model;

import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.recommend.model.RecommendMappingModel;

import java.util.List;

/**
 * @author soldier
 */
public class InfoModel extends Info {
    private List<RecommendMappingModel> recommendMappingModelList;
    private String zoneName;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public List<RecommendMappingModel> getRecommendMappingModelList() {
        return recommendMappingModelList;
    }

    public void setRecommendMappingModelList(List<RecommendMappingModel> recommendMappingModelList) {
        this.recommendMappingModelList = recommendMappingModelList;
    }
}
