window.onload = function () {
    //调用tchangeTab
    var VideoTab1 = new Tab('videoTab1', 'li', 'videoPlay1');
    VideoTab1.changeTab();
    var VideoTab2 = new Tab('videoTab2', 'li', 'videoPlay2');
    VideoTab2.changeTab();
};

function Tab(oul, oli, odiv) {
    this.oUl = document.getElementById(oul);
    this.oLi = this.oUl.getElementsByTagName(oli);
    this.oDiv = document.getElementsByClassName(odiv);
}
Tab.prototype.changeTab = function () {
    for (var i = 0; i < this.oLi.length; i++) {
        this.oLi[i].index = i;
        var that = this;
        this.oLi[i].onclick = function () {
            for (var j = 0; j < that.oLi.length; j++) {
                that.oDiv[j].style.display = 'none';
                that.oLi[j].className = '';
            }
            that.oDiv[this.index].style.display = 'block';
            this.className = 'active';
        }
    }
};
