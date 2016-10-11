package com.ygccw.website.pc.info.service;

import com.ygccw.website.pc.info.model.InfoWeb;
import com.ygccw.website.pc.info.model.TagMappingWeb;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import com.ygccw.wechat.common.tags.service.TagsService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class InfoWebService {
    @Inject
    InfoService infoService;
    @Inject
    TagMappingService tagMappingService;
    @Inject
    TagsService tagsService;
    @Inject
    AnchorZoneService anchorZoneService;

    public List<InfoWeb> infoList(InfoZoneType infoZoneType, TagZoneType tagZoneType, int offset, int fetchSize) {
        Info infoRequest = new Info();
        infoRequest.setInfoZoneType(infoZoneType);
        infoRequest.setVerify(1);
        List<Info> infoList = infoService.list(infoRequest, offset, fetchSize);
        List<InfoWeb> infoWebList = new ArrayList<>();
        for (Info info : infoList) {
            InfoWeb infoWeb = new InfoWeb();
            BeanUtils.copyProperties(info, infoWeb);
            TagMapping tagMappingRequest = new TagMapping();
            tagMappingRequest.setTagType(TagType.news);
            tagMappingRequest.setTagZoneType(tagZoneType);
            tagMappingRequest.setEntityId(info.getId());
            List<TagMapping> tagMappingList = tagMappingService.list(tagMappingRequest, 0, 10);
            List<TagMappingWeb> tagMappingWebList = new ArrayList<>();
            for (TagMapping tagMapping : tagMappingList) {
                TagMappingWeb tagMappingWeb = new TagMappingWeb();
                BeanUtils.copyProperties(tagMapping, tagMappingWeb);
                Tags tags = tagsService.findById(tagMappingWeb.getTagsId());
                tagMappingWeb.setName(tags.getName());
                tagMappingWebList.add(tagMappingWeb);
            }
            infoWeb.setTagMappingWebList(tagMappingWebList);
            infoWebList.add(infoWeb);
        }
        return infoWebList;
    }

    public List<Info> videoListTop(int offset, int fetchSize) {
        Info infoRequest = new Info();
        infoRequest.setInfoType(InfoType.video);
        infoRequest.setSortName("visitCount");
        infoRequest.setSortIfDesc(true);
        return infoService.list(infoRequest, offset, fetchSize);
    }

    public List<Info> newsListTop(int offset, int fetchSize) {
        Info infoRequest = new Info();
        infoRequest.setInfoType(InfoType.news);
        infoRequest.setSortName("visitCount");
        infoRequest.setSortIfDesc(true);
        return infoService.list(infoRequest, offset, fetchSize);
    }

    public List<AnchorZone> anchorListTop(int offset, int fetchSize) {
        AnchorZone anchorZone = new AnchorZone();
        anchorZone.setSortIfDesc(true);
        anchorZone.setSortName("visitCount");
        return anchorZoneService.list(anchorZone, offset, fetchSize);
    }
}
