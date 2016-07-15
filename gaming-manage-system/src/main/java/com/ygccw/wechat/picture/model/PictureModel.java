package com.ygccw.wechat.picture.model;

import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.entity.PictureDetail;
import com.ygccw.wechat.recommend.model.RecommendMappingModel;

import java.util.List;

/**
 * @author soldier
 */
public class PictureModel extends Picture {
    private List<PictureDetail> pictureDetailList;
    private List<RecommendMappingModel> recommendMappingModelList;

    public List<PictureDetail> getPictureDetailList() {
        return pictureDetailList;
    }

    public void setPictureDetailList(List<PictureDetail> pictureDetailList) {
        this.pictureDetailList = pictureDetailList;
    }

    public List<RecommendMappingModel> getRecommendMappingModelList() {
        return recommendMappingModelList;
    }

    public void setRecommendMappingModelList(List<RecommendMappingModel> recommendMappingModelList) {
        this.recommendMappingModelList = recommendMappingModelList;
    }
}
