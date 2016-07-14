package com.ygccw.wechat.common.tags.service;


import com.ygccw.wechat.common.tags.entity.Tags;

import java.util.List;

/**
 * @author soldier
 */
public interface TagsService {
    void save(Tags tags);

    void update(Tags tags);

    void delete(Long id);

    Tags findById(Long id);

    void deleteStatus(Long id);

    List<Tags> list(Tags tags, int offset, int fetchSize);

    int listSize(Tags tags);

}
