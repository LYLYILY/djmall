<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <title>Insert title here</title>
</head>
<script type="text/javascript">
    function add(){
        var index = layer.load();
        $.post(
            "<%=request.getContextPath()%>/res/add",
            $("#fm").serialize(),
            function(result){
                layer.close(index);
                if(result.code == 200){
                    layer.msg("资源新增成功");
                    parent.location.href="<%=request.getContextPath()%>/res/toShow";
                    return;
                }
                layer.msg(result.msg);
            }
        );
    }
</script>
<body>
<form id="fm">
    <input type="hidden" name="parentId" value="${id}"><br>
    上级节点：<input type="text" value="${resourceName}" disabled="disabled"><br>
    资源名称：<input type="text" name="resourceName"><br>
    url：<input type="text" name="url"><br>
    资源编码：<input type="text" name="resourceCode"><br>
    资源类型：<select name="resourceType">
        <option value="">资源类型</option>
        <option value="1">菜单</option>
        <option value="2">按钮</option>
    </select><br>
    <shiro:hasPermission name="RESOURCE_ADD_BTN">
    <input type="button" onclick="add()" value="新增">
    </shiro:hasPermission>
</form>
</body>
</html>
