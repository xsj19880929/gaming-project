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
        url: "/video/list?offset=" + postData.offset + "&fetchSize=" + postData.fetchSize,
        success: function (data) {
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
            layer.close(index);
        }
    });
}