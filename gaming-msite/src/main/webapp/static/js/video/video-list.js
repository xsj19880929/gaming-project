/**
 * @author soldier
 */
function getNewsList() {
    var offset = $("#offset").val();
    var zoneType = $("#zoneType").val();
    var index = layer.load(0, {shade: false});
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        url: "/news/list?offset=" + offset + "&zoneType=" + zoneType,
        success: function (data) {
            layer.close(index);
            if (data != null) {
                $("#offset").val(Number(offset) + 20);
                for (var i in data.list) {
                    var map = {};
                    map.title = data.list[i].title;
                    map.titleImage = data.list[i].titleImage;
                    map.href = data.htmlTemplate.baseUrl + "/news/" + data.list[i].id + ".html";
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image/275x161" + data.list[i].titleImage;
                    map.infoZoneType = data.list[i].infoZoneType.label;
                    map.publishTime = data.list[i].publishTime;
                    var html = htmlRep(data.htmlTemplate.htmlTemplate, map);
                    $("#newsListData").append(html);
                }
            } else {
                $("#loadMore").hide();
            }
        }
    });
}