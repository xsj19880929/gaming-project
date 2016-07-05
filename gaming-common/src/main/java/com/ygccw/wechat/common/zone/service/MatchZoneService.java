package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.MatchZone;

import java.util.List;

/**
 * @author soldier
 */
public interface MatchZoneService {
    void save(MatchZone matchZone);

    void update(MatchZone matchZone);

    void delete(Long id);

    MatchZone findById(Long id);

    void deleteStatus(Long id);

    List<MatchZone> list(MatchZone matchZone, int offset, int fetchSize);

    int listSize(MatchZone matchZone);
}
