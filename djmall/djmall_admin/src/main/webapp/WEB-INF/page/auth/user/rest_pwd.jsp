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
        $(function() {
            // 在键盘按下并释放及提交后验证提交表单
            $("#fm").validate({
                rules: {
                    userPwd: {
                        required: true,
                        minlength: 3
                    },
                    confirmPassword: {
                        required: true,
                        minlength: 3,
                        equalTo: "#userPwd"
                    },

                },
                messages: {
                    userPwd: {
                        required: "请输入密码",
                        minlength: "密码长度不能小于 3 个字母"
                    },
                    confirmPassword: {
                        required: "请输入密码",
                        minlength: "密码长度不能小于 3 个字母",
                        equalTo: "两次密码输入不一致"
                    },
                },
                submitHandler:function(fm){
                    var index = layer.load();
                    $("#userPwd").val(md5(md5($("#userPwd").val())+$("#salt").val()));
                    $("#confirmPassword").val(md5(md5($("#confirmPassword").val())+$("#salt").val()));
                    $.post(
                        "<%=request.getContextPath()%>/user/restUserPwd",
                        $("#fm").serialize(),
                        function(data){
                            layer.close(index);
                            if(data.code == 200){
                                layer.msg("修改密码成功，请重新登录！");
                                parent.location.href="<%=request.getContextPath()%>/user/toLogin";
                                return ;
                            }
                            layer.msg(data.msg,{icon:0});
                        })
                }
            });
        });

    </script>
    <style>
        .error{
            color:red;
            font-size:9px;
        }
    </style>
</head>
<body>
<form id="fm">
    <input name="salt" id="salt" type="hidden" value="${salt}">
    <input name="queryName" id="queryName" type="hidden" value="${queryName}">
    <table>
        <tr>
            <td><label for="userPwd">密码</label></td>
            <td><input id="userPwd" name="userPwd" type="password"></td>
        </tr>
        <tr>
            <td><label for="confirmPassword">确认新密码</label></td>
            <td><input id="confirmPassword" name="confirmPassword" type="password"></td>
        </tr>
        <tr>
            <th colspan="2"><input class="submit" type="submit" value="提交"></th>
        </tr>
    </table>
</form>
</body>
</html>