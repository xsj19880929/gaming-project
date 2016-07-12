package com.ygccw.wechat.zone.service;

import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.MatchTeamMappingService;
import com.ygccw.wechat.common.zone.service.MatchTeamService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import com.ygccw.wechat.zone.model.MatchTeamMappingModel;
import com.ygccw.wechat.zone.model.MatchTeamModel;
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
public class MatchTeamModelService {
    @Inject
    private MatchTeamService matchTeamService;
    @Inject
    private MatchTeamMappingService matchTeamMappingService;
    @Inject
    private MatchZoneService matchZoneService;

    @Transactional
    public void save(MatchTeamModel matchTeamModel) {
        MatchTeam matchTeam = new MatchTeam();
        BeanUtils.copyProperties(matchTeamModel, matchTeam);
        matchTeamService.save(matchTeam);
        for (MatchTeamMappingModel matchTeamMappingModel : matchTeamModel.getMatchTeamMappingModelList()) {
            if (matchTeamMappingModel.getChecked()) {
                MatchTeamMapping matchTeamMapping = new MatchTeamMapping();
                BeanUtils.copyProperties(matchTeamMappingModel, matchTeamMapping);
                matchTeamMapping.setMatchTeamId(matchTeam.getId());
                matchTeamMappingService.save(matchTeamMapping);
            }
        }

    }

    @Transactional
    public void update(MatchTeamModel matchTeamModel) {
        MatchTeam matchTeam = new MatchTeam();
        BeanUtils.copyProperties(matchTeamModel, matchTeam);
        matchTeamService.update(matchTeam);
        for (MatchTeamMappingModel matchTeamMappingModel : matchTeamModel.getMatchTeamMappingModelList()) {
            MatchTeamMapping matchTeamMapping = new MatchTeamMapping();
            BeanUtils.copyProperties(matchTeamMappingModel, matchTeamMapping);
            matchTeamMapping.setMatchTeamId(matchTeam.getId());
            if (matchTeamMappingModel.getChecked()) {
                if (matchTeamMapping.getId() == null) {
                    matchTeamMappingService.save(matchTeamMapping);
                } else {
                    matchTeamMappingService.update(matchTeamMapping);
                }

            } else if (matchTeamMapping.getId() != null) {
                matchTeamMappingService.delete(matchTeamMapping.getId());
            }
        }
    }


    public MatchTeamModel findById(Long id) {
        MatchTeam matchTeam = matchTeamService.findById(id);
        MatchTeamModel matchTeamModel = new MatchTeamModel();
        BeanUtils.copyProperties(matchTeam, matchTeamModel);
        List<MatchTeamMapping> matchTeamMappingList = matchTeamMappingService.listByMatchTeamId(id);
        List<MatchZone> matchZoneList = matchZoneService.listAll();
        List<MatchTeamMappingModel> matchTeamMappingModelList = new ArrayList<>();
        for (MatchZone matchZone : matchZoneList) {
            MatchTeamMappingModel matchTeamMappingModel = new MatchTeamMappingModel();
            matchTeamMappingModel.setChecked(false);
            matchTeamMappingModel.setMatchZoneName(matchZone.getName());
            matchTeamMappingModel.setMatchZoneId(matchZone.getId());
            for (MatchTeamMapping matchTeamMapping : matchTeamMappingList) {
                if (matchTeamMapping.getMatchZoneId().compareTo(matchZone.getId()) == 0) {
                    BeanUtils.copyProperties(matchTeamMapping, matchTeamMappingModel);
                    matchTeamMappingModel.setChecked(true);
                }
            }
            matchTeamMappingModelList.add(matchTeamMappingModel);
        }
        matchTeamModel.setMatchTeamMappingModelList(matchTeamMappingModelList);
        return matchTeamModel;
    }

    public List<MatchTeamMappingModel> listMatchTeamMappingModel() {
        List<MatchZone> matchZoneList = matchZoneService.listAll();
        List<MatchTeamMappingModel> matchTeamMappingModelList = new ArrayList<>();
        for (MatchZone matchZone : matchZoneList) {
            MatchTeamMappingModel matchTeamMappingModel = new MatchTeamMappingModel();
            matchTeamMappingModel.setChecked(false);
            matchTeamMappingModel.setMatchZoneName(matchZone.getName());
            matchTeamMappingModel.setMatchZoneId(matchZone.getId());
            matchTeamMappingModelList.add(matchTeamMappingModel);
        }
        return matchTeamMappingModelList;
    }

}
