package com.ygccw.wechat.common.tags.service.impl;


import com.ygccw.wechat.common.tags.dao.TagMappingDao;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author soldier
 */
@Service
public class TagMappingServiceImpl implements TagMappingService {
    @Inject
    private TagMappingDao tagMappingDao;

    @Override
    public void save(TagMapping tagMapping) {
        tagMapping.setCreateTime(new Date());
        tagMapping.setUpdateTime(new Date());
        tagMapping.setStatus(1);
        tagMappingDao.save(tagMapping);
    }

    @Override
    public void update(TagMapping tagMapping) {
        tagMapping.setUpdateTime(new Date());
        tagMappingDao.update(tagMapping);
    }

    @Override
    public void delete(Long id) {
        tagMappingDao.delete(id);
    }

    @Override
    public TagMapping findById(Long id) {
        return tagMappingDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        tagMappingDao.deleteStatus(id);
    }

    @Override
    public List<TagMapping> list(TagMapping tagMapping, int offset, int fetchSize) {
        return tagMappingDao.list(tagMapping, offset, fetchSize);
    }

    @Override
    public int listSize(TagMapping tagMapping) {
        return tagMappingDao.listSize(tagMapping);
    }

    @Override
    public List<TagMapping> listByTagsId(Long tagsId) {
        return tagMappingDao.listByTagsId(tagsId);
    }

    @Override
    public void deleteByEntityIdAndType(Long entityId, TagType tagType, TagZoneType tagZoneType) {
        tagMappingDao.deleteByEntityIdAndType(entityId, tagType, tagZoneType);
    }

    @Override
    public List<Map<String, Object>> listHotTags(TagMapping tagMapping, int offset, int fetchSize) {
        return tagMappingDao.listHotTags(tagMapping, offset, fetchSize);
    }

    @Override
    public List<TagMapping> listByTagsIdPaging(Long tagsId, int offset, int fetchSize) {
        return tagMappingDao.listByTagsIdPaging(tagsId, offset, fetchSize);

    }

    @Override
    public int listByTagsIdPagingSize(Long tagsId) {
        return tagMappingDao.listByTagsIdPagingSize(tagsId);
    }
}
