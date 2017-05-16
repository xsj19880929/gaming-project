/**
 * @author soldier
 */
function getNewsList() {
    var offset = $("#offset").val();
    var zoneType = $("#zoneType").val();
    $.ajax({
        async: true,
        type: "POST",
        contentType: 'application/json',
        url: "/news/list?offset=" + offset + "&zoneType=" + zoneType,
        success: function (data) {
            if (data != null) {
                $("#offset").val(Number(offset) + 20);
            } else {
                $("#loadMore").hide();
            }
        }
    });
}