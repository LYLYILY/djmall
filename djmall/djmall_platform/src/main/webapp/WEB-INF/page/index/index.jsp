<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理系统</title>
</head>
	<frameset rows="20%,*">
		<frame src="<%=request.getContextPath()%>/index/toTop" name="top" target="right">
	<frameset cols="17%,*">
		<frame src="<%=request.getContextPath()%>/index/toLeft" name="left" target="right">
		<frame src="<%=request.getContextPath()%>/index/toRight" name="right" target="right">
	</frameset>
	</frameset>
</html>