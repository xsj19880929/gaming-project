package com.ygccw.wechat.recommend.model;

import com.ygccw.wechat.common.recommend.entity.RecommendMapping;

/**
 * @author soldier
 */
public class RecommendMappingModel extends RecommendMapping {
    private String recommendName;

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }
}
