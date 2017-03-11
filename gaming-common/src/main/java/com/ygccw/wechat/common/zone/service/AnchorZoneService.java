package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.AnchorZone;

import java.util.List;

/**
 * @author soldier
 */
public interface AnchorZoneService {
    void updateVisitCount(Long id);

    void save(AnchorZone anchorZone);

    void update(AnchorZone anchorZone);

    void delete(Long id);

    AnchorZone findById(Long id);

    void deleteStatus(Long id);

    List<AnchorZone> list(AnchorZone anchorZone, int offset, int fetchSize);

    int listSize(AnchorZone anchorZone);

    List<AnchorZone> listAll();

    AnchorZone findByUuId(String uuid);
}
