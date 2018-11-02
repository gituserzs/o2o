function changeVerifyCode(img) {
    img.src = "../kaptcha?"+Math.floor(Math.random()*100);
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    /*
        search
        得到的是url中query部分
        substr()
        返回一个从指定位置开始的指定长度的子字符串
        这里设置为1，是为了把url中的?号去掉
     */
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2])
    }
    return "";
}