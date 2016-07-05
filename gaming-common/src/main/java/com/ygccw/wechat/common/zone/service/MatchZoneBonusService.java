package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.MatchZoneBonus;

import java.util.List;

/**
 * @author soldier
 */
public interface MatchZoneBonusService {
    void save(MatchZoneBonus matchZoneBonus);

    void update(MatchZoneBonus matchZoneBonus);

    MatchZoneBonus findById(Long id);

    List<MatchZoneBonus> listByMatchZoneId(Long matchZoneId);

    void saveList(List<MatchZoneBonus> matchZoneBonusList);

    void deleteByMatchZoneId(Long matchZoneId);
}
