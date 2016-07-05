package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.zone.dao.MatchZoneBonusDao;
import com.ygccw.wechat.common.zone.entity.MatchZoneBonus;
import com.ygccw.wechat.common.zone.service.MatchZoneBonusService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchZoneBonusServiceImpl implements MatchZoneBonusService {
    @Inject
    private MatchZoneBonusDao matchZoneBonusDao;

    @Override
    public void save(MatchZoneBonus matchZoneBonus) {
        matchZoneBonus.setCreateTime(new Date());
        matchZoneBonus.setUpdateTime(new Date());
        matchZoneBonus.setStatus(1);
        matchZoneBonusDao.save(matchZoneBonus);
    }

    @Override
    public void update(MatchZoneBonus matchZoneBonus) {
        matchZoneBonus.setUpdateTime(new Date());
        matchZoneBonusDao.update(matchZoneBonus);
    }

    @Override
    public MatchZoneBonus findById(Long id) {
        return matchZoneBonusDao.findById(id);
    }


    @Override
    public List<MatchZoneBonus> listByMatchZoneId(Long matchZoneId) {
        return null;
    }

    @Override
    public void deleteByMatchZoneId(Long matchZoneId) {
        matchZoneBonusDao.deleteByMatchZoneId(matchZoneId);
    }

    @Override
    public void saveList(List<MatchZoneBonus> matchZoneBonusList) {
        for (MatchZoneBonus matchZoneBonus : matchZoneBonusList) {
            matchZoneBonus.setCreateTime(new Date());
            matchZoneBonus.setUpdateTime(new Date());
            matchZoneBonus.setStatus(1);
            matchZoneBonusDao.save(matchZoneBonus);
        }

    }
}
