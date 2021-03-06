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
        url: "/picture/tagList?offset=" + postData.offset + "&fetchSize=" + postData.fetchSize,
        success: function (data) {
            layer.close(index);
            if (data.list.length > 0) {
                $("#offset").val(Number(postData.offset) + Number(postData.fetchSize));
                for (var i in data.list) {
                    var map = {};
                    map.title = data.list[i].title;
                    map.titleImage = data.list[i].titleImage;
                    map.href = data.htmlTemplate.baseUrl + "/picture/" + data.list[i].id + ".html";
                    map.imageSrc = data.htmlTemplate.imageUrl + "/image" + data.list[i].image;
                    map.description = data.list[i].description;
                    map.createTime = formatDate(new Date(data.list[i].createTime), "yyy-MM-dd");
                    var html = htmlRep(data.htmlTemplate.htmlTemplate, map);
                    $("#newsListData").append(html);
                }
                $('.wall').jaliswall({item: '.article'});
            } else {
                $("#loadMore").hide();
            }
        }
    });

}