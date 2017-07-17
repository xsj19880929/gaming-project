package com.ygccw.wechat.common.advertising.enums;

import core.framework.util.NamedEnum;

/**
 * @author soldier
 */
public enum AdvType implements NamedEnum {
    index("首页轮播广告"), newsDetailPcHead("新闻详情页文章头PC"), newsDetailMatchPcHead("赛事详情页文章头PC"), newsDetailPcBottom("新闻详情页文章底PC"), newsDetailMHead("新闻详情页文章头移动端"), newsDetailMBottom("新闻详情页文章底移动端"), newsDetailPcRight("新闻资讯右侧PC"), newsDetailAnchorPcRight("主播资讯右侧PC"), top("顶部广告"), gameImagePlus("赛事图加"), imagePlus("图加"), bottom("底部广告");
    private String label;

    AdvType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getName() {
        return name();
    }
}
