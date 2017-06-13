/**
 * @author soldier
 */
function getNewsList() {
    var index = layer.load(0, {shade: false});
    var postData = jsonPostData("#loadMoreCondition");
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(postData),
        url: "/game/list?offset=" + postData.offset + "&fetchSize=" + postData.fetchSize,
        success: function (data) {
            if (data.list.length > 0) {
                $("#offset").val(Number(postData.offset) + Number(postData.fetchSize));
                for (var i in data.list) {
                    var map = {};
                    map.name = data.list[i].name;
                    map.href = data.htmlTemplate.baseUrl + "game/" + data.list[i].id + "/";
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image/122x122" + data.list[i].icoImage;
                    map.matchZoneAreaName = data.list[i].matchZoneAreaName;
                    map.startTime = formatDate(new Date(data.list[i].startTime), "yyyy-MM-dd");
                    map.endTime = formatDate(new Date(data.list[i].endTime), "yyyy-MM-dd");
                    map.matchStatus = data.list[i].matchStatus.label;
                    if (data.list[i].matchStatus.name == 'START') {
                        map.matchStatusImageSrc = data.htmlTemplate.baseUrl + "/static/img/hdqidai.png";
                    } else if (data.list[i].matchStatus.name == 'END') {
                        map.matchStatusImageSrc = data.htmlTemplate.baseUrl + "/static/img/hdover.png";
                    } else if (data.list[i].matchStatus.name == 'DOING') {
                        map.matchStatusImageSrc = data.htmlTemplate.baseUrl + "/static/img/hding.png";
                    }
                    var html = htmlRep(data.htmlTemplate.htmlTemplate, map);
                    $("#newsListData").append(html);
                }
            } else {
                $("#loadMore").hide();
            }
            layer.close(index);
        }
    });
}