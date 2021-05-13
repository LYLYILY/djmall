<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>授权</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
</head>
<script type="text/javascript">

    $(function(){
        success();
    });


    function success(){
        var index = layer.load();
        $.post(
            "<%=request.getContextPath()%>/role/show",
            $("#tb").serialize(),
            function(result){
                var html="";
                for(var i=0;i<result.data.length;i++){
                    var list=result.data[i];
                    html+="<tr>";
                    if (list.id  == ${roleId}){
                        html+="<td><input type='radio' id='role_id' name='id' value='"+list.id+"' checked>"+list.id+"</td>"
                    }else{
                        html+="<td><input type='radio' id='role_id' name='id' value='"+list.id+"' >"+list.id+"</td>"

                    }
                    html+="<td>"+list.roleName+"</td>"
                    html+="</tr>";
                }
                $("#tb").html(html);
                layer.close(index);
            });
    }


    function insertUserRole(){
        var index = layer.load();
        var roleId = $('input[id="role_id"]:checked').val();
        if (roleId == null){
            layer.msg("请选择授权身份");
            return;
        }
        $.post(
            "<%=request.getContextPath()%>/user/insertUserRole",
            {"roleId":roleId, "id": '${userId}'},
            function (result){
                layer.close(index);
                if (result.code == 200){
                    parent.location.href = "<%=request.getContextPath()%>/user/toShow";
                }
        })
    }

</script>
<body>
<center>
<table class="layui-table">
    <tr>
        <td>ID</td>
        <td>角色名</td>
    </tr>
    <tbody id="tb">
    </tbody>
</table>
    <shiro:hasPermission name="USER_PERMISSION_BTN">
<input type="button" value="确定" class="layui-btn layui-btn-sm" onclick="insertUserRole()">
    </shiro:hasPermission>
</center>
</body>
</html>