/**
 * @author soldier
 */
function getNewsList() {
    var offset = $("#offset").val();
    var fetchSize = $("#fetchSize").val();
    var matchZoneAreaId = $("#matchZoneAreaId").val();
    var matchZoneYearId = $("#matchZoneYearId").val();
    var matchStatus = $("#matchStatus").val();
    var index = layer.load(0, {shade: false});
    var postData = {
        "offset": offset,
        "fetchSize": fetchSize,
        "matchZoneAreaId": matchZoneAreaId,
        "matchZoneYearId": matchZoneYearId,
        "matchStatus": matchStatus
    };
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(postData),
        url: "/game/list?offset=" + offset,
        success: function (data) {
            layer.close(index);
            if (data.list.length > 0) {
                $("#offset").val(Number(offset) + fetchSize);
                for (var i in data.list) {
                    var map = {};
                    map.name = data.list[i].name;
                    map.href = data.htmlTemplate.baseUrl + "/game/" + data.list[i].id + ".html";
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
        }
    });
}