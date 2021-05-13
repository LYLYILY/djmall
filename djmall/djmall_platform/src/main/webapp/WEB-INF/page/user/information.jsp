<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/js.cookie.min.js"></script>

</head>
<body>
<form id="fm">
    <tr>
        <td><label for="nikeName">昵称</label></td>
        <td><input id="nikeName" name="nikeName" type="text" value="${user.nikeName}"></td>
    </tr><br> <br>
    头像<img style="width: 50px; height: 50px" src="${user.userPortrait}">
    <input type="file" id="f" name="file" value="file">
    <br> <br>
    性别: <c:forEach var="s" items="${sexList}">
    <input  type="radio" name="userSex" value="${s.code}" <c:if test="${user.userSex == s.code}"> checked</c:if>>${s.dictName}
</c:forEach><br> <br>
    邮箱:<input type="text" name="userEmail" value="${user.userEmail}"><br> <br>
    <input type="button" value="修改" onclick="update()">
</form>
</body>
<style>
    .error{
        color:red;
        font-size:9px;
    }
</style>
<script type="text/javascript">
    userId=Cookies.get("USER_ID");
    $(function() {
        // 在键盘按下并释放及提交后验证提交表单
        $("#fm").validate({
            rules: {
                nikeName: {
                    required: true,
                    minlength: 3,
                    remote: {
                        type: "post",
                        url: "<%=request.getContextPath()%>/userToken/checkUserNikeName/"+userId,
                        data:{
                            userName: function() {
                                return $("#userName").val();
                            }
                        }
                    }
                }

            },
            messages: {
                nikeName: {
                    required: "请输入昵称",
                    minlength: "昵称必需由三个字母组成",
                    remote:"昵称不能与用户名重复"

                }
            }
        });
    });


    //用户信息修改

    function update(){
        var index = layer.load(0, {shade: 0.3});
        var formData = new FormData($("#fm")[0]);
        $.ajax({
            url:"<%=request.getContextPath()%>/userToken/update?id="+Cookies.get("USER_ID"),
            type:"post",
            data:formData,
            contentType:false,
            processData: false,
            success: function (result){
                if (result.code == 200){
                    layer.msg("修改成功");
                    window.location.reload();
                }
                layer.close(index);
            }
        });
    }


</script>
</body>
</html>