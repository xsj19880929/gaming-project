package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.zone.dao.MatchZoneCalendarDao;
import com.ygccw.wechat.common.zone.entity.MatchZoneCalendar;
import com.ygccw.wechat.common.zone.service.MatchZoneCalendarService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchZoneCalendarServiceImpl implements MatchZoneCalendarService {
    @Inject
    private MatchZoneCalendarDao matchZoneCalendarDao;

    @Override
    public void save(MatchZoneCalendar matchZoneCalendar) {
        matchZoneCalendar.setCreateTime(new Date());
        matchZoneCalendar.setUpdateTime(new Date());
        matchZoneCalendar.setStatus(1);
        matchZoneCalendarDao.save(matchZoneCalendar);
    }

    @Override
    public void update(MatchZoneCalendar matchZoneCalendar) {
        matchZoneCalendar.setUpdateTime(new Date());
        matchZoneCalendarDao.update(matchZoneCalendar);
    }

    @Override
    public MatchZoneCalendar findById(Long id) {
        return matchZoneCalendarDao.findById(id);
    }


    @Override
    public List<MatchZoneCalendar> listByMatchZoneId(Long matchZoneId) {
        return matchZoneCalendarDao.listByMatchZoneId(matchZoneId);
    }

    @Override
    public void deleteByMatchZoneId(Long matchZoneId) {
        matchZoneCalendarDao.deleteByMatchZoneId(matchZoneId);
    }

    @Override
    public void saveList(List<MatchZoneCalendar> matchZoneCalendarList) {
        for (MatchZoneCalendar matchZoneCalendar : matchZoneCalendarList) {
            matchZoneCalendar.setCreateTime(new Date());
            matchZoneCalendar.setUpdateTime(new Date());
            matchZoneCalendar.setStatus(1);
            matchZoneCalendarDao.save(matchZoneCalendar);
        }

    }
}
