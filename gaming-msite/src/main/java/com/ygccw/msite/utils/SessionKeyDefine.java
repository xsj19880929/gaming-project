package com.ygccw.msite.utils;

import core.framework.web.site.session.SessionKey;

/**
 * Created by soldier
 * 要求新增的每个key定义都要加注释说明使用场景
 */
public class SessionKeyDefine {

    public static final SessionKey<String> GAMETAB = SessionKey.key("gameTab", String.class);
    public static final SessionKey<String> VIDEOTAB = SessionKey.key("videoTab", String.class);

}
