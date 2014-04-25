<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>解压管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		
		<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
		<script type="text/javascript" src="${ctx }/js/validate/jquery.metadata.js"></script>
		<script type="text/javascript" src="${ctx }/js/validate/jquery.validate.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgcore.min.js"></script> 
		<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgdialog.min.js"></script>
		
		<script type="text/javascript">
		jQuery.noConflict();
		jQuery(document).ready(function(){
			jQuery("#myForm").validate();
		});
		
   	 	</script>
	
	<style type="text/css">
		.button{
			background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;
			border: 1px solid #D9E6F0;
		}
	</style>
	
	</head>

	<body>
		<form action="${ctx}/manage/removePledge/remove" method="post" id="myForm" name="myForm" enctype="multipart/form-data">
			<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
			<tr>
				<td><input type="file" name="picfile" id="picfile" value="浏览" class="required"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="解压" class="button" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px; background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/></td>
			</tr>
		</table>
		<input type="hidden" name="removeSupervisionCustomerId" value="${removeSupervisionCustomerId}" />
		</form>
	</body>
</html>

