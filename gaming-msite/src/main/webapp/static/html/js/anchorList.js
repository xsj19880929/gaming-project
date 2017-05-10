/**
 * Created by Administrator on 2017/2/27.
 */
window.onload = function () {
//调用tab-img
    var TABIMG = new Tab('videoData', 'li', 'gamelist');
    TABIMG.imgSrc();
    // TABIMG.JBiao();
    //调用tab-border
    var TABBORD = new Tab('tabUl', 'li', 'zhuboleibiao');
    TABBORD.forIndx();
};

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
                that.changeImg[j].src = 'img/zhubosx' + (j + 1) + '.png';
                that.oDiv[j].style.display = 'none';
                that.jiaoBiao[j].style.display = 'none';
                that.oLi[j].className='';
            }
            that.changeImg[this.index].src = 'img/zhubosx' + (this.index + 1) + '-1.png';
            that.oDiv[this.index].style.display = 'block';
            that.jiaoBiao[this.index].style.display = 'block';
            this.className='active'
        }
    }
};

// **封装tab-border开始
Tab.prototype.forIndx = function () {
    for (var i = 0; i < this.oLi.length; i++) {
        this.oLi[i].index = i;
        var that = this;
        this.oLi[i].onclick = function () {
            for (var j = 0; j < that.oLi.length; j++) {
                that.oLi[j].className = '';
                that.oDiv[j].style.display = 'none';
            }
            this.className = 'action';
            that.oDiv[this.index].style.display = 'block';
        }
    }
};
