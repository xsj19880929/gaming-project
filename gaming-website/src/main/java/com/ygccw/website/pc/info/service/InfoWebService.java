package com.ygccw.website.pc.info.service;

import com.ygccw.website.pc.info.model.InfoWeb;
import com.ygccw.website.pc.info.model.TagMappingWeb;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import com.ygccw.wechat.common.tags.service.TagsService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import core.framework.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Inject
    PictureService pictureService;

    public List<InfoWeb> infoList(Long zoneId, InfoZoneType infoZoneType, TagZoneType tagZoneType, int offset, int fetchSize) {
        Info infoRequest = new Info();
        infoRequest.setInfoZoneType(infoZoneType);
        infoRequest.setZoneId(zoneId);
        infoRequest.setVerify(1);
        infoRequest.setInfoType(InfoType.news);
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
            if (!tagMappingWebList.isEmpty()) {
                infoWeb.setTagMappingWebList(tagMappingWebList);
            }
            infoWebList.add(infoWeb);
        }
        return infoWebList;
    }

    public int infoListSize(Long zoneId, InfoZoneType infoZoneType) {
        Info infoRequest = new Info();
        infoRequest.setInfoZoneType(infoZoneType);
        infoRequest.setZoneId(zoneId);
        infoRequest.setVerify(1);
        infoRequest.setInfoType(InfoType.news);
        return infoService.listSize(infoRequest);
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

    public List<Info> newsListNewest(int offset, int fetchSize) {
        Info infoRequest = new Info();
        infoRequest.setInfoType(InfoType.news);
        return infoService.list(infoRequest, offset, fetchSize);
    }

    public List<AnchorZone> anchorListTop(int offset, int fetchSize) {
        AnchorZone anchorZone = new AnchorZone();
        anchorZone.setSortIfDesc(true);
        anchorZone.setSortName("visitCount");
        return anchorZoneService.list(anchorZone, offset, fetchSize);
    }

    public InfoWeb findById(Long id) {
        Info info = infoService.findById(id);
        InfoWeb infoWeb = new InfoWeb();
        BeanUtils.copyProperties(info, infoWeb);
        TagMapping tagMappingRequest = new TagMapping();
        tagMappingRequest.setTagType(getTagTypeByString(info.getInfoType().getName()));
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
        if (!tagMappingWebList.isEmpty()) {
            infoWeb.setTagMappingWebList(tagMappingWebList);
        }
        return infoWeb;
    }

    private TagType getTagTypeByString(String tagTypeString) {
        for (TagType tagType : TagType.values()) {
            if (tagType.getName().equals(tagTypeString)) {
                return tagType;
            }
        }
        return null;

    }

    public List<Picture> pictureListTop(int offset, int fetchSize) {
        Picture picture = new Picture();
        picture.setSortName("visitCount");
        picture.setSortIfDesc(true);
        return pictureService.list(picture, offset, fetchSize);
    }

    public Set<Info> likeInfoList(InfoWeb infoWeb, int fetchSize) {
        Set<Info> infoList = new HashSet<>(fetchSize);
        Map<Long, Info> mappingMap = new HashMap<>(fetchSize);
        if (StringUtils.hasText(infoWeb.getTags())) {
            String[] tagArray = infoWeb.getTags().split(" ");
            TagZoneType tagZoneTypeRequest = null;
            for (TagZoneType tagZoneType : TagZoneType.values()) {
                if (infoWeb.getInfoZoneType().getName().equals(tagZoneType.getName())) {
                    tagZoneTypeRequest = tagZoneType;
                }
            }
            for (String tagName : tagArray) {
                Tags tags = tagsService.findByName(tagName, getTagTypeByString(infoWeb.getInfoType().getName()), tagZoneTypeRequest);
                if (tags == null) continue;
                List<TagMapping> tagMappingList = tagMappingService.listByTagsId(tags.getId(), 0, fetchSize);
                for (TagMapping tagMapping : tagMappingList) {
                    Info info = infoService.findById(tagMapping.getEntityId());
                    mappingMap.put(tagMapping.getEntityId(), info);
                    if (mappingMap.size() == fetchSize) {
                        break;
                    }
                }
            }
        }
        mappingMap.remove(infoWeb.getId());
        for (Map.Entry<Long, Info> entry : mappingMap.entrySet()) {
            infoList.add(entry.getValue());
        }
        return infoList;

    }

    public Info lastInfo(InfoWeb infoWeb) {
        infoWeb.setZoneId(null);
        return infoService.lastInfo(infoWeb);
    }

    public Info nextInfo(InfoWeb infoWeb) {
        infoWeb.setZoneId(null);
        return infoService.nextInfo(infoWeb);
    }

    public List<InfoWeb> infoListByTagId(Long tagId, int offset, int fetchSize) {
        List<InfoWeb> infoWebList = new ArrayList<>();
        List<TagMapping> tagMappingListPaging = tagMappingService.listByTagsIdPaging(tagId, offset, fetchSize);
        for (TagMapping tagMappingPaging : tagMappingListPaging) {
            Info info = infoService.findById(tagMappingPaging.getEntityId());
            InfoWeb infoWeb = new InfoWeb();
            BeanUtils.copyProperties(info, infoWeb);
            TagMapping tagMappingRequest = new TagMapping();
            tagMappingRequest.setTagType(tagMappingPaging.getTagType());
            tagMappingRequest.setTagZoneType(tagMappingPaging.getTagZoneType());
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
            if (!tagMappingWebList.isEmpty()) {
                infoWeb.setTagMappingWebList(tagMappingWebList);
            }
            infoWebList.add(infoWeb);
        }
        return infoWebList;
    }

    public int infoListByTagIdSize(Long tagId) {
        return tagMappingService.listByTagsIdPagingSize(tagId);
    }
}
