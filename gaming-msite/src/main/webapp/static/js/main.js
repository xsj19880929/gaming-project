function htmlRep(html, mapData) {
    var newHtml = html;
    for (var key in mapData) {
        var strR = "{" + key + "}";
        newHtml = newHtml.replace(new RegExp(strR, "gm"), mapData[key]);
    }
    return newHtml;
}

function formatDate(date, format) {
    var v = "";
    if (typeof date == "string" || typeof date != "object") {
        return;
    }
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    var weekDay = date.getDay();
    var ms = date.getMilliseconds();
    var weekDayString = "";
    if (weekDay == 1) {
        weekDayString = "星期一";
    } else if (weekDay == 2) {
        weekDayString = "星期二";
    } else if (weekDay == 3) {
        weekDayString = "星期三";
    } else if (weekDay == 4) {
        weekDayString = "星期四";
    } else if (weekDay == 5) {
        weekDayString = "星期五";
    } else if (weekDay == 6) {
        weekDayString = "星期六";
    } else if (weekDay == 7) {
        weekDayString = "星期日";
    }
    v = format;
    v = v.replace(/yyyy/g, year);
    v = v.replace(/YYYY/g, year);
    v = v.replace(/yy/g, (year + "").substring(2, 4));
    v = v.replace(/YY/g, (year + "").substring(2, 4));
    var monthStr = ("0" + month);
    v = v.replace(/MM/g, monthStr.substring(monthStr.length - 2));
    var dayStr = ("0" + day);
    v = v.replace(/dd/g, dayStr.substring(dayStr.length - 2));
    var hourStr = ("0" + hour);
    v = v.replace(/HH/g, hourStr.substring(hourStr.length - 2));
    v = v.replace(/hh/g, hourStr.substring(hourStr.length - 2));
    var minuteStr = ("0" + minute);
    v = v.replace(/mm/g, minuteStr.substring(minuteStr.length - 2));
    v = v.replace(/sss/g, ms);
    v = v.replace(/SSS/g, ms);
    var secondStr = ("0" + second);
    v = v.replace(/ss/g, secondStr.substring(secondStr.length - 2));
    v = v.replace(/SS/g, secondStr.substring(secondStr.length - 2));
    v = v.replace(/E/g, weekDayString);
    return v;
}

function jsonPostData(selector) {
    var jsonData = {};
    $(selector + ">input").each(function () {
        jsonData[$(this).attr("name")] = $(this).attr("value");
    });
    return jsonData;
}