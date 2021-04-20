<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/validate/jquery.validate.js"></script>
</head>
<body>
<form id="fm">
    <input type="hidden" name="attrId" value="${id}"><br>
        属性名：<input type="text" value="${attrName}" disabled="disabled"><br>
        <label for="attrValue">属性值:</label>
        <input id="attrValue" name="attrValue" type="text"><br>
    <shiro:hasPermission name="ATTR_VALUE_INSERT_BTN">
        <input type='submit' class='submit' value="新增" />
    </shiro:hasPermission>
</form>
<table  class="layui-table">
    <tr>
        <th>编号</th>
        <th>属性名</th>
        <th>操作</th>
    </tr>
    <tbody id="tb"></tbody>
</table><br/>
<div id="page"></div>

</body>
<script type="text/javascript">
    $(function(){
        show();
        $("#fm").validate({
            rules: {
                attrValue: {
                    required: true,
                    remote: {
                        type: "post",
                        url: "<%=request.getContextPath()%>/attr/findAttrValueName",
                        data:{
                            attrValue: function() {
                                return $("#attrValue").val();
                            }
                        }
                    }
                }
            },
            messages: {
                attrValue: {
                    required: "请输入商品属性",
                    remote: "商品属性重复"
                }
            },
            submitHandler:function(fm){
                var index = layer.load(2,{shade:0.4});

                $.post(
                    "<%=request.getContextPath()%>/attr/insert",
                    $("#fm").serialize(),
                    function(result){
                        layer.msg(result.msg, {
                            time: 1500
                        }, function(){
                            if(result.code == "200"){
                                layer.close(index);
                                parent.location.href="<%=request.getContextPath()%>/attr/toShow";
                                return;
                            }
                            layer.close(index);
                        })
                    });
            }
        });
    });

    //展示
    function show(){
        var index = layer.load(0, {shade: 0.3});
        $.post(
            "<%=request.getContextPath()%>/attr/list/${id}",
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
                    html+="<td>"+list.id+"</td>";
                    html+="<td>"+list.attrValue+"</td>";
                    html+="<td>";
                    html+="<shiro:hasPermission name="ATTR_VALUE_DEL_BTN"><input type='button' class='layui-btn layui-btn-normal layui-btn-sm' value='移除' onclick='del("+list.id+")'/></shiro:hasPermission>";
                    html+="</td>";
                    html+="</tr>";
                }
                $("#tb").html(html);
                layer.close(index);
            });
    }
    function del(id) {
        var index = layer.load(2,{shade:0.4});
        $.post(
            "<%=request.getContextPath()%>/attr/del/" + id,
            $("#fm").serialize(),
            function (result) {
                layer.msg(result.msg, {
                    time: 1500
                }, function () {
                    if (result.code == "200") {
                        layer.close(index);
                        parent.location.href = "<%=request.getContextPath()%>/attr/toShow";
                        return;
                    }
                    layer.close(index);
                })
            });
    }

</script>
<style>
    .error{
        color:red;
        font-size:9px;
    }
</style>
</html>
