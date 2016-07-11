package com.ygccw.wechat.zone.service;

import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.entity.MatchZoneBonus;
import com.ygccw.wechat.common.zone.entity.MatchZoneCalendar;
import com.ygccw.wechat.common.zone.service.MatchZoneBonusService;
import com.ygccw.wechat.common.zone.service.MatchZoneCalendarService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import com.ygccw.wechat.zone.model.MatchZoneModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchZoneModelService {
    @Inject
    private MatchZoneService matchZoneService;
    @Inject
    private MatchZoneBonusService matchZoneBonusService;
    @Inject
    private MatchZoneCalendarService matchZoneCalendarService;


    @Transactional
    public void save(MatchZoneModel matchZoneModel) {
        MatchZone matchZone = new MatchZone();
        BeanUtils.copyProperties(matchZoneModel, matchZone);
        matchZoneService.save(matchZone);
        for (MatchZoneBonus matchZoneBonus : matchZoneModel.getMatchZoneBonusList()) {
            matchZoneBonus.setMatchZoneId(matchZone.getId());
            matchZoneBonusService.save(matchZoneBonus);
        }
        for (MatchZoneCalendar matchZoneCalendar : matchZoneModel.getMatchZoneCalendarList()) {
            matchZoneCalendar.setMatchZoneId(matchZone.getId());
            matchZoneCalendarService.save(matchZoneCalendar);
        }
    }

    @Transactional
    public void update(MatchZoneModel matchZoneModel) {
        MatchZone matchZone = new MatchZone();
        BeanUtils.copyProperties(matchZoneModel, matchZone);
        matchZoneService.update(matchZone);
        matchZoneBonusService.deleteByMatchZoneId(matchZone.getId());
        for (MatchZoneBonus matchZoneBonus : matchZoneModel.getMatchZoneBonusList()) {
            matchZoneBonus.setMatchZoneId(matchZone.getId());
            matchZoneBonus.setId(null);
            matchZoneBonusService.save(matchZoneBonus);
        }
        matchZoneCalendarService.deleteByMatchZoneId(matchZone.getId());
        for (MatchZoneCalendar matchZoneCalendar : matchZoneModel.getMatchZoneCalendarList()) {
            matchZoneCalendar.setMatchZoneId(matchZone.getId());
            matchZoneCalendar.setId(null);
            matchZoneCalendarService.save(matchZoneCalendar);
        }
    }

    public List<MatchZoneModel> list(MatchZone matchZoneRequest, int offset, int fetchSize) {
        List<MatchZone> list = matchZoneService.list(matchZoneRequest, offset, fetchSize);
        List<MatchZoneModel> modelList = new ArrayList<>();
        for (MatchZone matchZone : list) {
            MatchZoneModel matchZoneModel = new MatchZoneModel();
            BeanUtils.copyProperties(matchZone, matchZoneModel);
            matchZoneModel.setMatchZoneBonusList(matchZoneBonusService.listByMatchZoneId(matchZone.getId()));
            matchZoneModel.setMatchZoneCalendarList(matchZoneCalendarService.listByMatchZoneId(matchZone.getId()));
            modelList.add(matchZoneModel);
        }
        return modelList;
    }

    public MatchZoneModel findById(Long id) {
        MatchZone matchZone = matchZoneService.findById(id);
        MatchZoneModel matchZoneModel = new MatchZoneModel();
        BeanUtils.copyProperties(matchZone, matchZoneModel);
        matchZoneModel.setMatchZoneBonusList(matchZoneBonusService.listByMatchZoneId(matchZone.getId()));
        matchZoneModel.setMatchZoneCalendarList(matchZoneCalendarService.listByMatchZoneId(matchZone.getId()));
        return matchZoneModel;

    }
}
