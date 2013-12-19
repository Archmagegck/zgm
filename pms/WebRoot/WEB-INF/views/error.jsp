<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="org.apache.commons.logging.LogFactory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'error.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
 	<%
        if (exception != null) //from JSP
        {
            String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
            LogFactory.getLog(requestUri).error(exception.getMessage(), exception);
        } else if (request.getAttribute("exception") != null) //from Spring
        {
            exception = (Exception) request.getAttribute("exception");
        }
    %>
  <body>
    未知错误，请联系管理员. <br>
  </body>
</html>
