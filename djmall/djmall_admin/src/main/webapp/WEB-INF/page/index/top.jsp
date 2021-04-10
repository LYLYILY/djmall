<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
	<script type="text/javascript">
		setInterval(function() {
			var now = (new Date()).toLocaleString();
			$('#current-time').text(now);
		}, 1000);
	</script>
</head>
<body>
<h4><a href="<%=request.getContextPath()%>/user/toLogin">退出</a></h4>
	<center>
		<h1>欢迎<span style="color: chartreuse">${USER.userName}</span>进入平台</h1>
	</center>
<div id="current-time" align="right"></div>

</body>
</html>