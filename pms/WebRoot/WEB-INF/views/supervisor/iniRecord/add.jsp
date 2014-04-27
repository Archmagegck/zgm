<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>初始状态录入</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/js/validate/jquery.validate.css">
		<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
		<script type="text/javascript" src="${ctx }/js/validate/jquery.metadata.js"></script>
		<script type="text/javascript" src="${ctx }/js/validate/jquery.validate.js"></script>
	
		<script type="text/javascript">
		$(document).ready(function(){
			$("#myForm").validate();
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
		 <form id="myForm" name="myForm" action="${ctx}/supervisor/iniRecord/save" method="post" class="myform">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						初始状态录入
					</h3>
					<div>
						&nbsp;
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="50%">
						<thead>
							<tr>
								<th width="8%">序号</th>
								<th>款式大类</th>
								<th>重量(g)</th>
							</tr>
						</thead>
						<c:forEach items="${styleList}" var="style" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${style.name }&nbsp;
									<input type="hidden" name="iniRecords[${status.index}].style.id" value="${style.id}">
								</td>
								<td>
									<input id="iniRecords[${status.index}].weight" name="iniRecords[${status.index}].weight" class="{number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
									<input type="hidden" name="iniRecords[${status.index}].warehouse.id" value="${sessionScope.warehouseId}">
									&nbsp;
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div style="margin-bottom: 5px;padding: 3px;" align="center">
    			<input id="button1" type="submit" value="录入" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px; background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
    		</div>
		</form>
	</body>
</html>

