<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/static/jq/login.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>


</head>
<script type="text/javascript">

	function login(){
		var index = layer.load();
		$.post(
			"<%=request.getContextPath()%>/user/login",
			$("#fm").serialize(),
			function(data){
				layer.close(index);
				if(data.code =='200'){
					layer.msg(data.msg);
					location.href="<%=request.getContextPath()%>/index/toIndex";
					return;
				}
				layer.msg(data.msg);
			}
		);
	}
	function add() {
		layer.open({
			type: 2,
			title: '添加页面',
			shadeClose: false,
			shade: 0.5,
			area: ['300px', '400px'],
			content:"<%=request.getContextPath()%>/user/toAdd"
		});
	}

	//判断当前窗口路径与加载路径是否一致。
	if(window.top.document.URL != document.URL){
		//将窗口路径与加载路径同步
		window.top.location = document.URL;
	}

</script>
<body class="container">
<form id="fm">
	<div class="login-form" style="width: 350px; height: 300px">
	账号：<input type="text" name="queryName" id="queryName" placeholder="用户名/手机号/邮箱"/><br/>
	密码：<input type="password" name="userPwd" id="pwd" /><br/>
	<a onclick="add()" style="color: #1E9FFF; font-size: 10px">还没有账号？点我去注册</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a onclick="pwd()" style="color: #1E9FFF; font-size: 10px">忘记密码？</a><br>
	<input value="登录" onclick="login()" class="btn"/>
	</div>
</form>
</body>

</html>