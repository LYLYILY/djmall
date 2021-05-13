<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/token/js.cookie.min.js"></script>

	<title>left</title>
	<script>

	</script>
</head>
<body>
<table border="1px solid" cellspacing="0" cellpadding="10" align="center">
	<tr>
		<th><a href="javascript:void(0)" onclick="toUser()" target="right" style="color: black">个人信息</a></th>
	</tr>
	<tr>
		<th><a href="<%=request.getContextPath()%>/userToken/toShippingAddress" target="right" style="color: black">收货地址</a></th>
	</tr>
	<tr>
		<th><a href="<%=request.getContextPath()%>/order/toShow" target="right" style="color: black">我的订单</a></th>
	</tr>
</table>
</body>
<script type="text/javascript">
	function toUser(){
		userId =Cookies.get("USER_ID");
		parent.right.location.href="<%=request.getContextPath()%>/userToken/toPersonalInformation/"+userId;
	}

</script>
</html>