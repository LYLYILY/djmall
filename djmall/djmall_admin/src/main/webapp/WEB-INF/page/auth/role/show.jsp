<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>角色展示列表</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
</head>
<script type="text/javascript">

    $(function(){
        success();
    });

    function success(){
        $.post(
            "<%=request.getContextPath()%>/role/show",
            $("#tb").serialize(),
            function(result){
                var html="";
                for(var i=0;i<result.data.length;i++){
                    html+="<tr>";
                    html+="<td>"+result.data[i].id+"</td>"
                    html+="<td>"+result.data[i].roleName+"</td>"
                    html+="<td>"
                    html+="<shiro:hasPermission name="ROLE_RESOURCE_BTN"><input type='button' value='关联资源' onclick='gl("+result.data[i].id+")'></shiro:hasPermission>";
                    html+="<shiro:hasPermission name="ROLE_UPDATE_BTN"><input type='button' value='编辑' onclick='updateRole("+result.data[i].id+")'></shiro:hasPermission>";
                    html+="<shiro:hasPermission name="ROLE_DEL_BTN"><input type='button' value='删除' onclick='del("+result.data[i].id+")'></shiro:hasPermission>";
                    html+="</td>";
                    html+="</tr>";
                }
                $("#tb").html(html);
            });
    }
    function insertRole(){
        layer.open({
            type: 2,
            title: '新增角色',
            shadeClose: true,
            move: false,
            shade: 0.8,
            area: ['380px', '90%'],
            content: "<%=request.getContextPath()%>/role/toInsert"
        });
    }
    function updateRole(id){
        layer.open({
            type: 2,
            title: '修改角色',
            shadeClose: true,
            move: false,
            shade: 0.8,
            area: ['380px', '90%'],
            content: "<%=request.getContextPath()%>/role/toUpdate/"+id
        });
    }
    function gl(roleId){
        layer.open({
            type: 2,
            title: '关联资源',
            shadeClose: true,
            move: false,
            shade: 0.8,
            area: ['380px', '90%'],
            content: "<%=request.getContextPath()%>/role/toRoleResource/"+roleId
        });
    }

</script>
<body>
<shiro:hasPermission name="ROLE_ADD_BTN">
<input type="button" value="新增" onclick="insertRole()">
</shiro:hasPermission>
<table border="1">
    <tr>
        <td>ID</td>
        <td>角色名</td>
        <td>操作</td>
    </tr>
    <tbody id="tb">
    </tbody>
</table>

</body>
</html>