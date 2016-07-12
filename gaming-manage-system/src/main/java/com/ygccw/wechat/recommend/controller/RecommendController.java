package com.ygccw.wechat.recommend.controller;

import com.ygccw.wechat.common.recommend.entity.Recommend;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendService;
import com.ygccw.wechat.recommend.model.RecommendMappingModel;
import com.ygccw.wechat.recommend.service.RecommendMappingModelService;
import com.ygccw.wechat.sys.Menu;
import core.framework.database.FindResult;
import core.framework.web.site.session.RequireSession;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author soldier
 */
@RestController
@Menu({"recommend.recommend.list"})
public class RecommendController {
    @Inject
    RecommendService recommendService;
    @Inject
    RecommendMappingModelService recommendMappingModelService;

    @RequireSession
    @RequestMapping(value = "/recommend/listByType/{recommendType}", method = RequestMethod.GET)
    @ResponseBody
    public List<Recommend> listRecommend(@PathVariable("recommendType") RecommendType recommendType) {
        return recommendService.listByType(recommendType);
    }

    @RequireSession
    @RequestMapping(value = "/recommend/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<Recommend> list(@RequestBody Recommend recommend, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(recommendService.list(recommend, offset, fetchSize), offset, recommendService.listSize(recommend));
    }

    @RequireSession
    @RequestMapping(value = "/recommend", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody Recommend recommend) {
        recommendService.update(recommend);
    }


    @RequireSession
    @RequestMapping(value = "/recommend/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        recommendService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "/recommend", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody Recommend recommend) {
        recommendService.save(recommend);
    }

    @RequireSession
    @RequestMapping(value = "/recommend/recommendMappingByEntityId", method = RequestMethod.GET)
    @ResponseBody
    public List<RecommendMappingModel> listRecommendMappingByEntityId(@RequestParam("recommendType") RecommendType recommendType, @RequestParam("entityId") Long entityId) {
        return recommendMappingModelService.listByEntityAndTyp(entityId, recommendType);
    }

    @RequireSession
    @RequestMapping(value = "/recommend/listRecommendMapping", method = RequestMethod.GET)
    @ResponseBody
    public List<RecommendMappingModel> listRecommendMapping(@RequestParam("recommendType") RecommendType recommendType) {
        return recommendMappingModelService.listRecommendMapping(recommendType);
    }

    @RequireSession
    @RequestMapping(value = "/recommend/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Recommend findById(@PathVariable("id") Long id) {
        return recommendService.findById(id);
    }

}
