/**
 * @author soldier
 */
$(function () {
    $("input[name='searchSubmit']").click(function () {
        var keywords = $("input[name='keywords']").val();
        if (keywords == "" || keywords == "请输入您要搜索的关键字") {
            alert("请输入关键字");
            return;
        }
        var typeName = $(".search_change").text();
        var type = "all";
        if (typeName == "资讯") {
            type = "news";
        } else if (typeName == "视频") {
            type = "video";
        } else if (typeName == "赛事") {
            type = "match";
        } else if (typeName == "主播") {
            type = "anchor";
        } else if (typeName == "图集") {
            type = "picture";
        }
        window.location.href = "/search_" + keywords + "_" + type + "_1.html";
    })
});

function getNewsList() {
    var index = layer.load(0, {shade: false});
    var postData = jsonPostData("#loadMoreCondition");
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(postData),
        url: "/search/list?offset=" + postData.offset + "&fetchSize=" + postData.fetchSize,
        success: function (data) {
            layer.close(index);
            if (data.list.length > 0) {
                $("#offset").val(Number(postData.offset) + Number(postData.fetchSize));
                for (var i in data.list) {
                    var map = {};
                    map.title = data.list[i].title;
                    map.subTitle = data.list[i].subTitle;
                    map.href = data.htmlTemplate.baseUrl + "/news/" + data.list[i].id + ".html";
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image/275x161" + data.list[i].titleImage;
                    var html = htmlRep(data.htmlTemplate.htmlTemplate, map);
                    $("#newsListData").append(html);
                }
            } else {
                $("#loadMore").hide();
            }
        }
    });
}

function getVideoList() {
    var index = layer.load(0, {shade: false});
    var postData = jsonPostData("#loadMoreCondition");
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(postData),
        url: "/search/list?offset=" + postData.offset + "&fetchSize=" + postData.fetchSize,
        success: function (data) {
            layer.close(index);
            if (data.list.length > 0) {
                $("#offset").val(Number(postData.offset) + Number(postData.fetchSize));
                for (var i in data.list) {
                    var map = {};
                    map.title = data.list[i].title;
                    map.href = data.htmlTemplate.baseUrl + "/news/" + data.list[i].id + ".html";
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image/330x195" + data.list[i].titleImage;
                    map.baseUrl = data.htmlTemplate.baseUrl;
                    var html = htmlRep(data.htmlTemplate.htmlTemplate, map);
                    $("#newsListData").append(html);
                }
            } else {
                $("#loadMore").hide();
            }
        }
    });
}

function getPictureList() {
    var index = layer.load(0, {shade: false});
    var postData = jsonPostData("#loadMoreCondition");
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(postData),
        url: "/search/list?offset=" + postData.offset + "&fetchSize=" + postData.fetchSize,
        success: function (data) {
            layer.close(index);
            if (data.list.length > 0) {
                $("#offset").val(Number(postData.offset) + Number(postData.fetchSize));
                for (var i in data.list) {
                    var map = {};
                    map.href = data.htmlTemplate.baseUrl + "/picture/" + data.list[i].id + ".html";
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image/222x169" + data.list[i].image;
                    map.description = data.list[i].description;
                    var html = htmlRep(data.htmlTemplate.htmlTemplate, map);
                    $("#newsListData").append(html);
                }
            } else {
                $("#loadMore").hide();
            }
        }
    });
}

function getMatchList() {
    var index = layer.load(0, {shade: false});
    var postData = jsonPostData("#loadMoreCondition");
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(postData),
        url: "/search/list?offset=" + postData.offset + "&fetchSize=" + postData.fetchSize,
        success: function (data) {
            layer.close(index);
            if (data.list.length > 0) {
                $("#offset").val(Number(postData.offset) + Number(postData.fetchSize));
                for (var i in data.list) {
                    var map = {};
                    map.name = data.list[i].name;
                    map.href = data.htmlTemplate.baseUrl + "/game/" + data.list[i].id + ".html";
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image/122x122" + data.list[i].icoImage;
                    map.matchZoneAreaName = data.list[i].matchZoneAreaName;
                    map.startTime = formatDate(new Date(data.list[i].startTime), "yyyy-MM-dd");
                    map.endTime = formatDate(new Date(data.list[i].endTime), "yyyy-MM-dd");
                    var html = htmlRep(data.htmlTemplate.htmlTemplate, map);
                    $("#newsListData").append(html);
                }
            } else {
                $("#loadMore").hide();
            }
        }
    });
}

function getAnchorList() {
    var index = layer.load(0, {shade: false});
    var postData = jsonPostData("#loadMoreCondition");
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(postData),
        url: "/search/list?offset=" + postData.offset + "&fetchSize=" + postData.fetchSize,
        success: function (data) {
            layer.close(index);
            if (data.list.length > 0) {
                $("#offset").val(Number(postData.offset) + Number(postData.fetchSize));
                for (var i in data.list) {
                    var map = {};
                    map.href = data.htmlTemplate.baseUrl + "/anchor/" + data.list[i].id + ".html";
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image/79x79" + data.list[i].icoImage;
                    map.username = data.list[i].username;
                    var html = htmlRep(data.htmlTemplate.htmlTemplate, map);
                    $("#newsListData").append(html);
                }
            } else {
                $("#loadMore").hide();
            }
        }
    });
}