package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.AnchorZonePlatform;

import java.util.List;

/**
 * @author soldier
 */
public interface AnchorZonePlatformService {
    void save(AnchorZonePlatform anchorZonePlatform);

    void update(AnchorZonePlatform anchorZonePlatform);

    void delete(Long id);

    AnchorZonePlatform findById(Long id);

    void deleteStatus(Long id);

    List<AnchorZonePlatform> list(AnchorZonePlatform anchorZonePlatform, int offset, int fetchSize);

    int listSize(AnchorZonePlatform anchorZonePlatform);

    List<AnchorZonePlatform> listAll();
}
