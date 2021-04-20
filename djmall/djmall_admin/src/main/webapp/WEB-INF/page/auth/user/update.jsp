<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/validate/jquery.validate.js"></script>
    <title></title>
</head>
<body align="center">
<h4></h4>
<form id="fm">
    <input type="hidden" name="id" value="${user.id}"/>
    <table class="layui-table">
        <tr>
            <td><label for="userName">用户名:</label></td>
            <td><input type="text" name="userName" id ="userName" value="${user.userName}"/></td>
        </tr>
        <tr>
            <td><label for="userPhone">手机号:</label></td>
            <td><input type="text" name="userPhone" id ="userPhone" value="${user.userPhone}"/></td>
        </tr>
        <tr>
            <td><label for="userEmail">邮箱:</label></td>
            <td><input type="text" name="userEmail" id ="userEmail" value="${user.userEmail}"/></td>
        </tr>
        <tr>
            <td>性别:</td>
            <td>
                <c:forEach var="s" items="${sexList}">
                    <input  type="radio" name="userSex" value="${s.code}" <c:if test="${s.code == user.userSex}">checked</c:if>>${s.dictName}
                </c:forEach>
            </td>
        </tr>
    </table>
    <shiro:hasPermission name="USER_UPDATE_BTN">
    <input type="submit" class="layui-btn layui-btn-sm" value="修改">
    </shiro:hasPermission>
</form>
</body>
<script type="text/javascript">

    $(function (){
        $("#fm").validate({
            rules:{
                userName:{
                    required:true,
                    rangelength:[2,6],
                    remote: {
                        type: "post",
                        url: "<%=request.getContextPath()%>/user/checkUserName/"+'${user.id}',
                        data:{
                            userName: function() {
                                return $("#userName").val();
                            }
                        }
                    }
                },
                userPhone:{
                    required:true,
                    rangelength:[11,11],
                    isphoneNum:true,
                    remote: {
                        type: "post",
                        url: "<%=request.getContextPath()%>/user/checkUserPhone/"+'${user.id}',
                        data:{
                            userPhone: function() {
                                return $("#userPhone").val();
                            }
                        }
                    }
                },
                userEmail:{
                    required:true,
                    email:true,
                    remote: {
                        type: "post",
                        url: "<%=request.getContextPath()%>/user/checkUserEmail/"+'${user.id}',
                        data:{
                            userEmail: function() {
                                return $("#userEmail").val();
                            }
                        }
                    }
                }
            },
            messages:{
                userName:{
                    required:"用户名不能为空",
                    rangelength:"输入必须在2~6个字符之间",
                    remote:"用户名重复"
                },
                userPhone:{
                    required:"不能为空",
                    rangelength:"请输入十一位手机号",
                    remote:"手机号重复"
                },
                userEmail:{
                    required:"不能为空",
                    email:"请输入正确的邮箱规则",
                    remote:"邮箱号重复"
                }
            },
            submitHandler:function(fm){
                var index = layer.load(0, {shade: 0.3});
                $.post(
                    "<%=request.getContextPath()%>/user/update",
                    $("#fm").serialize(),
                    function(result){
                        var i=result.code == 200 ? 6 : 5;
                        layer.msg(result.msg,
                            {icon:i, time:1000},
                            function(){
                                layer.close(index);
                                if(result.code == '200'){
                                    parent.location.href="<%=request.getContextPath()%>/user/toShow";
                                    return;
                                }
                            });
                    });
            }
        });

        //自定义手机号验证
        jQuery.validator.addMethod("isphoneNum", function(value, element) {
            var length = value.length;
            var mobile = /^1[3|5|7|8]{1}[0-9]{9}$/;
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
</html>