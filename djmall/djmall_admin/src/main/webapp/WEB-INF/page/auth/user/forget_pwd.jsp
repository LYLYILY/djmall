<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/md5-min.js"></script>
    <title>Insert title here</title>
    <script type="text/javascript">

        function getCode(btn){
            $.post(
                "<%=request.getContextPath()%>/user/getCode",
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

        function updatePwd(){
            $.post(
                "<%=request.getContextPath()%>/user/updatePwd",
                {
                    "userPhone":$("#userPhone").val(),
                    "smsCode":$("#smsCode").val(),
                    "userPwd":md5(md5($("#userPwd").val()) + $("#salt").val()),
                    "confirmPassword":md5(md5($("#confirmPassword").val()) + $("#salt").val()),
                    "salt":$("#salt").val()
                },
                function (result){
                    if (result.code == 200){
                        layer.msg("修改成功");
                        parent.location.href = "<%=request.getContextPath()%>/user/toLogin";
                    }else{
                        layer.msg(result.msg);
                    }
                }
            );
        }


        function removes(){
            parent.location.href = "<%=request.getContextPath()%>/user/toLogin";
        }

    </script>
</head>
<body>
<form id="fm">
    <input type="hidden" name="salt" id="salt" value="${salt}">
    <table>
        <tr>
            <td>手机号</td>
            <td><input name="userPhone" id="userPhone" type="text"></td>
        </tr>
        <tr>
            <td>图形验证码</td>
            <td><input name="verifyCode" id="verifyCode" type="text" style="width: 50px">&nbsp;&nbsp;
                <img src="<%=request.getContextPath()%>/user/getVerifCode" width="70px" height="20px" onclick=" this.src ='<%=request.getContextPath()%>/user/getVerifCode?d=' + Math.random();"/></td>
        </tr>
        <tr>
            <td>短信验证码</td>
            <td><input name="smsCode" id="smsCode" type="text" style="width: 50px"><input type="button" value="发送短信验证码" onclick="getCode(this)"/></td>
        </tr>
        <tr>
            <td>新密码</td>
            <td><input name="userPwd" id="userPwd" type="password"></td>
        </tr>
        <tr>
            <td>确认新密码</td>
            <td><input id="confirmPassword" name="confirmPassword" type="password"></td>
        </tr>
        <tr>
            <th colspan="2"><input type="button" value="确认修改" onclick="updatePwd()">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="取消" onclick="removes()"></th>
        </tr>
    </table>
</form>
</body>
</html>