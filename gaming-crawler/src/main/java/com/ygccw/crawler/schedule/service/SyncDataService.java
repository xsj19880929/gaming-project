package com.ygccw.crawler.schedule.service;

import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.enums.PictureZoneType;
import com.ygccw.wechat.common.picture.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class SyncDataService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    private InfoService infoService;
    @Inject
    private PictureService pictureService;

    @Transactional
    public void updateVerify() {
        //发布行业新闻
        infoUpdateVerifyCommon(InfoType.news, InfoZoneType.trade);
        infoUpdateVerifyCommon(InfoType.news, InfoZoneType.matchZone);
        infoUpdateVerifyCommon(InfoType.news, InfoZoneType.anchorZone);
        infoUpdateVerifyCommon(InfoType.video, InfoZoneType.trade);
        infoUpdateVerifyCommon(InfoType.video, InfoZoneType.matchZone);
        infoUpdateVerifyCommon(InfoType.video, InfoZoneType.anchorZone);
        pictureUpdateVerifyCommon(PictureZoneType.matchZone);
        pictureUpdateVerifyCommon(PictureZoneType.trade);
        pictureUpdateVerifyCommon(PictureZoneType.anchorZone);
    }

    private void infoUpdateVerifyCommon(InfoType infoType, InfoZoneType infoZoneType) {
        Info info = new Info();
        info.setInfoType(infoType);
        info.setSortName("publishTime");
        info.setSortIfDesc(false);
        info.setInfoZoneType(infoZoneType);
        info.setVerify(0);
        List<Info> tradeInfoList = infoService.list(info, 0, 1);
        if (tradeInfoList != null && !tradeInfoList.isEmpty()) {
            Info infoUpdate = tradeInfoList.get(0);
            infoUpdate.setVerify(1);
            infoUpdate.setPublishTime(new Date());
            infoService.update(infoUpdate);
            logger.info("发布一条{}的{}", infoZoneType.getLabel(), infoType.getLabel());
        }
    }

    private void pictureUpdateVerifyCommon(PictureZoneType pictureZoneType) {
        Picture picture = new Picture();
        picture.setSortName("publishTime");
        picture.setSortIfDesc(false);
        picture.setPictureZoneType(pictureZoneType);
        picture.setVerify(0);
        List<Picture> pictureList = pictureService.list(picture, 0, 1);
        if (pictureList != null && !pictureList.isEmpty()) {
            Picture pictureUpdate = pictureList.get(0);
            pictureUpdate.setVerify(1);
            pictureUpdate.setPublishTime(new Date());
            pictureService.update(pictureUpdate);
            logger.info("发布一条{}的图集", pictureZoneType);
        }
    }
}
