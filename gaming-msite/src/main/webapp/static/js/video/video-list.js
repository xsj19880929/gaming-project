/**
 * @author soldier
 */
function getNewsList() {
    var offset = $("#offset").val();
    var fetchSize = $("#fetchSize").val();
    var infoVideoTypeStr = $("#infoVideoTypeStr").val();
    var infoZoneTypeStr = $("#infoZoneTypeStr").val();
    var zoneId = $("#zoneId").val();
    var index = layer.load(0, {shade: false});
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        url: "/video/list?offset=" + offset + "&infoVideoTypeStr=" + infoVideoTypeStr + "&infoVideoTypeStr=" + infoZoneTypeStr + "&zoneId=" + zoneId,
        success: function (data) {
            layer.close(index);
            if (data != null) {
                $("#offset").val(Number(offset) + fetchSize);
                for (var i in data.list) {
                    var map = {};
                    map.title = data.list[i].title;
                    map.href = data.htmlTemplate.baseUrl + "/news/" + data.list[i].id + ".html";
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image/330x195" + data.list[i].titleImage;
                    map.imageUrl = data.htmlTemplate.imageUrl;
                    var html = htmlRep(data.htmlTemplate.htmlTemplate, map);
                    $("#newsListData").append(html);
                }
            } else {
                $("#loadMore").hide();
            }
        }
    });
}