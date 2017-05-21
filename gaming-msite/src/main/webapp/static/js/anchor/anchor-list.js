/**
 * @author soldier
 */
function getNewsList() {
    var index = layer.load(0, {shade: false});
    var postData = jsonPostData("#loadMoreCondition");
    postData.sortIfDesc = true;
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(postData),
        url: "/anchor/list?offset=" + postData.offset,
        success: function (data) {
            if (data.list.length > 0) {
                $("#offset").val(Number(postData.offset) + Number(postData.fetchSize));
                for (var i in data.list) {
                    var map = {};
                    map.name = data.list[i].name;
                    map.href = data.htmlTemplate.baseUrl + "/anchor/" + data.list[i].id + ".html";
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image/79x79" + data.list[i].icoImage;
                    map.username = data.list[i].username;
                    map.visitCount = data.list[i].visitCount;
                    for (var j in data.list[i].matchZoneList) {
                        var image = "<img src='" + data.htmlTemplate.imageUrl + "/image/23x23" + data.list[i].matchZoneList[j].icoImage + "' alt='" + data.list[i].matchZoneList[j].name + "'/>";
                        map.matchZoneImageEach = map.matchZoneImageEach + image;
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