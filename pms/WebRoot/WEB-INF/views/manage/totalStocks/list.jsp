<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>总库存</title>

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
						总库存
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
								<th>总重量（g）</th>
								<th>总价值</th>
							</tr>
						</thead>
						<c:forEach items="${stockList}" var="totalStock" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${totalStock.styleName }&nbsp;
								</td>
								<td>
									${totalStock.pledgePurityName }&nbsp;
								</td>
								<td>
									${totalStock.sumWeight}&nbsp;
								</td>
								<td>
									${totalStock.sumValue}&nbsp;
								</td>
							</tr>
						</c:forEach>
							<tr>
								<td>合计&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>${sumWeight}&nbsp;</td>
								<td>${sumValue}&nbsp;</td>
							</tr>
					</table>
				</div>
			</div>
	</body>
</html>

