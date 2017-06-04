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
        url: "/game/teamList?offset=" + postData.offset + "&fetchSize=" + postData.fetchSize,
        success: function (data) {
            if (data.list.length > 0) {
                $("#offset").val(Number(postData.offset) + Number(postData.fetchSize));
                for (var i in data.list) {
                    var map = {};
                    map.name = data.list[i].name;
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image/182x137" + data.list[i].icoImage;
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