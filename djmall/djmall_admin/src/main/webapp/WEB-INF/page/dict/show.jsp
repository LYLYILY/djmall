<%--
  Created by IntelliJ IDEA.
  User: ly
  Date: 2021/4/7
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <title>Title</title>
</head>
<body>
<form id="fm">
分类上级：<select name="parentCode">
            <option VALUE="SYSTEM">SYSTEM</option>
            <c:forEach items="${system}" var="s">
                <option value="${s.code}">${s.dictName}</option>
            </c:forEach>
        </select><br>
分类名称：<input type="text" name="dictName"><br>
分类code:<input type='text' name="code"/><br>
<input type='button' class='layui-btn layui-btn-normal layui-btn-sm' value="新增" onclick="add()"/>
    <table class="layui-table">
        <tr>
            <th>CODE</th>
            <th>字典名</th>
            <th>上级CODE</th>
            <th>操作</th>
        </tr>
        <tbody id="tb"></tbody>
    </table><br/>
    <div id="page"></div>
</form>
</body>
<script type="text/javascript">
    $(
        function(){
            show();
        }
    );

    //展示
    function show(){
        var index = layer.load(0, {shade: 0.3});
        $.post(
            "<%=request.getContextPath()%>/dict/show",
            $("#fm").serialize(),
            function(result){
                if(result.code!=200){
                    layer.msg(result.msg);
                    return;
                }
                var html = "";
                for (var i = 0; i < result.data.length; i++) {
                    var list=result.data[i];
                    html+="<tr>";
                    html+="<td>"+list.code+"</td>";
                    html+="<td>"+list.dictName+"</td>";
                    html+="<td>"+list.parentCode+"</td>";
                    html+="<td><input type='button' class='layui-btn layui-btn-normal layui-btn-sm' value='修改' onclick='upd(&quot;"+ list.code + "&quot;)'/>";
                    html+="</tr>";
                }
                $("#tb").html(html);
                layer.close(index);
            });
    }

    function upd(code){
        layer.open({
            type: 2,
            area: ['500px', '400px'],
            content: ['<%=request.getContextPath()%>/dict/toUpd/' +code, 'no']
        });
    }


    function add(){
        $.post(
            "<%=request.getContextPath()%>/dict/add",
            $("#fm").serialize(),
            function (result){
                if (result.code == 200){
                    layer.msg("新增成功");
                    show();
                    return;
                }
                layer.msg(result.msg);
            }
        );
    }
</script>
</html>
