package com.ygccw.wechat.common.tags.service;


import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;

import java.util.List;
import java.util.Map;

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

    List<TagMapping> listByTagsId(Long tagsId, int offset, int fetchSize);

    List<Map<String, Object>> listHotTags(TagMapping tagMapping, int offset, int fetchSize);

    List<TagMapping> listByTagsIdPaging(Long tagsId, int offset, int fetchSize);

    int listByTagsIdPagingSize(Long tagsId);
}
