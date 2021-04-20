<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
</head>
<body>
<form id="fm">
    <input type="hidden" name="id" value="${id}">
    运费:<input type="text" name="freight" value="${freight}" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d{0,1})?).*$/g, '$1')"/><br>
    <shiro:hasPermission name="FREIGHT_UPDATE_BTN">
        <input type="button" value="修改" onclick="upd()">
    </shiro:hasPermission>
</form>
</body>
<script type="text/javascript">
    function upd(){
        $.post(
            "<%=request.getContextPath()%>/freight/update",
            $("#fm").serialize(),
            function(result){
                if(result.code == 200){
                    layer.msg("修改成功");
                    parent.location.href="<%=request.getContextPath()%>/freight/toShow";
                    return;
                }
                layer.msg(result.msg);
            });
    }
</script>
</html>
