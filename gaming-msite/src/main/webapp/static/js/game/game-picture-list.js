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
        url: "/picture/list?offset=" + postData.offset + "&fetchSize=" + postData.fetchSize,
        success: function (data) {
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
            layer.close(index);
        }
    });

}