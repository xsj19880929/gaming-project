package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.zone.dao.MatchTeamDao;
import com.ygccw.wechat.common.zone.dao.MatchTeamMappingDao;
import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;
import com.ygccw.wechat.common.zone.service.MatchTeamService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchTeamServiceImpl implements MatchTeamService {
    @Inject
    private MatchTeamDao matchTeamDao;
    @Inject
    private MatchTeamMappingDao matchTeamMappingDao;

    @Override
    public void save(MatchTeam matchTeam) {
        matchTeam.setCreateTime(new Date());
        matchTeam.setUpdateTime(new Date());
        matchTeam.setStatus(1);
        matchTeamDao.save(matchTeam);
    }

    @Override
    public void update(MatchTeam matchTeam) {
        matchTeam.setUpdateTime(new Date());
        matchTeamDao.update(matchTeam);
    }

    @Override
    public void delete(Long id) {
        matchTeamDao.delete(id);
    }

    @Override
    public MatchTeam findById(Long id) {
        return matchTeamDao.findById(id);
    }

    @Override
    @Transactional
    public void deleteStatus(Long id) {
        matchTeamDao.deleteStatus(id);
        List<MatchTeamMapping> matchTeamMappingList = matchTeamMappingDao.listByMatchTeamId(id);
        for (MatchTeamMapping matchTeamMapping : matchTeamMappingList) {
            matchTeamMappingDao.delete(matchTeamMapping.getId());
        }
    }

    @Override
    public List<MatchTeam> list(MatchTeam matchTeam, int offset, int fetchSize) {
        return matchTeamDao.list(matchTeam, offset, fetchSize);
    }

    @Override
    public int listSize(MatchTeam matchTeam) {
        return matchTeamDao.listSize(matchTeam);
    }

    @Override
    public List<MatchTeam> listAll() {
        return matchTeamDao.listAll();
    }
}
