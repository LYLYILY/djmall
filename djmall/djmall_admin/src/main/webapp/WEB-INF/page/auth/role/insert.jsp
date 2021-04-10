<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/validate/jquery.validate.js"></script>
</head>
<body>
<form id="fm">
    <label for="roleName">角色名</label>
    <input id="roleName" name="roleName" type="text"><br>
    <input class="submit" type="submit" value="提交">
</form>
</body>
<script type="text/javascript">


    $(function() {
        // 在键盘按下并释放及提交后验证提交表单
        $("#fm").validate({
            rules: {
                roleName: {
                    required: true,
                    remote: {
                        type: "post",
                        url: "<%=request.getContextPath()%>/role/getRoleName",
                        data:{
                            roleName: function() {
                                return $("#roleName").val();
                            }
                        }
                    }
                }
            },
            messages: {
                roleName: {
                    required: "请输入用户名",
                    remote: "用户名重复"
                }
            },
            submitHandler:function(fm){
                var index = layer.load(2,{shade:0.4});

                $.post(
                    "<%=request.getContextPath()%>/role/insert",
                    $("#fm").serialize(),
                    function(result){
                        layer.msg(result.msg, {
                            time: 1500
                        }, function(){
                            if(result.code == "200"){
                                layer.close(index);
                                parent.location.href="<%=request.getContextPath()%>/role/toShow";
                                return;
                            }
                            layer.close(index);
                        })
                    });
            }
        });
    });
</script>
<style>
    .error{
        color:red;
        font-size:10px;
    }
</style>
</html>