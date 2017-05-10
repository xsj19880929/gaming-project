package com.ygccw.msite.utils;

import core.framework.util.TimeLength;
import core.framework.web.site.cookie.CookieSpec;

/**
 * Created by lucian.lin on 15/12/29.
 * 包含 站点所有的Cookie的key定义;
 */
public final class CookieKeyDefine {
    public static final CookieSpec<String> OPEN_ID = CookieSpec.stringKey("OpenId");
    public static final CookieSpec<String> VISITOR_ID = CookieSpec.stringKey("Id");
    public static final CookieSpec<String> CITY_ID = CookieSpec.stringKey("city_id");
    public static final CookieSpec<String> TOKEN = CookieSpec.stringKey("Token").maxAge(TimeLength.days(30)).path("/");
    public static final CookieSpec<String> USER_ID = CookieSpec.stringKey("Uid").maxAge(TimeLength.days(30)).path("/");
    public static final CookieSpec<String> FROM_PROMOTION_INDEX = CookieSpec.stringKey("fromPromotionIndex");
    public static final CookieSpec<String> LOGOUT = CookieSpec.stringKey("logout").path("/");
    public static final CookieSpec<String> SHARE_LINK = CookieSpec.stringKey("sharedLink").path("/").maxAge(TimeLength.days(7));
    public static final CookieSpec<String> PRIVATE_ID = CookieSpec.stringKey("PRIVATE_ID");
    public static final CookieSpec<String> SESSION_ID = CookieSpec.stringKey("SessionId");
    public static final CookieSpec<String> APP = CookieSpec.stringKey("app");
    public static final String COOKIE_VISITOR_ID = "Id";
    public static final String COOKIE_SESSION_ID = "SessionId";
    public static final CookieSpec<String> FULL_SCREEN = CookieSpec.stringKey("FullScreen").maxAge(TimeLength.hours(24));

}
