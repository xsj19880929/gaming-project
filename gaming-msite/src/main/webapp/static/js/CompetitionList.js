/**
 * Created by Administrator on 2017/2/27.
 */
window.onload = function () {
//调用tab-img

    var TABIMG = new Tab('tabUl', 'li', 'gamelist');
    TABIMG.imgSrc();

};

// **封装tab-img开始
function Tab(oul, oli, odiv) {
    this.oUl = document.getElementById(oul);
    this.oLi = this.oUl.getElementsByTagName(oli);
    this.oDiv = document.getElementsByClassName(odiv);
    this.changeImg = document.getElementsByClassName('chengaeImg');
    this.jiaoBiao = document.getElementsByClassName('triangle');
}


Tab.prototype.imgSrc = function () {
    for (var i = 0; i < this.oLi.length; i++) {
        this.oLi[i].index = i;
        var that = this;
        this.oLi[i].onclick = function () {
            for (var j = 0; j < that.oLi.length; j++) {
                var imageUrl = that.changeImg[j].src.split('static');
                that.changeImg[j].src = imageUrl[0] + 'static/img/saish' + (j + 2) + '.png';
                that.oDiv[j].style.display = 'none';
                that.jiaoBiao[j].style.display = 'none';
                that.oLi[j].className = '';
            }
            var imageUrl2 = that.changeImg[this.index].src.split('static');
            that.changeImg[this.index].src = imageUrl2[0] + 'static/img/saish' + (this.index + 2) + '-1.png';
            that.oDiv[this.index].style.display = 'block';
            that.jiaoBiao[this.index].style.display = 'block';
            this.className = 'active';
        }
    }
};

