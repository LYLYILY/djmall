<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>资源展示界面</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.all.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bootstrap/css/bootstrap.min.css" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/bootstrap/js/bootstrap.min.js"></script>
</head>
<SCRIPT type="text/javascript">
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            },
            key: {
                name: "resourceName",
                url: "xurl"
            }
        }
    };

    $(function(){
        show();
    })
    function show(){
        $.post(
            "<%=request.getContextPath()%>/res/show",
            {},
            function(result){
                $.fn.zTree.init($("#treeDemo"), setting, result.data);
            }
        );
    }

    //添加
    function add() {
        var parentId = 0;
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var selectedNodes = treeObj.getSelectedNodes();
        if(selectedNodes != undefined && selectedNodes.length > 0){
            var selectedNode = selectedNodes[0];
            parentId = selectedNode.id;
        }
        //弹窗
        layer.open({
            type: 2,
            title: '添加页面',
            shadeClose: false,
            shade: 0.5,
            area: ['400px', '300px'],
            content:"<%=request.getContextPath()%>/res/toAdd?id="+parentId
        });
    }

    //修改
    function toUpdate(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var selectedNodes = treeObj.getSelectedNodes();
        if (selectedNodes == undefined || selectedNodes.length == 0){
            layer.msg("请选择要修改的资源");
            return;
        }
        var selectedNode = selectedNodes[0];
        $("#resourceName").val(selectedNode.resourceName);
        $("#parentId").val(selectedNode.id);
        $("#url").val(selectedNode.url);
        $("#resourceCode").val(selectedNode.resourceCode);
        $("#resourceType").val(selectedNode.resourceType);
        $("#myModal").modal();
    }

    function update(){
        var index = layer.load();
        $.post(
            "<%=request.getContextPath()%>/res/update",
            $("#fm").serialize(),
            function (result){
                layer.close(index);
                if (result.code == 200){
                    layer.msg("修改成功");
                    $("#myModal").modal("hide");
                    show();
                    return;
                }
                layer.msg(result.msg);
            }
        );
    }

    function getChildNode(parentNode){
        var ids = "";
        var childList = parentNode.children;
        for (var i = 0; i < childList.length; i++) {
            var child = childList[i];
            ids += child.id + ",";
            if (child.isParent){
                ids += getChildNode(child);
            }
        }
        return ids;
    }

</SCRIPT>
<body>
<shiro:hasPermission name="RESOURCE_ADD_BTN">
    <input type="button" class="btn btn-info" value="新增资源" onclick="add()">
</shiro:hasPermission>
<shiro:hasPermission name="RESOURCE_UPDATE_BTN">
    <input type="button" class="btn btn-info" value="修改" onclick="toUpdate()">
</shiro:hasPermission>
<shiro:hasPermission name="RESOURCE_DEL_BTN">
    <input type="button" class="btn btn-info" value="删除" onclick="del()">
</shiro:hasPermission>
<h1 id="treeDemo" class="ztree"></h1>

<%----%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">修改页面</h4>
            </div>
            <div class="modal-body">
                <form id="fm">
                    <input type="hidden" id="parentId" name="id" ><br>
                    资源名称：<input type="text" id="resourceName" name="resourceName"><br>
                    url：<input type="text" id="url" name="url"><br>
                    资源编码：<input type="text" id="resourceCode" name="resourceCode" readonly><br>
                    资源类型：<select name="resourceType" id="resourceType">
                                <option value="">资源类型</option>
                                <option value="1">菜单</option>
                                <option value="2">按钮</option>
                            </select><br>
                </form>
            </div>
            <div class="modal-footer">
                <shiro:hasPermission name="RESOURCE_UPDATE_BTN">
                <input type="button" class="btn btn-primary" value="保存" onclick="update()">
                </shiro:hasPermission>
            </div>
        </div>
    </div>
</div>
</body>
</html>
