package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.AnchorZoneMatchZoneMapping;

import java.util.List;

/**
 * @author soldier
 */
public interface AnchorZoneMatchZoneMappingService {
    void save(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping);

    void update(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping);

    void delete(Long id);

    AnchorZoneMatchZoneMapping findById(Long id);

    void deleteStatus(Long id);

    List<AnchorZoneMatchZoneMapping> list(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping, int offset, int fetchSize);

    int listSize(AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping);

    List<AnchorZoneMatchZoneMapping> listByAnchorZoneId(Long anchorZoneId);

    void deleteByMatchZoneId(Long matchZoneId);

    void deleteByAnchorZoneId(Long anchorZoneId);
}
