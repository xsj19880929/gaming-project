package com.ygccw.wechat.common.zone.service.impl;

import com.ygccw.wechat.common.zone.dao.MatchTeamDao;
import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.service.MatchTeamService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class MatchTeamServiceImpl implements MatchTeamService {
    @Inject
    private MatchTeamDao matchTeamDao;

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
    public void deleteStatus(Long id) {
        matchTeamDao.deleteStatus(id);
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
