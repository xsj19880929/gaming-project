package com.ygccw.wechat.picture.service;

import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.entity.PictureDetail;
import com.ygccw.wechat.common.picture.service.PictureDetailService;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.recommend.entity.Recommend;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendMappingService;
import com.ygccw.wechat.common.recommend.service.RecommendService;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import com.ygccw.wechat.common.tags.service.TagsService;
import com.ygccw.wechat.picture.model.PictureModel;
import com.ygccw.wechat.recommend.model.RecommendMappingModel;
import com.ygccw.wechat.recommend.service.RecommendMappingModelService;
import core.framework.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class PictureModelService {
    @Inject
    private PictureService pictureService;
    @Inject
    private RecommendMappingModelService recommendMappingModelService;
    @Inject
    private RecommendService recommendService;
    @Inject
    private RecommendMappingService recommendMappingService;
    @Inject
    private PictureDetailService pictureDetailService;
    @Inject
    private TagsService tagsService;
    @Inject
    private TagMappingService tagMappingService;


    @Transactional
    public void save(PictureModel pictureModel) {
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureModel, picture);
        pictureService.save(picture);
        if (pictureModel.getPictureDetailList() != null) {
            for (PictureDetail pictureDetail : pictureModel.getPictureDetailList()) {
                pictureDetail.setPictureId(picture.getId());
                pictureDetailService.save(pictureDetail);
            }
        }
        if (pictureModel.getRecommendMappingModelList() != null) {
            for (RecommendMappingModel recommendMappingModel : pictureModel.getRecommendMappingModelList()) {
                if (recommendMappingModel.getChecked()) {
                    RecommendMapping recommendMapping = new RecommendMapping();
                    BeanUtils.copyProperties(recommendMappingModel, recommendMapping);
                    recommendMapping.setEntityId(picture.getId());
                    recommendMappingService.save(recommendMapping);
                }
            }
        }
        saveTags(pictureModel.getTags(), picture.getId());
    }

    @Transactional
    public void update(PictureModel pictureModel) {
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureModel, picture);
        pictureService.update(picture);
        pictureDetailService.deleteByPictureId(picture.getId());
        if (pictureModel.getPictureDetailList() != null) {
            for (PictureDetail pictureDetail : pictureModel.getPictureDetailList()) {
                pictureDetail.setPictureId(picture.getId());
                pictureDetail.setId(null);
                pictureDetailService.save(pictureDetail);
            }
        }
        if (pictureModel.getRecommendMappingModelList() != null) {
            saveOrUpdateRecommendMapping(pictureModel.getRecommendMappingModelList());
        }
        saveTags(pictureModel.getTags(), picture.getId());
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


    public PictureModel findById(Long id) {
        Picture picture = pictureService.findById(id);
        PictureModel pictureModel = new PictureModel();
        BeanUtils.copyProperties(picture, pictureModel);
        pictureModel.setPictureDetailList(pictureDetailService.listByPictureId(picture.getId()));
        List<Recommend> recommendList = recommendService.listByType(RecommendType.picture);
        List<RecommendMappingModel> recommendMappingModelList = recommendMappingModelService.listByEntityAndTyp(id, RecommendType.picture);
        List<RecommendMappingModel> recommendMappingModelListNew = new ArrayList<>();
        for (Recommend recommend : recommendList) {
            RecommendMappingModel recommendMappingModelNew = new RecommendMappingModel();
            recommendMappingModelNew.setChecked(false);
            recommendMappingModelNew.setRecommendName(recommend.getName());
            recommendMappingModelNew.setEntityId(id);
            recommendMappingModelNew.setRecommendId(recommend.getId());
            recommendMappingModelNew.setRecommendType(RecommendType.picture);
            for (RecommendMappingModel recommendMappingModel : recommendMappingModelList) {
                if (recommendMappingModel.getRecommendId().compareTo(recommend.getId()) == 0) {
                    BeanUtils.copyProperties(recommendMappingModel, recommendMappingModelNew);
                    recommendMappingModelNew.setChecked(true);
                }
            }
            recommendMappingModelListNew.add(recommendMappingModelNew);
        }
        pictureModel.setRecommendMappingModelList(recommendMappingModelListNew);
        return pictureModel;

    }

    private void saveTags(String tags, Long entityId) {
        if (StringUtils.hasText(tags)) {
            String[] tagArray = tags.split(",| ");
            List<String> tagList = Arrays.asList(tagArray);
            tagMappingService.deleteByEntityIdAndType(entityId, TagType.picture, null);
            for (String tag : tagList) {
                Tags tagsEntity = tagsService.findByName(tag, TagType.picture, null);
                if (tagsEntity == null) {
                    tagsEntity = new Tags();
                    tagsEntity.setTagType(TagType.picture);
                    tagsEntity.setName(tag);
                    tagsService.save(tagsEntity);
                }
                TagMapping tagMapping = new TagMapping();
                tagMapping.setEntityId(entityId);
                tagMapping.setTagsId(tagsEntity.getId());
                tagMapping.setTagType(TagType.picture);
                tagMappingService.save(tagMapping);
            }

        }

    }
}
