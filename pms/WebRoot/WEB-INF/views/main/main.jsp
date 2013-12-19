<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>国家兔产业信息采集管理系统</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx}/css/styles.css">

		<title>管理中心</title>
		
	</head>

	<frameset rows="64,*" frameborder="NO" border="0" framespacing="0">
		<frame src="${ctx }/admin/top" noresize="noresize" frameborder="0"
			name="topFrame" scrolling="no" marginwidth="0" marginheight="0"/>
		<frameset cols="200,*" id="frame">
			<frame src="${ctx }/admin/left" name="leftFrame" noresize="noresize"
				marginwidth="0" marginheight="0" frameborder="0" scrolling="no" />
			<frame src="${ctx }/admin/right" name="right" marginwidth="0"
				marginheight="0" frameborder="0" scrolling="auto"/>
		</frameset>
	</frameset>
</html>
