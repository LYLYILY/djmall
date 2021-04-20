<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css" media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/validate/jquery.validate.js"></script>
</head>
<body>
<form id="fm">
    <label for="attrName">属性名:</label>
    <input id="attrName" name="attrName" type="text"><br>
    <shiro:hasPermission name="ATTR_INSERT_BTN">
    <input type='submit' class='submit' value="新增商品属性"/>
    </shiro:hasPermission>
</form>
<table  class="layui-table">
    <tr>
        <th>编号</th>
        <th>属性名</th>
        <th>属性值</th>
        <th>操作</th>
    </tr>
    <tbody id="tb"></tbody>
</table><br/>
<%--<div id="page"></div>--%>
</body>
<script type="text/javascript">
    $(function(){
        show();
        // 在键盘按下并释放及提交后验证提交表单
        $("#fm").validate({
            rules: {
                attrName: {
                    required: true,
                    remote: {
                        type: "post",
                        url: "<%=request.getContextPath()%>/attr/findAttrName",
                        data: {
                            userName: function () {
                                return $("#attrName").val();
                            }
                        }
                    }
                },
            },
            messages: {
                attrName: {
                    required: "请输入属性名",
                    remote: "属性名重复"
                }
            },
            submitHandler: function (fm) {
                var index = layer.load();
                $.post("<%=request.getContextPath()%>/attr/add",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if (data.code == 200) {
                            parent.right.location.href = "<%=request.getContextPath()%>/attr/toShow";
                            return;
                        }
                        layer.msg(data.msg, {icon: 0});
                    })
            }
        });
    });
        //展示
        function show() {
            var index = layer.load(0, {shade: 0.3});
            $.post(
                "<%=request.getContextPath()%>/attr/show",
                $("#fm").serialize(),
                function (result) {
                    if (result.code != 200) {
                        layer.msg(result.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < result.data.length; i++) {
                        var list = result.data[i];
                        html += "<tr>";
                        html += "<td>" + list.id + "</td>";
                        html += "<td>" + list.attrName + "</td>";
                        html += "<td>" + list.attrValue + "</td>";
                        html += "<td>";
                        html += "<shiro:hasPermission name="ATTR_VALUE_BTN"><input type='button' class='layui-btn layui-btn-normal layui-btn-sm' value='关联属性' onclick='toRele(" + list.id + ")'/></shiro:hasPermission>";
                        html += "</td>";
                        html += "</tr>";
                    }
                    $("#tb").html(html);
                    layer.close(index);
                });
        }

    function toRele(id){
        layer.open({
            type: 2,
            title: '关联资源',
            shadeClose: true,
            move: false,
            shade: 0.8,
            area: ['380px', '90%'],
            content: "<%=request.getContextPath()%>/attr/toRelevance/" + id
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
