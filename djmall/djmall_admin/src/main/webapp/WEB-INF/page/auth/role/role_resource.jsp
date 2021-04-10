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
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.all.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript">
        var setting = {
            check:{
                enable:true
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId"
                }
            }
        };

        $(function (){
            $.get(
                "<%=request.getContextPath()%>/role/getRoleResourceTree/${roleId}",
                function (result){
                if (result.code == 200) {
                    $.fn.zTree.init($("#roleResource"), setting, result.data);
                }
            })
        });

        function update(){
            var treeObj = $.fn.zTree.getZTreeObj("roleResource");
            var nodes = treeObj.getCheckedNodes(true);
            //node:resourcesIds
            var node = "";
            for (var i = 0; i <nodes.length ; i++) {
                node+=nodes[i].id+",";
            }
            node=node.substr(0,node.length-1);
            $.post(
                "<%=request.getContextPath()%>/role/addRoleResource",
                //node:资源id集合
                {"ids":node,"id":${roleId}},
                function (data){
                    if(data.code==200){
                        layer.msg("保存成功");
                        parent.location.href="<%=request.getContextPath()%>/role/toShow "
                        return;
                    }
                    layer.msg(data.messages);
                }
            )
        }
    </script>
</head>
<body>
<input type="button" value="保存" onclick="update()">
<div id="roleResource" class="ztree"></div>
</body>
</html>