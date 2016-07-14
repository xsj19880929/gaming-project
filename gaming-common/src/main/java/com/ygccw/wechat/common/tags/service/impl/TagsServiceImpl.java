package com.ygccw.wechat.common.tags.service.impl;


import com.ygccw.wechat.common.tags.dao.TagsDao;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.service.TagsService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class TagsServiceImpl implements TagsService {
    @Inject
    private TagsDao tagsDao;

    @Override
    public void save(Tags tags) {
        tags.setCreateTime(new Date());
        tags.setUpdateTime(new Date());
        tags.setStatus(1);
        tagsDao.save(tags);
    }

    @Override
    public void update(Tags tags) {
        tags.setUpdateTime(new Date());
        tagsDao.update(tags);
    }

    @Override
    public void delete(Long id) {
        tagsDao.delete(id);
    }

    @Override
    public Tags findById(Long id) {
        return tagsDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        tagsDao.deleteStatus(id);
    }

    @Override
    public List<Tags> list(Tags tags, int offset, int fetchSize) {
        return tagsDao.list(tags, offset, fetchSize);
    }

    @Override
    public int listSize(Tags tags) {
        return tagsDao.listSize(tags);
    }

}
