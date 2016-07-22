package com.ygccw.wechat.common.zone.service.impl;


import com.ygccw.wechat.common.zone.dao.MatchTeamMappingDao;
import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;
import com.ygccw.wechat.common.zone.service.MatchTeamMappingService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchTeamMappingServiceImpl implements MatchTeamMappingService {
    @Inject
    private MatchTeamMappingDao matchTeamMappingDao;

    @Override
    public void save(MatchTeamMapping matchTeamMapping) {
        matchTeamMapping.setCreateTime(new Date());
        matchTeamMapping.setUpdateTime(new Date());
        matchTeamMapping.setStatus(1);
        matchTeamMappingDao.save(matchTeamMapping);
    }

    @Override
    public void update(MatchTeamMapping matchTeamMapping) {
        matchTeamMapping.setUpdateTime(new Date());
        matchTeamMappingDao.update(matchTeamMapping);
    }

    @Override
    public void delete(Long id) {
        matchTeamMappingDao.delete(id);
    }

    @Override
    public MatchTeamMapping findById(Long id) {
        return matchTeamMappingDao.findById(id);
    }

    @Override
    public void deleteStatus(Long id) {
        matchTeamMappingDao.deleteStatus(id);
    }

    @Override
    public List<MatchTeamMapping> list(MatchTeamMapping matchTeamMapping, int offset, int fetchSize) {
        return matchTeamMappingDao.list(matchTeamMapping, offset, fetchSize);
    }

    @Override
    public int listSize(MatchTeamMapping matchTeamMapping) {
        return matchTeamMappingDao.listSize(matchTeamMapping);
    }

    @Override
    public List<MatchTeamMapping> listByMatchTeamId(Long matchTeamId) {
        return matchTeamMappingDao.listByMatchTeamId(matchTeamId);
    }

    @Override
    public List<MatchTeamMapping> listByMatchZoneId(Long matchZoneId) {
        return matchTeamMappingDao.listByMatchZoneId(matchZoneId);
    }
}
