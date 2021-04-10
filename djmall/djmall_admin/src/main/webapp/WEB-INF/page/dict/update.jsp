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
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/my97/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/validate/jquery.validate.js"></script>
</head>
<body>
<form id="fm">
    <%--<input type="hidden" name="_method" value="PUT"/>--%>
    上级CODE:<input type="text" name="parentCode" value="${list.parentCode}" readonly/><br>
    CODE:<input type="text" name="code" value="${list.code}" readonly><br>
    字典名：<input id="dictName" name="dictName" type="text" value="${list.dictName}"><br>
    <input type="button" value="修改" onclick="upd()">
</form>
</body>
<script type="text/javascript">

    function upd(){
        $.post(
            "<%=request.getContextPath()%>/dict/update",
            $("#fm").serialize(),
            function(result){
                if(result.code == 200){
                    layer.msg("修改成功");
                    parent.location.href="<%=request.getContextPath()%>/dict/toShow";
                    return;
                }
                layer.msg(result.msg);
            });
        }
</script>
</html>