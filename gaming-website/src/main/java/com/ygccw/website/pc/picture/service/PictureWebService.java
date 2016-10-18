package com.ygccw.website.pc.picture.service;

import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * @author soldier
 */
@Service
public class PictureWebService {
    @Inject
    PictureService pictureService;
    @Inject
    TagMappingService tagMappingService;

    public List<Picture> pictureList(int offset, int fetchSize) {
        Picture picture = new Picture();
        return pictureService.list(picture, offset, fetchSize);
    }

    public List<Map<String, Object>> listHotTags() {
        TagMapping tagMapping = new TagMapping();
        tagMapping.setTagType(TagType.picture);
        return tagMappingService.listHotTags(tagMapping, 0, 10);
    }

}
