/**
 * @author soldier
 */
function getNewsList() {
    var offset = $("#offset").val();
    var fetchSize = $("#fetchSize").val();
    var infoVideoTypeStr = $("#infoVideoTypeStr").val();
    var infoZoneTypeStr = $("#infoZoneTypeStr").val();
    var zoneId = $("#zoneId").val();
    var sortName = $("#sortName").val();
    var index = layer.load(0, {shade: false});
    var postData = {
        "offset": offset,
        "fetchSize": fetchSize,
        "infoVideoTypeStr": infoVideoTypeStr,
        "infoZoneTypeStr": infoZoneTypeStr,
        "zoneId": zoneId,
        "sortName": sortName,
        "sortIfDesc": true,
    };
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(postData),
        url: "/video/list?offset=" + offset,
        success: function (data) {
            layer.close(index);
            if (data.list.length > 0) {
                $("#offset").val(Number(offset) + fetchSize);
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