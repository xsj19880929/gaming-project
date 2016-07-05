package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.MatchZoneArea;

import java.util.List;

/**
 * @author soldier
 */
public interface MatchZoneAreaService {
    void save(MatchZoneArea matchZoneArea);

    void update(MatchZoneArea matchZoneArea);

    void delete(Long id);

    MatchZoneArea findById(Long id);

    void deleteStatus(Long id);

    List<MatchZoneArea> list(MatchZoneArea matchZoneArea, int offset, int fetchSize);

    int listSize(MatchZoneArea matchZoneArea);

    List<MatchZoneArea> listAll();
}
