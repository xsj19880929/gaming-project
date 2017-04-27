package com.ygccw.crawler.schedule.service;

import com.ygccw.wechat.common.commiturlsearch.entity.CommitUrlSearch;
import com.ygccw.wechat.common.commiturlsearch.enums.WebType;
import com.ygccw.wechat.common.commiturlsearch.service.CommitUrlSearchService;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class CreateUrlService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    private InfoService infoService;
    @Inject
    private PictureService pictureService;
    @Inject
    private CommitUrlSearchService commitUrlSearchService;
    @Inject
    private MatchZoneService matchZoneService;
    @Inject
    private AnchorZoneService anchorZoneService;

    public void updateVerify(Date lastTime) {
        createNewVideoUrl(lastTime);
        createPictureUrl(lastTime);
        createMatchZoneUrl(lastTime);
        createAnchorZoneUrl(lastTime);
    }

    /**
     * 生成新闻和视频url提交地址
     *
     * @param lastTime
     */
    private void createNewVideoUrl(Date lastTime) {
        Info info = new Info();
        info.setStartTime(lastTime);
        info.setStatus(1);
        info.setVerify(1);
        int offset = 0;
        int fetchSize = 100;
        List<Info> infoList = null;
        do {
            infoList = infoService.list(info, offset, fetchSize);
            for (Info info1 : infoList) {
                if (InfoType.news == info1.getInfoType()) {
                    logger.info("新闻：" + info1.getId());
                    String url = "http://www.55djw.com/news/" + info1.getId() + ".html";
                    saveCommitUrl(url);
                }
                if (InfoType.video == info1.getInfoType()) {
                    logger.info("视频：" + info1.getId());
                    String url = "http://www.55djw.com/video/" + info1.getId() + ".html";
                    saveCommitUrl(url);
                }
            }
            offset = offset + fetchSize;
        } while (!infoList.isEmpty());
    }

    /**
     * 生成图片url提交地址
     *
     * @param lastTime
     */
    private void createPictureUrl(Date lastTime) {
        Picture picture = new Picture();
        picture.setStartTime(lastTime);
        picture.setStatus(1);
        picture.setVerify(1);
        int offset = 0;
        int fetchSize = 100;
        List<Picture> pictureList = null;
        do {
            pictureList = pictureService.list(picture, offset, fetchSize);
            for (Picture picture1 : pictureList) {
                logger.info("图片：" + picture1.getId());
                String url = "http://www.55djw.com/picture/" + picture1.getId() + ".html";
                saveCommitUrl(url);
            }
            offset = offset + fetchSize;
        } while (!pictureList.isEmpty());
    }

    /**
     * 生成赛事url提交地址
     *
     * @param lastTime
     */
    private void createMatchZoneUrl(Date lastTime) {
        MatchZone matchZone = new MatchZone();
        matchZone.setCreateStartTime(lastTime);
        matchZone.setStatus(1);
        List<MatchZone> matchZoneList = matchZoneService.list(matchZone, 0, Integer.MAX_VALUE);
        for (MatchZone matchZone1 : matchZoneList) {
            logger.info("赛事：" + matchZone1.getId());
            String url = "http://www.55djw.com/game/" + matchZone1.getId() + "/";
            saveCommitUrl(url);

        }
    }

    /**
     * 生成主播url提交地址
     *
     * @param lastTime
     */
    private void createAnchorZoneUrl(Date lastTime) {
        AnchorZone anchorZone = new AnchorZone();
        anchorZone.setCreateStartTime(lastTime);
        anchorZone.setStatus(1);
        List<AnchorZone> anchorZoneList = anchorZoneService.list(anchorZone, 0, Integer.MAX_VALUE);
        for (AnchorZone anchorZone1 : anchorZoneList) {
            logger.info("主播：" + anchorZone1.getId());
            String url = "http://www.55djw.com/anchor/" + anchorZone1.getId() + "/";
            saveCommitUrl(url);

        }
    }

    /**
     * 保存提交url地址
     *
     * @param url
     */
    private void saveCommitUrl(String url) {
        for (WebType webType : WebType.values()) {
            CommitUrlSearch commitUrlSearchResult = commitUrlSearchService.findByUrlAndWebType(url, webType);
            if (commitUrlSearchResult == null) {
                CommitUrlSearch commitUrlSearch = new CommitUrlSearch();
                commitUrlSearch.setUrl(url);
                commitUrlSearch.setWebType(webType);
                commitUrlSearchService.save(commitUrlSearch);
            }
        }
    }

}
