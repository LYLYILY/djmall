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
                userName: {
                    required: true,
                    minlength: 2,
                    remote: {
                        type: "post",
                        url: "<%=request.getContextPath()%>/userToken/checkUserName",
                        data:{
                            userName: function() {
                                return $("#userName").val();
                            }
                        }
                    }
                },
                userPwd: {
                    required: true,
                    minlength: 3
                },
                confirmPassword: {
                    required: true,
                    minlength: 3,
                    equalTo: "#password"
                },
                userEmail: {
                    required: true,
                    email: true,
                    remote: {
                        type: "post",
                        url: "<%=request.getContextPath()%>/userToken/checkUserEmail",
                        data:{
                            userEmail: function() {
                                return $("#userEmail").val();
                            }
                        }
                    }
                },
                userPhone: {
                    required: true,
                    maxlength:11,
                    isphoneNum:true,
                    remote: {
                        type: "post",
                        url: "<%=request.getContextPath()%>/userToken/checkUserPhone",
                        data:{
                            userPhone: function() {
                                return $("#userPhone").val();
                            }
                        }
                    }
                },
                userSex:{
                    required: true
                }

            },
            messages: {
                userName: {
                    required: "请输入用户名",
                    minlength: "用户名必需由两个字母组成",
                    remote:"用户名重复"
                },
                userPwd: {
                    required: "请输入密码",
                    minlength: "密码长度不能小于 3 个字母"
                },
                confirmPassword: {
                    required: "请输入密码",
                    minlength: "密码长度不能小于 3 个字母",
                    equalTo: "两次密码输入不一致"
                },
                userEmail:{
                    required:"请输入邮箱",
                    email:"请输入一个正确的邮箱",
                    remote:"邮箱已重复"
                } ,
                userPhone:{
                    required:"请输入手机号",
                    maxlength:"请填写11位的手机号",
                    minlength:"请填写11位的手机号",
                    isphoneNum:"请填写正确的手机号码",
                    remote:"手机号已存在"
                },
                userSex:{
                    required:"请选择性别"
                }
            },
            submitHandler:function(fm){
                var index = layer.load();
                $("#pwd").val(md5(md5($("#password").val())+$("#salt").val()));
                $("#pwd1").val(md5(md5($("#password").val())+$("#salt").val()));
                $.post("<%=request.getContextPath()%>/userToken/addRegister",
                    $("#fm").serialize(),
                    function(data){
                        layer.close(index);
                        if(data.code == 200){
                            layer.msg("注册成功正在返回")
                            parent.location.href="<%=request.getContextPath()%>/product/toShow";
                            return ;
                        }
                        layer.msg(data.msg,{icon:0});
                    })


            }
        });
        //自定义手机号验证
        jQuery.validator.addMethod("isphoneNum", function(value, element) {
            var length = value.length;
            var mobile = /^1[3|7|5|8]{1}[0-9]{9}$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "请正确填写您的手机号码");
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
    <input name="userRank" type="hidden" value=3>
    <input name="userStatus" type="hidden" value="NORMAL">
    <input name="salt" id="salt" type="hidden" value="${salt}">
    <input id="nikeName" name="nikeName" type="hidden">
    <input id="pwd" name="userPwd" type="hidden">
    <input id="pwd1" name="confirmPassword" type="hidden">
    <table>
        <tr>
            <td><label for="userName">用户名</label></td>
            <td><input id="userName" name="userName" type="text"></td>
        </tr>



        <tr>
            <td><label for="password">密码</label></td>
            <td><input id="password" name="userPwd1" type="password"></td>
        </tr>
        <tr>
            <td><label for="confirm_password">验证密码</label></td>
            <td><input id="confirm_password" name="confirmPassword1" type="password"></td>
        </tr>
        <tr>
            <td><label for="userPhone">手机号</label></td>
            <td><input id="userPhone" name="userPhone" type="text"></td>
        </tr>
        <tr>
            <td><label for="userEmail">邮箱</label></td>
            <td><input id="userEmail" name="userEmail" type="text"></td>
        </tr>
        <tr>
            <td><label for="userSex">性别</label></td>
            <td> <c:forEach var="s" items="${sexList}">
                    <input  type="radio" name="userSex" value="${s.code}">${s.dictName}
                </c:forEach>
                <label id="userSex" class="error" for="userSex"></label>
            </td>
        </tr>

        <tr>
            <th colspan="2"><input class="submit" type="submit" value="提交"></th>
        </tr>
    </table>
</form>
</body>
</html>