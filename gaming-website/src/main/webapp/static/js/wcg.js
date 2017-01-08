// JavaScript Document
$(function () {
    //搜索下拉菜单
    $(".search_change").click(function (event) {
        event.stopPropagation();
        $(this).addClass("new_search_change");
        $(".search_option").slideDown(150);
    })
    $(".search_option span").click(function (event) {
        event.stopPropagation();
        var spanval = $(this).text();
        var myval = $(".search_change").text();
        $(this).text(myval);
        $(".search_change").text(spanval);
        $(".search_option").slideUp(150);
        $(".search_change").removeClass("new_search_change")
    })
    $(document).click(function () {
        $(".search_option").hide();
    })
    //导航
    $(".ppw_nav li").click(function () {
        $(this).addClass("sel").siblings().removeClass("sel");
    })
    //首页顶部赛事列表
    $(".wcg_list li").mouseover(function () {
        $(this).addClass("hover");
    })
    $(".wcg_list li").mouseout(function () {
        $(this).removeClass("hover");
    })
    //首页资讯鼠标移上去显示日期
    $(".index_con ul li").hover(function () {
        $(this).addClass("hover").siblings().removeClass("hover");
    })
    //精选美图
    $(".about_pic li a").mouseover(function () {
        $(this).children("p").css("display", "block");
    })
    $(".about_pic li a").mouseout(function () {
        $(this).children("p").css("display", "none");
    })
    //帅选
    $(".list_choose_con dl dd.dd1 span").click(function () {
        if ($(this).attr("class") == "more") {
            $(this).parent(".dd1").next(".dd2").hide();
            $(this).removeClass("more");
        } else {
            $(this).parent(".dd1").next(".dd2").show();
            $(this).addClass("more");
        }
    })
    //比赛视频
    $(".index_video_right ul li").mouseover(function () {
        $(this).addClass("hover");
    })
    $(".index_video_right ul li").mouseout(function () {
        $(this).removeClass("hover");
    })
    //game导航
    $(".game_nav li").click(function () {
        $(this).addClass("change").siblings().removeClass("change");
    })
    //明星战队切换内容
    ullength = $(".qiehuan .list_01").length;
    ulwidth = $(".qiehuan .list_01").width();
    $(".qiehuan .inner").width(ullength * ulwidth);
    var number = 0;
    $(".arrow .next").click(function () {
        showpic();
    })
    $(".arrow .prev").click(function () {
        if (number > 0) {
            number--;
            $(".qiehuan .inner").animate({left: -(ulwidth * number)}, 10);

        } else {
            number = ullength - 1;
            $(".qiehuan .inner").animate({left: -(ulwidth * number)}, 10);

        }
    })
    $(".qiehuan .inner").hover(function () {
        clearInterval(pictime)

    }, function () {
        pictime = setInterval(showpic, 4000)
    }).trigger("mouseleave")

    function showpic() {

        if (ullength - 1 > number) {
            number++;
            $(".qiehuan .inner").animate({left: -(ulwidth * number)}, 10);
        } else {
            number = 0;
            $(".qiehuan .inner").animate({left: -(ulwidth * number)}, 10)
        }

    }
})
//tab切换	
function setTab(name, cursel, n) {
    for (i = 1; i <= n; i++) {
        var menu = document.getElementById(name + i);
        var con = document.getElementById("con_" + name + "_" + i);
        menu.className = i == cursel ? "hover" : "";
        con.style.display = i == cursel ? "block" : "none";
    }
}
//设为首页
function setHome(obj, url) {
    try {
        obj.style.behavior = 'url(#default#homepage)';
        obj.setHomePage(url);
    } catch (e) {
        if (window.netscape) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            } catch (e) {
                alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
            }
        } else {
            alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【" + url + "】设置为首页。");
        }
    }
}
//收藏本站
function addFavorite(title, url) {
    try {
        window.external.addFavorite(url, title);
    }
    catch (e) {
        try {
            window.sidebar.addPanel(title, url, "");
        }
        catch (e) {
            alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}


		
		


		
		






