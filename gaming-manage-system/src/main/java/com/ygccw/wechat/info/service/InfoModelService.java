package com.ygccw.wechat.info.service;

import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.recommend.entity.Recommend;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendMappingService;
import com.ygccw.wechat.common.recommend.service.RecommendService;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import com.ygccw.wechat.common.tags.service.TagsService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import com.ygccw.wechat.info.model.InfoModel;
import com.ygccw.wechat.recommend.model.RecommendMappingModel;
import com.ygccw.wechat.recommend.service.RecommendMappingModelService;
import core.framework.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class InfoModelService {
    @Inject
    private InfoService infoService;
    @Inject
    private RecommendMappingModelService recommendMappingModelService;
    @Inject
    private RecommendService recommendService;
    @Inject
    private RecommendMappingService recommendMappingService;
    @Inject
    private TagsService tagsService;
    @Inject
    private TagMappingService tagMappingService;
    @Inject
    private MatchZoneService matchZoneService;
    @Inject
    private AnchorZoneService anchorZoneService;


    @Transactional
    public void save(InfoModel infoModel) {
        Info info = new Info();
        BeanUtils.copyProperties(infoModel, info);
        if (!StringUtils.hasText(info.getTitleImage())) {
            info.setTitleImage(getContentImageFirst(info.getContent()));
        }
        infoService.save(info);
        if (infoModel.getRecommendMappingModelList() != null) {
            for (RecommendMappingModel recommendMappingModel : infoModel.getRecommendMappingModelList()) {
                if (recommendMappingModel.getChecked()) {
                    RecommendMapping recommendMapping = new RecommendMapping();
                    BeanUtils.copyProperties(recommendMappingModel, recommendMapping);
                    recommendMapping.setEntityId(info.getId());
                    recommendMappingService.save(recommendMapping);
                }
            }
        }

        saveTags(infoModel.getTags(), info.getId(), changeTagType(info.getInfoType()), changeTagZoneType(info.getInfoZoneType()));
    }

    @Transactional
    public void update(InfoModel infoModel) {
        Info info = new Info();
        BeanUtils.copyProperties(infoModel, info);
        if (!StringUtils.hasText(info.getTitleImage())) {
            info.setTitleImage(getContentImageFirst(info.getContent()));
        }
        infoService.update(info);
        if (infoModel.getRecommendMappingModelList() != null) {
            saveOrUpdateRecommendMapping(infoModel.getRecommendMappingModelList());
        }
        saveTags(infoModel.getTags(), info.getId(), changeTagType(info.getInfoType()), changeTagZoneType(info.getInfoZoneType()));
    }

    private TagType changeTagType(InfoType infoType) {
        TagType tagType = null;
        if (InfoType.news.toString().equals(infoType.name().toString())) {
            tagType = TagType.news;
        } else if (InfoType.video.toString().equals(infoType.name().toString())) {
            tagType = TagType.video;
        }
        return tagType;
    }

    private TagZoneType changeTagZoneType(InfoZoneType infoZoneType) {
        TagZoneType tagZoneType = null;
        if (InfoZoneType.matchZone.toString().equals(infoZoneType.name().toString())) {
            tagZoneType = TagZoneType.matchZone;
        } else if (InfoZoneType.anchorZone.toString().equals(infoZoneType.name().toString())) {
            tagZoneType = TagZoneType.anchorZone;
        } else if (InfoZoneType.trade.toString().equals(infoZoneType.name().toString())) {
            tagZoneType = TagZoneType.trade;
        }
        return tagZoneType;
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


    public InfoModel findById(Long id) {
        Info info = infoService.findById(id);
        InfoModel infoModel = new InfoModel();
        BeanUtils.copyProperties(info, infoModel);
        List<Recommend> recommendList = recommendService.listByType(RecommendType.news);
        List<RecommendMappingModel> recommendMappingModelList = recommendMappingModelService.listByEntityAndTyp(id, RecommendType.news);
        List<RecommendMappingModel> recommendMappingModelListNew = new ArrayList<>();
        for (Recommend recommend : recommendList) {
            RecommendMappingModel recommendMappingModelNew = new RecommendMappingModel();
            recommendMappingModelNew.setChecked(false);
            recommendMappingModelNew.setRecommendName(recommend.getName());
            recommendMappingModelNew.setEntityId(id);
            recommendMappingModelNew.setRecommendId(recommend.getId());
            recommendMappingModelNew.setRecommendType(RecommendType.news);
            for (RecommendMappingModel recommendMappingModel : recommendMappingModelList) {
                if (recommendMappingModel.getRecommendId().compareTo(recommend.getId()) == 0) {
                    BeanUtils.copyProperties(recommendMappingModel, recommendMappingModelNew);
                    recommendMappingModelNew.setChecked(true);
                }
            }
            recommendMappingModelListNew.add(recommendMappingModelNew);
        }
        infoModel.setRecommendMappingModelList(recommendMappingModelListNew);
        return infoModel;

    }

    private void saveTags(String tags, Long entityId, TagType tagType, TagZoneType tagZoneType) {
        if (StringUtils.hasText(tags)) {
            String[] tagArray = tags.split(",| ");
            List<String> tagList = Arrays.asList(tagArray);
            tagMappingService.deleteByEntityIdAndType(entityId, tagType, tagZoneType);
            for (String tag : tagList) {
                Tags tagsEntity = tagsService.findByName(tag, tagType, tagZoneType);
                if (tagsEntity == null) {
                    tagsEntity = new Tags();
                    tagsEntity.setTagType(tagType);
                    tagsEntity.setName(tag);
                    tagsEntity.setTagZoneType(tagZoneType);
                    tagsService.save(tagsEntity);
                }
                TagMapping tagMapping = new TagMapping();
                tagMapping.setEntityId(entityId);
                tagMapping.setTagsId(tagsEntity.getId());
                tagMapping.setTagType(tagType);
                tagMapping.setTagZoneType(tagZoneType);
                tagMappingService.save(tagMapping);
            }

        }

    }

    public List<InfoModel> list(Info infoRequest, int offset, int fetchSize) {
        List<Info> list = infoService.list(infoRequest, offset, fetchSize);
        List<InfoModel> infoModelList = new ArrayList<>();
        for (Info info : list) {
            InfoModel infoModel = new InfoModel();
            BeanUtils.copyProperties(info, infoModel);
            if (InfoZoneType.matchZone.getName().compareTo(info.getInfoZoneType().getName()) == 0) {
                MatchZone matchZone = matchZoneService.findById(info.getZoneId());
                infoModel.setZoneName(matchZone.getName());
            } else if (InfoZoneType.anchorZone.getName().compareTo(info.getInfoZoneType().getName()) == 0) {
                AnchorZone anchorZone = anchorZoneService.findById(info.getZoneId());
                infoModel.setZoneName(anchorZone.getName());
            }
            infoModelList.add(infoModel);
        }
        return infoModelList;
    }

    private String getContentImageFirst(String content) {
        Document document = Jsoup.parse(content);
        Element element = document.select("img").first();
        if (element == null) {
            return null;
        }
        String imagePath = element.attr("src");
        List<String> list = Arrays.asList(imagePath.split("/file"));
        return "/file" + list.get(1);

    }
}
