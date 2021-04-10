<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.all.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
<title>left</title>
	<script>
		var setting = {
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				},
				 key : {
					name : "resourceName",
				 	url : "xUrl"
				 }
			},
			callback:{    //第一步
				onClick:function (event, treeId, treeNode) {
					if (!treeNode.isParent) {
						parent.right.location.href = "<%=request.getContextPath() %>" + treeNode.url;
					}
				}
			}
		};

		$(function(){
			$.get(
				"<%=request.getContextPath()%>/index/getLeft",
				function(result){
					$.fn.zTree.init($("#treeDemo"), setting, result.data);
				}
			);
		});
	</script>
</head>
<body>
	<div id="treeDemo" class="ztree"></div>
</body>

</html>