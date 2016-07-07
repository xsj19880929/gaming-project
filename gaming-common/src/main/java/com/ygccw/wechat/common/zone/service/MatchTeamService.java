package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.MatchTeam;

import java.util.List;

/**
 * @author soldier
 */
public interface MatchTeamService {
    void save(MatchTeam matchTeam);

    void update(MatchTeam matchTeam);

    void delete(Long id);

    MatchTeam findById(Long id);

    void deleteStatus(Long id);

    List<MatchTeam> list(MatchTeam matchTeam, int offset, int fetchSize);

    int listSize(MatchTeam matchTeam);

    List<MatchTeam> listAll();
}
