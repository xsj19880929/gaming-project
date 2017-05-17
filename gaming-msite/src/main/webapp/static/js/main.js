function htmlRep(html, mapData) {
    var newHtml = html;
    for (var key in mapData) {
        var strR = "{" + key + "}";
        newHtml = newHtml.replace(new RegExp(strR, "gm"), mapData[key]);
    }
    return newHtml;
}