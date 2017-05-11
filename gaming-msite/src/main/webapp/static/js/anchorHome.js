/**
 * Created by Administrator on 2017/2/27.
 */
window.onload = function () {

//调用tab-img
    var TABIMG = new Tab('links', 'linksText');
    TABIMG.imgSrc();
};

// **封装tab-img开始
function Tab(oimg, text) {
    this.links = document.getElementById(oimg);
    this.linkTex = document.getElementById(text);
}
Tab.prototype.imgSrc = function () {
    var that = this;
    this.links.onclick = function () {
        if (that.links.alt == 0) {
            that.links.alt = 1;
            this.src = "img/sc.png";
            that.linkTex.textContent = '已收藏';
            that.linkTex.className='anchordeta scqk1'

        } else {
            this.src = "img/sc-1.png";
            that.linkTex.textContent = '收藏';
            that.links.alt = 0;
            that.linkTex.className='anchordeta scqk'
        }
    }
};


