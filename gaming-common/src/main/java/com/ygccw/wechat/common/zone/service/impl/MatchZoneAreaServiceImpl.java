package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.zone.dao.MatchZoneAreaDao;
import com.ygccw.wechat.common.zone.entity.MatchZoneArea;
import com.ygccw.wechat.common.zone.service.MatchZoneAreaService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchZoneAreaServiceImpl implements MatchZoneAreaService {
    @Inject
    private MatchZoneAreaDao matchZoneAreaDao;

    @Override
    public void save(MatchZoneArea matchZoneArea) {
        matchZoneArea.setCreateTime(new Date());
        matchZoneArea.setUpdateTime(new Date());
        matchZoneArea.setStatus(1);
        matchZoneAreaDao.save(matchZoneArea);
    }

    @Override
    public void update(MatchZoneArea matchZoneArea) {
        matchZoneArea.setUpdateTime(new Date());
        matchZoneAreaDao.update(matchZoneArea);
    }

    @Override
    public void delete(Long id) {
        matchZoneAreaDao.delete(id);
    }

    @Override
    public MatchZoneArea findById(Long id) {
        return matchZoneAreaDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        matchZoneAreaDao.deleteStatus(id);
    }

    @Override
    public List<MatchZoneArea> list(MatchZoneArea matchZoneArea, int offset, int fetchSize) {
        return matchZoneAreaDao.list(matchZoneArea, offset, fetchSize);
    }

    @Override
    public int listSize(MatchZoneArea matchZoneArea) {
        return matchZoneAreaDao.listSize(matchZoneArea);
    }

    @Override
    public List<MatchZoneArea> listAll() {
        return matchZoneAreaDao.listAll();
    }
}
