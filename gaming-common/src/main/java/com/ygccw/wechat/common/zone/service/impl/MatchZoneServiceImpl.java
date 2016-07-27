package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.zone.dao.MatchZoneAreaDao;
import com.ygccw.wechat.common.zone.dao.MatchZoneDao;
import com.ygccw.wechat.common.zone.dao.MatchZoneYearDao;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchZoneServiceImpl implements MatchZoneService {
    @Inject
    private MatchZoneDao matchZoneDao;
    @Inject
    private MatchZoneYearDao matchZoneYearDao;
    @Inject
    private MatchZoneAreaDao matchZoneAreaDao;

    @Override
    public void save(MatchZone matchZone) {
        saveAndUpdateSetDate(matchZone);
        matchZone.setCreateTime(new Date());
        matchZone.setUpdateTime(new Date());
        matchZone.setStatus(1);
        matchZoneDao.save(matchZone);
    }

    @Override
    public void update(MatchZone matchZone) {
        saveAndUpdateSetDate(matchZone);
        matchZone.setUpdateTime(new Date());
        matchZoneDao.update(matchZone);
    }

    private void saveAndUpdateSetDate(MatchZone matchZone) {
        if (matchZone.getMatchZoneYearId() != null) {
            matchZone.setMatchZoneYearName(matchZoneYearDao.findById(matchZone.getMatchZoneYearId()).getYearName());
        }
        if (matchZone.getMatchZoneAreaId() != null) {
            matchZone.setMatchZoneAreaName(matchZoneAreaDao.findById(matchZone.getMatchZoneAreaId()).getAreaName());
        }
        if (matchZone.getIfAnchorMatch() == null) {
            matchZone.setIfAnchorMatch(false);
        }
        if (matchZone.getIfStart() == null) {
            matchZone.setIfStart(false);
        }

    }

    @Override
    public void delete(Long id) {
        matchZoneDao.delete(id);
    }

    @Override
    public MatchZone findById(Long id) {
        return matchZoneDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        matchZoneDao.deleteStatus(id);
    }

    @Override
    public List<MatchZone> list(MatchZone matchZone, int offset, int fetchSize) {
        return matchZoneDao.list(matchZone, offset, fetchSize);
    }

    @Override
    public int listSize(MatchZone matchZone) {
        return matchZoneDao.listSize(matchZone);
    }

    @Override
    public List<MatchZone> listAll() {
        return matchZoneDao.listAll();
    }

    @Override
    public List<MatchZone> listByIfAnchorMatch() {
        return matchZoneDao.listByIfAnchorMatch();
    }

    public List<MatchZone> listOrderByVisit(int offset, int fetchSize) {
        return matchZoneDao.listOrderByVisit(offset, fetchSize);
    }
}
