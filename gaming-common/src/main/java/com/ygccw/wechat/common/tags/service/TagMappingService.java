package com.ygccw.wechat.common.tags.service;


import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;

import java.util.List;

/**
 * @author soldier
 */
public interface TagMappingService {
    void deleteByEntityIdAndType(Long entityId, TagType tagType, TagZoneType tagZoneType);

    void save(TagMapping tagMapping);

    void update(TagMapping tagMapping);

    void delete(Long id);

    TagMapping findById(Long id);

    void deleteStatus(Long id);

    List<TagMapping> list(TagMapping tagMapping, int offset, int fetchSize);

    int listSize(TagMapping tagMapping);

    List<TagMapping> listByTagsId(Long tagsId);
}
