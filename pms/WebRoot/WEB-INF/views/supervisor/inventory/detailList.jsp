<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>库存盘点及成色检测记录明细</title>

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
		<form action="${ctx }/supervisor/inventory" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="center">
						库存盘点及成色检测记录明细
					</h3>
					<div>
						&nbsp;
					</div>
					<div align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						库存记录编号：${inventory.code}
					</div>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>监管员姓名</th>
								<th>时间</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${inventory.supName}</td>
								<td>${inventory.date}</td>
							</tr>
						</tbody>
					</table>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="4%">序号</th>
								<th>款式大类</th>
								<th>标明成色</th>
								<th>标明规格重量（kg/件）</th>
								<th>数量（件）</th>
								<th>总重量（kg）</th>
								<th>生产厂商</th>
								<th>是否封闭运输</th>
								<th>存储地点</th>
								<th>实际数量（件）</th>
								<th>实际检测重量（kg）</th>
								<th>实际检测成色</th>
								<th>检测方法</th>
								<th>是否相符</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${detailList}" var="inventoryDetail" varStatus="status">
							<tr>
								<td>${status.count}&nbsp;</td>
								<td>${inventoryDetail.style.name}&nbsp;</td>
								<td>${inventoryDetail.pledgePurity.name}&nbsp;</td>
								<td>${inventoryDetail.specWeight}&nbsp;</td>
								<td>${inventoryDetail.amount}&nbsp;</td>
								<td>${inventoryDetail.sumWeight}&nbsp;</td>
								<td>${inventoryDetail.company}&nbsp;</td>
								<td>
									<c:if test="${inventoryDetail.closedTran == 1}">是</c:if>
									<c:if test="${inventoryDetail.closedTran == 0}">否</c:if>
								</td>
								<td>${sessionScope.warehouse.address}&nbsp;</td>
								<td>${inventoryDetail.realAmount}&nbsp;</td>
								<td>${inventoryDetail.realWeight}&nbsp;</td>
								<td>${inventoryDetail.realPledgePurity}&nbsp;</td>
								<td>${inventoryDetail.checkMethod.title}&nbsp;</td>
								<td>
									<c:if test="${inventoryDetail.equation == 1}">盘存一致</c:if>
									<c:if test="${inventoryDetail.equation == 0}">盘存不一致</c:if>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<div align="center" id="pager">
						<input id="button2" type="button" value="返回" onclick="javascript:history.back();" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px;background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;">
					</div>
				</div>
			</div>
		</form>
	</body>
</html>

