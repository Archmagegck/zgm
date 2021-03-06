<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		
		<script language="javascript" type="text/javascript" src="${ctx }/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx }/js/date/WdatePicker.js"></script>
		
		<script type="text/javascript">
    		
    	</script>
	
	<style type="text/css">
		.button{
			background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;
			border: 1px solid #D9E6F0;
		}
	</style>
	
	</head>

	<body>
		<form action="${ctx }/supervisor/inventory/saveCheckResult" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="center">
						盘存检测单
					</h3>
					<div>
						&nbsp;
					</div>
					<div align="left">
						盘存检测单号:${inventoryCheck.code} &nbsp;<br/>
						盘存检测单生成时间:${inventoryCheck.date} &nbsp;
					</div>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>托盘号</th>
								<th>款式大类</th>
								<th>检测件数</th>
								<th>检测重量(g)</th>
								<th>检测结果</th>
								
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${detailList}" var="inventoryCheckDetail" varStatus="status">
							<tr>
								<td>${inventoryCheckDetail.trayNo}&nbsp;</td>
								<td>
									${inventoryCheckDetail.style.name}&nbsp;														
								</td>
								<td>
									${inventoryCheckDetail.amount}&nbsp;					
									
								</td>
								<td>
										
									<fmt:formatNumber value="${inventoryCheckDetail.weight }" pattern="#,#00.00#"/>&nbsp;	
								</td>
								<td>
									${inventoryCheckDetail.checkResult.title}&nbsp;		
									
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					
				</div>
			</div>
		</form>
	</body>
</html>

