package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.MatchZoneCalendar;

import java.util.List;

/**
 * @author soldier
 */
public interface MatchZoneCalendarService {
    void save(MatchZoneCalendar matchZoneCalendar);

    void update(MatchZoneCalendar matchZoneCalendar);

    MatchZoneCalendar findById(Long id);

    List<MatchZoneCalendar> listByMatchZoneId(Long matchZoneId);

    void saveList(List<MatchZoneCalendar> matchZoneCalendarList);

    void deleteByMatchZoneId(Long matchZoneId);
}
