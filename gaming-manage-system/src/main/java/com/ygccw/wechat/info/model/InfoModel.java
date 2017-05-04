package com.ygccw.wechat.info.model;

import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.recommend.model.RecommendMappingModel;

import java.util.List;

/**
 * @author soldier
 */
public class InfoModel extends Info {
    private List<Long> ids;
    private List<RecommendMappingModel> recommendMappingModelList;
    private String zoneName;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

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
