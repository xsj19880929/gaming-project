package com.ygccw.msite.mobile.game.model;

import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;

/**
 * @author soldier
 */
public class MatchTeamMappingRequest extends MatchTeamMapping {
    private String templateName;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
