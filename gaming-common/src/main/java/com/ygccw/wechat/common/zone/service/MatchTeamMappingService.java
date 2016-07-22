package com.ygccw.wechat.common.zone.service;


import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;

import java.util.List;

/**
 * @author soldier
 */
public interface MatchTeamMappingService {
    void save(MatchTeamMapping matchTeamMapping);

    void update(MatchTeamMapping matchTeamMapping);

    void delete(Long id);

    MatchTeamMapping findById(Long id);

    void deleteStatus(Long id);

    List<MatchTeamMapping> list(MatchTeamMapping matchTeamMapping, int offset, int fetchSize);

    int listSize(MatchTeamMapping matchTeamMapping);

    List<MatchTeamMapping> listByMatchTeamId(Long matchTeamId);

    List<MatchTeamMapping> listByMatchZoneId(Long matchZoneId);
}
