<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>盘存检测单</title>

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
		 <form id="myForm" name="myForm" action="${ctx}/supervisor/inventoryCheck/save" method="post" class="myform">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						盘存检测单
					</h3>
					<div>
						&nbsp;
					</div>
					<div align="left">
						<c:if test="${not empty messageOK}">
							<div class="flash notice">
								&nbsp;&nbsp;${messageOK}
							</div>
						</c:if>
						<c:if test="${not empty messageErr}">
							<div class="flash error">
								&nbsp;&nbsp;${messageErr}
							</div>
						</c:if>
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="50%">
						<thead>
							<tr>
								<th width="8%">托盘号</th>
								<th>款式大类</th>
								<th>检测件数</th>
							</tr>
						</thead>
						<c:forEach items="${details}" var="detail" varStatus="status">
							<tr>
								<td>
									${detail.trayNo}&nbsp;
									<input type="hidden" name="inventoryCheckDetails[${status.index}].trayNo" value="${detail.trayNo}">
								</td>
								<td>
									<select id="inventoryCheckDetailsStyle[${status.index}]" name="inventoryCheckDetails[${status.index}].style.id" class="required">
										<c:forEach items="${styleList}" var="style">
											<option value = "${style.id }">${style.name }</option>
										</c:forEach>
									</select>
								</td>
								<td>
									<input id="inventoryCheckDetailsAmount[${status.index}]" name="inventoryCheckDetails[${status.index}].amount" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
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

