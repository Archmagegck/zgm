<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>${supervisionCustomer.name}库存信息</title>

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
						${supervisionCustomer.name}库存信息
					</h3>
					<div>
						&nbsp;
					</div>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="6%">序号</th>
								<th>款式大类</th>
								<th>表明成色</th>
								<th>重量（g）</th>
								<th>存储地点</th>
							</tr>
						</thead>
						<c:forEach items="${stocksList}" var="stock" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${stock.styleName}&nbsp;
								</td>
								<td>
									${stock.pledgePurityName }&nbsp;
								</td>
								<td>
									${stock.sumWeight}&nbsp;
								</td>
								<td>
									${stock.storage}&nbsp;
								</td>
							</tr>
						</c:forEach>
					</table>
					<input id="button2" type="button" value="返回" onclick="javascript:history.back();" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px;background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;">
				</div>
			</div>
	</body>
</html>

