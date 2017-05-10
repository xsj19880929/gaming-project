$(function () {
    //导航栏
    $("#wcgp-nav1 li").click(function () {
        $("#wcgp-nav2 li,#wcgp-nav1 li").removeClass("on");
        $(this).addClass("on");
    });
    $("#wcgp-nav2 li").click(function () {
        $("#wcgp-nav2 li,#wcgp-nav1 li").removeClass("on");
        $(this).addClass("on");
    });
    //导航栏隐藏
    $(".wcgp-nav-more").click(function () {
        if ($(".wcgp-navm-con").hasClass("hide")) {
            $(".wcgp-navm-con").toggleClass("show");
            $("#wcgp-nav-p1").show();
            $("#wcgp-nav-p2").hide();
            $("#nav-bg").toggleClass("change")
        }
        if ($(".wcgp-navm-con").hasClass("show")) {
            $("#wcgp-nav-p1").hide();
            $("#wcgp-nav-p2").show();
        }
    });
});

$(window).scroll(function () {
    if ($(window).scrollTop() > 0) {
        $(".tipTop.backtop").fadeIn(400);//当滑动栏向下滑动时，按钮渐现的时间
    } else {
        $(".tipTop.backtop").fadeOut(200);//当页面回到顶部第一屏时，按钮渐隐的时间
    }
});
$(".tipTop.backtop").click(function () {
    $('html,body').animate({
        scrollTop : '0px'
    }, 200);//返回顶部所用的时间
});


	


















































































