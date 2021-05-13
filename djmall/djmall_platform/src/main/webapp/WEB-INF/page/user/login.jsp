<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/md5-min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.all.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/js.cookie.min.js"></script>

</head>
<body>
<div class="layui-tab">
    <ul class="layui-tab-title">
        <li class="layui-this">账号密码登录</li>
        <li>手机号登录</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form id="fm">
                <input type="hidden" id="salt"/>
                账号：<input type="text" id="userName" name="queryName" onblur="findSalt(this.value)"/><br/>
                密码：<input type="password" id="password" name="userPwd1"/><br/>
                <input id="pwd" name="userPwd" type="hidden">
                <input type="button" value="登录" onclick="login()"/>
                <input type="button" value="注册" onclick="register()"/>
            </form>
        </div>
        <div class="layui-tab-item">
            <form id="fm1">
                手机号：<input type="text" id="userPhone" name="userPhone"/><br/>
                图形验证码:<input name="verifyCode" id="verifyCode" type="text" style="width: 50px">&nbsp;&nbsp;
                <img src="<%=request.getContextPath()%>/userToken/getVerifCode" width="70px" height="20px" onclick=" this.src ='<%=request.getContextPath()%>/userToken/getVerifCode?d=' + Math.random();"/><br>
                验证码：<input name="smsCode" id="smsCode" type="text" style="width: 50px"><input type="button" value="发送短信验证码" onclick="getCode(this)"/><br/>
                <input type="button" value="登录" onclick="login1()"/>
                <input type="button" value="注册" onclick="register()"/>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">

    //普通登录
    function login(){
        $("#pwd").val(md5(md5($("#password").val())+$("#salt").val()));
        $.post(
            "<%=request.getContextPath()%>/userToken/loginToken",
            $("#fm").serialize(),
            function(result) {
                if (result.code == 200) {
                    //登录成功存cookie
                    Cookies.set("USER_ID", result.data.userId, 2);
                    Cookies.set("TOKEN", result.data.token, 2);
                    Cookies.set("NICK_NAME", result.data.nickName, 2);
                    //重新加载页面
                    parent.window.location.reload();
                    return;
                }
                layer.msg(result.msg);
            }
        )
    }

    //查找盐
    function findSalt(queryName){
        $.post(
            "<%=request.getContextPath()%>/userToken/getSalt",
            {"queryName":queryName},
            function (result){
                if(result.code == 200){
                    $("#salt").val(result.data);
                    return ;
                }
            }
        )
    }

    layui.use('element', function () {
        var element = layui.element;
        element.on('tab(filter'), function (data) {
            console.log(this);//当前tab标题所在得原始DOM元素
            console.log(data.index);//得到当前tab得所在下标
            console.log(data.elem);//得到当前得tab大容器
        }
    })

    /*$(function () {
        $("#loginBtn").click(function () {
            var overPwd = md5(md5("#pwd").val()) + $("#salt").val();
            $("#pwd").val(overPwd);
            $.post(
                "<%=request.getContextPath()%>/userToken/loginToken",
                $("#loginform").serialize(),
                function (result){
                    if(result.code == 200) {
                        cookie.set("TOKEN", result.data.token);
                        cookie.set("NICK_NAME", result.data.nickName);
                        praent.window.location.reload();
                    } else {
                        layer.msg(result.msg);
                        $("userPwd").val("");
                    }
                }
            )
        })
    })*/

    function getCode(btn){
        $.post(
            "<%=request.getContextPath()%>/userToken/getCode",
            {
                "verifyCode": $("#verifyCode").val(),
                "userPhone": $("#userPhone").val(),
            },
            function (result){
                if (result.code == 200){
                    layer.msg("发送成功");
                    $(btn).attr("disabled", "disabled");
                    var s = 60;
                    var interval = setInterval(function (){
                        $(btn).val("请"+ s-- +"秒后重新发送");
                        if (s <= 0){
                            clearInterval(interval);
                            $(btn).removeAttr("disabled", "disabled");
                            $(btn).val("请重新获取");
                        }
                    }, 1000);
                }else{
                    layer.msg(result.msg);
                }
            }
        );
    }

    function login1() {
        $.post(
            "<%=request.getContextPath()%>/userToken/phoneLogin",
            $("#fm1").serialize(),
            function(result) {
                if (result.code == 200) {
                    //登录成功存cookie
                    Cookies.set("USER_ID", result.data.userId, 2);
                    Cookies.set("TOKEN", result.data.token, 2);
                    Cookies.set("NICK_NAME", result.data.nickName, 2);
                    //重新加载页面
                    parent.window.location.reload();
                    return;
                }
                layer.msg(result.msg);
            }
        )
    }

    function register(){
        location.href = "<%=request.getContextPath()%>/userToken/toAdd";
    }
</script>
</body>
</html>