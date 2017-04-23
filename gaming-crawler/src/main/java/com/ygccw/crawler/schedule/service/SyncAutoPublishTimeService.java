package com.ygccw.crawler.schedule.service;

import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.service.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author soldier
 *         按设定时间发布新闻
 */
@Service
public class SyncAutoPublishTimeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    private InfoService infoService;

    @Transactional
    public void updateVerify() {
        //发布行业新闻
        Info info = new Info();
        info.setSortName("publishTime");
        info.setSortIfDesc(false);
        info.setVerify(0);
        info.setAutoPublishTime(new Date());
        info.setIfAutoPublish(1);
        List<Info> tradeInfoList = infoService.list(info, 0, 1000);
        if (tradeInfoList != null && !tradeInfoList.isEmpty()) {
            Info infoUpdate = tradeInfoList.get(0);
            infoUpdate.setVerify(1);
            infoUpdate.setPublishTime(new Date());
            infoService.update(infoUpdate);
            logger.info("定时发布程序发布一条{}的{}", infoUpdate.getInfoZoneType().getLabel(), infoUpdate.getInfoType().getLabel());
        }
    }


}
