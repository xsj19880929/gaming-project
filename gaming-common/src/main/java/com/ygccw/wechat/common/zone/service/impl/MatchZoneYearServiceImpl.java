package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.zone.dao.MatchZoneYearDao;
import com.ygccw.wechat.common.zone.entity.MatchZoneYear;
import com.ygccw.wechat.common.zone.service.MatchZoneYearService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchZoneYearServiceImpl implements MatchZoneYearService {
    @Inject
    private MatchZoneYearDao matchZoneYearDao;

    @Override
    public void save(MatchZoneYear matchZoneYear) {
        matchZoneYear.setCreateTime(new Date());
        matchZoneYear.setUpdateTime(new Date());
        matchZoneYear.setStatus(1);
        matchZoneYearDao.save(matchZoneYear);
    }

    @Override
    public void update(MatchZoneYear matchZoneYear) {
        matchZoneYear.setUpdateTime(new Date());
        matchZoneYearDao.update(matchZoneYear);
    }

    @Override
    public void delete(Long id) {
        matchZoneYearDao.delete(id);
    }

    @Override
    public MatchZoneYear findById(Long id) {
        return matchZoneYearDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        matchZoneYearDao.deleteStatus(id);
    }

    @Override
    public List<MatchZoneYear> list(MatchZoneYear matchZoneYear, int offset, int fetchSize) {
        return matchZoneYearDao.list(matchZoneYear, offset, fetchSize);
    }

    @Override
    public int listSize(MatchZoneYear matchZoneYear) {
        return matchZoneYearDao.listSize(matchZoneYear);
    }

    @Override
    public List<MatchZoneYear> listAll() {
        return matchZoneYearDao.listAll();
    }
}
