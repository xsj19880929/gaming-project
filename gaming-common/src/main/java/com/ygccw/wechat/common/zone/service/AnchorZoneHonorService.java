package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.AnchorZoneHonor;

import java.util.List;

/**
 * @author soldier
 */
public interface AnchorZoneHonorService {
    void save(AnchorZoneHonor anchorZoneHonor);

    void update(AnchorZoneHonor anchorZoneHonor);

    AnchorZoneHonor findById(Long id);

    List<AnchorZoneHonor> listByAnchorZoneId(Long anchorZoneId);

    void saveList(List<AnchorZoneHonor> anchorZoneHonorList);

    void deleteByAnchorZoneId(Long anchorZoneId);
}
