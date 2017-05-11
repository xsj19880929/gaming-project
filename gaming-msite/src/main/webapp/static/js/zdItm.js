/**
 * Created by Administrator on 2017/2/27.
 */
window.onload = function () {

//调用tab
    var TAB = new Tab('tabUl', 'li', 'takeItemContent');
    TAB.forIndx();
};

// **封装tab开始
function Tab(oul, oli, odiv) {
    this.oUl = document.getElementById(oul);
    this.oLi = this.oUl.getElementsByTagName(oli);
    this.oDiv = document.getElementsByClassName(odiv);
}
Tab.prototype.forIndx = function () {
    for (var i = 0; i < this.oLi.length; i++) {
        this.oLi[i].index = i;
        var that = this;
        this.oLi[i].onclick = function () {
            for (var j = 0; j < that.oLi.length; j++) {
                that.oLi[j].className = 'action';
                that.oDiv[j].style.display = 'none';
            }
            this.className = '';
            that.oDiv[this.index].style.display = 'block';
        }
    }
};
// 封装tab结束


