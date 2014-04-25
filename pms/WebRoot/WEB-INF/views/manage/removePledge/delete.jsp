<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'delete.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
      <form id="myForm" name="myForm" action="${ctx}/manage/removePledge/remove" method="post" class="myform" enctype="multipart/form-data">
		<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
			<tr>
				<td><input type="file" name="picfile" id="picfile" value="浏览" class="{required:true}"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="解压" class="button" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px; background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/></td>
			</tr>
		</table>
		<input type="hidden" name="removeSupervisionCustomerId" value="${removeSupervisionCustomerId}" />
	</form>
  </body>
</html>
