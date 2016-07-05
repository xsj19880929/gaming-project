package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.MatchZoneYear;

import java.util.List;

/**
 * @author soldier
 */
public interface MatchZoneYearService {
    void save(MatchZoneYear matchZoneYear);

    void update(MatchZoneYear matchZoneYear);

    void delete(Long id);

    MatchZoneYear findById(Long id);

    void deleteStatus(Long id);

    List<MatchZoneYear> list(MatchZoneYear matchZoneYear, int offset, int fetchSize);

    int listSize(MatchZoneYear matchZoneYear);

    List<MatchZoneYear> listAll();
}
