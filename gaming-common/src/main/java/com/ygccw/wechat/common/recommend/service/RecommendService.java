package com.ygccw.wechat.common.recommend.service;


import com.ygccw.wechat.common.recommend.entity.Recommend;
import com.ygccw.wechat.common.recommend.enums.RecommendType;

import java.util.List;

/**
 * @author soldier
 */
public interface RecommendService {
    void save(Recommend recommend);

    void update(Recommend recommend);

    void delete(Long id);

    Recommend findById(Long id);

    void deleteStatus(Long id);

    List<Recommend> list(Recommend recommend, int offset, int fetchSize);

    int listSize(Recommend recommend);

    List<Recommend> listByType(RecommendType recommendType);
}
