package com.ygccw.wechat.common.recommend.service;


import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendType;

import java.util.List;

/**
 * @author soldier
 */
public interface RecommendMappingService {
    void save(RecommendMapping recommendMapping);

    void update(RecommendMapping recommendMapping);

    void delete(Long id);

    RecommendMapping findById(Long id);

    void deleteStatus(Long id);

    List<RecommendMapping> list(RecommendMapping recommendMapping, int offset, int fetchSize);

    int listSize(RecommendMapping recommendMapping);

    List<RecommendMapping> listByEntityIdAndType(Long entityId, RecommendType recommendType);
}
