package com.ygccw.website.pc.picture.service;

import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.service.PictureDetailService;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import com.ygccw.wechat.common.tags.service.TagsService;
import core.framework.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author soldier
 */
@Service
public class PictureWebService {
    @Inject
    PictureService pictureService;
    @Inject
    TagMappingService tagMappingService;
    @Inject
    TagsService tagsService;
    @Inject
    PictureDetailService pictureDetailService;

    public List<Picture> pictureList(int offset, int fetchSize) {
        Picture picture = new Picture();
        return pictureService.list(picture, offset, fetchSize);
    }

    public int pictureListSize() {
        Picture picture = new Picture();
        return pictureService.listSize(picture);
    }

    public List<Map<String, Object>> listHotTags() {
        TagMapping tagMapping = new TagMapping();
        tagMapping.setTagType(TagType.picture);
        return tagMappingService.listHotTags(tagMapping, 0, 10);
    }

    public List<Picture> pictureListTop(int offset, int fetchSize) {
        Picture picture = new Picture();
        picture.setSortIfDesc(true);
        picture.setSortName("visitCount");
        return pictureService.list(picture, offset, fetchSize);
    }

    public Picture findById(Long id) {
        Picture picture = pictureService.findById(id);
        picture.setPictureDetailList(pictureDetailService.listByPictureId(id));
        return picture;
    }


    public Set<Picture> likePictureList(Picture picture, int fetchSize) {
        Set<Picture> pictureList = new HashSet<>(fetchSize);
        Map<Long, Picture> mappingMap = new HashMap<>(fetchSize);
        if (StringUtils.hasText(picture.getTags())) {
            String[] tagArray = picture.getTags().split(" ");
            TagZoneType tagZoneTypeRequest = null;
            for (TagZoneType tagZoneType : TagZoneType.values()) {
                if (picture.getPictureZoneType() != null && tagZoneType.getName().equals(picture.getPictureZoneType().getName())) {
                    tagZoneTypeRequest = tagZoneType;
                }
            }
            for (String tagName : tagArray) {
                Tags tags = tagsService.findByName(tagName, TagType.picture, tagZoneTypeRequest);
                if (tags == null) continue;
                List<TagMapping> tagMappingList = tagMappingService.listByTagsId(tags.getId());
                for (TagMapping tagMapping : tagMappingList) {
                    Picture pictureNew = pictureService.findById(tagMapping.getEntityId());
                    mappingMap.put(tagMapping.getEntityId(), pictureNew);
                    if (mappingMap.size() == fetchSize) {
                        break;
                    }
                }
            }
        }
        mappingMap.remove(picture.getId());
        for (Map.Entry<Long, Picture> entry : mappingMap.entrySet()) {
            pictureList.add(entry.getValue());
        }
        return pictureList;

    }

}
