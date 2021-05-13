// 必须要登录才可以访问的 URL
var token_get = function (url, callback) {
    if (check_login()) {
        $.ajax({
            type: "GET",
            url: url,
            beforeSend: function (request) {
                request.setRequestHeader("TOKEN", Cookies.get("TOKEN"));
            },
            success: callback
        });
    } else {
        logout();
    }
};
var token_post = function (url, param, callback) {
    if (check_login()) {
        $.ajax({
            type: "POST",
            url: url,
            data: param,
            beforeSend: function (request) {
                request.setRequestHeader("TOKEN", Cookies.get("TOKEN"));
            },
            success: callback
        });
    } else {
        logout();
    }
};

// 设置登录的cookie信息
var set_login = function (TOKEN, NICK_NAME, USER_ID) {
    Cookies.set("TOKEN", TOKEN, {expires: 1, path: "/"});
    Cookies.set("NICK_NAME", NICK_NAME, {expires: 1, path: "/"});
    Cookies.set("USER_ID", USER_ID, {expires: 1, path: "/"});
}

// 验证是否登陆
var check_login = function () {
    return (Cookies.get("TOKEN") != undefined && Cookies.get("NICK_NAME") != undefined);
};
// 退出登录
var logout = function () {
    Cookies.remove("TOKEN");
    Cookies.remove("NICK_NAME");
    Cookies.remove("USER_ID");
    toLogin();
}

function getToken() {
    return Cookies.get("TOKEN");
}

// 去登录
function toLogin() {
    layer.open({
        type: 2,
        content: "/toLogin",
        title: '登录',
        shade: 0.6
    });
}

// Ajax请求 返回拦截
$.ajaxSetup({
    type: "POST",
    error: function (jqXHR, textStatus, errorMsg) {  // 出错时默认的处理函数
        // jqXHR 是经过jQuery封装的XMLHttpRequest对象
        // textStatus 可能为： null、"timeout"、"error"、"abort"或"parsererror"
        // errorMsg 可能为： "Not Found"、"Internal Server Error"等
        switch (jqXHR.status) {
            case(500):
                alert("服务器系统内部错误");
                break;
            case(666):
                alert("未登录");
                logout();
                break;
            case(403):
                alert("当前用户没有权限");
                break;
            case(408):
                alert("请求超时");
                break;
            default:
                alert("未知错误");
        }
    }
});
$.ajaxSetup({
    type: "GET",
    error: function (jqXHR, textStatus, errorMsg) {  // 出错时默认的处理函数
        // jqXHR 是经过jQuery封装的XMLHttpRequest对象
        // textStatus 可能为： null、"timeout"、"error"、"abort"或"parsererror"
        // errorMsg 可能为： "Not Found"、"Internal Server Error"等
        switch (jqXHR.status) {
            case(500):
                alert("服务器系统内部错误");
                break;
            case(666):
                alert("未登录");
                logout();
                break;
            case(403):
                alert("当前用户没有权限");
                break;
            case(408):
                alert("请求超时");
                break;
            default:
                alert("未知错误");
        }
    }
});
// 清空全部cookie
function clear(){
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    console.log("需要删除的cookie名字：" + keys);
    if (keys) {
        for (var i = keys.length; i--;){
            //     document.cookie = keys[i] + "=; expire=" + date.toGMTString() + "; path=/";
            Cookies.remove(keys[i]);
        }
    }
}