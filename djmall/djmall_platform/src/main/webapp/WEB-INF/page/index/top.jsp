<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/token/token.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/token/js.cookie.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
	<script type="text/javascript">
		setInterval(function() {
			var now = (new Date()).toLocaleString();
			$('#current-time').text(now);
		}, 1000);

		function exitt(){
			Cookies.remove("TOKEN");
			Cookies.remove("NICK_NAME");
			Cookies.remove("USER_ID");
			location.href="<%=request.getContextPath()%>/product/toShow";
		}

		$(
			function(){
				$("#token").text(Cookies.get("NICK_NAME"));
			}
		)
	</script>
</head>
<body>
<h4><a onclick="exitt()" style="color: #1E9FFF">退出</a></h4>
	<center>
		<h1>欢迎<span id="token"></span>的到来</h1>
	</center>
<div id="current-time" align="right"></div>
</body>

</html>