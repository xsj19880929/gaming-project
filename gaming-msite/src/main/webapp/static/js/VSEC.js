/**
 * Created by Administrator on 2017/2/28.
 */
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


/**
 * Created by Administrator on 2017/2/27.
 */

// /**封装头部开始
function Tgshow(id1, id2) {
    this.aginMores = document.getElementById(id1);
    this.moreshow = document.getElementById(id2);
    this.wcgpnavbox = document.getElementById('wcgpnavbox');
}
//gottle()
Tgshow.prototype.toggleSH = function () {
    var that = this;
    this.aginMores.onclick = function () {
        if (that.getStyle(that.moreshow, 'display') == 'none') {
            that.moreshow.style.display = 'block';
            that.wcgpnavbox.style.borderBottom = 'none';
            that.aginMores.textContent = '收起∧';
        } else {
            that.moreshow.style.display = 'none';
            that.wcgpnavbox.style.borderBottom = '1px solid #d5d9da';
            that.aginMores.textContent = '更多∨';
        }
    }
};
//currentStyle()
Tgshow.prototype.getStyle = function (dom, attr) {
    if (dom.currentStyle) {
        return dom.currentStyle[attr];
    } else {
        return getComputedStyle(dom)[attr];
    }
};
// / 封装头部结束

