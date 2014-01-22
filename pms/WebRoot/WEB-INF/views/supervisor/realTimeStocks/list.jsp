<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>实时库存</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		
	
	<style type="text/css">
		.button{
			background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;
			border: 1px solid #D9E6F0;
		}
	</style>
	
	</head>

	<body>
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						实时库存
					</h3>
					<div>
						&nbsp;
					</div>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="6%">序号</th>
								<th>款式大类</th>
								<th>标明成色</th>
								<th>标明规格重量（kg/件）</th>
								<th>数量（件）</th>
								<th>总重量（kg）</th>
								<th>生产厂家</th>
								<th>是否封闭运输</th>
								<th>存储地点</th>
								<th>标记/备注</th>
							</tr>
						</thead>
						<c:forEach items="${realTimeStocksList}" var="realTimeStocks" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${realTimeStocks.style.name }&nbsp;
								</td>
								<td>
									${realTimeStocks.pledgePurity.name }&nbsp;
								</td>
								<td>
									${realTimeStocks.specWeight}&nbsp;
								</td>
								<td>
									${realTimeStocks.amount}&nbsp;
								</td>
								<td>
									${realTimeStocks.sumWeight}&nbsp;
								</td>
								<td>
									${realTimeStocks.company}&nbsp;
								</td>
								<td>
									<c:if test="${realTimeStocks.closedTran == 0}">否</c:if>
									<c:if test="${realTimeStocks.closedTran == 1}">是</c:if>
								</td>
								<td>
									<c:if test="${realTimeStocks.inStock == 1}">
										${sessionScope.warehouse.address}&nbsp;
									</c:if>
									<c:if test="${realTimeStocks.inStock == 0}">
										在途
									</c:if>
								</td>
								<td>
									${realTimeStocks.desc}&nbsp;
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
	</body>
</html>

