<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>检测结果评价单</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
		
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
					<h3 align="center">
						北京中工美国际货物运输代理有限责任公司监管检测结果评价单
					</h3>
					<div>
						&nbsp;
					</div>
					<table style="text-align: left; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
							<tr>
								<td>监管客户（出质人）名称:</td>
								<td>${supervisionCustomerName}</td>
								<td>检测结果评价单编号：</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>送货人姓名：</td>
								<td>${insRecord.sender}</td>
								<td>检测日期：</td>
								<td>${insRecord.dateStr}</td>
							</tr>
					</table>
					<table style="text-align: left; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>款式</th>
								<th>数量</th>
								<th>检测方法</th>
								<th>标明成色</th>
								<th>检测成色</th>
								<th>标明规格重量</th>
								<th>检测重量</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${detailList}" var="insRecordDetail" >
							<tr>
								<td>${insRecordDetail.style.name}&nbsp;</td>
								<td>${insRecordDetail.amount}&nbsp;</td>
								<td>${insRecordDetail.checkMethod.title}&nbsp;</td>
								<td>${insRecordDetail.pledgePurity.name}&nbsp;</td>
								<td>${insRecordDetail.checkPurity}&nbsp;</td>
								<td>${insRecordDetail.specWeight}&nbsp;</td>
								<td>${insRecordDetail.checkWeight}&nbsp;</td>
							</tr>
						</c:forEach>
							<tr>
								<td>合计&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>${checkSumWeight}&nbsp;</td>
							</tr>
							<tr>
								<td colspan="7">
								备注:&nbsp;
								<br><br><br><br>
								</td>
							</tr>
						</tbody>
					</table>
						<br><br>	
					<div align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;检测结果评价：
					</div>
					<div align="right">
						监管员：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
					<div align="center" id="pager" >
						<input id="btnPrint" type="button" value="  打  印  " class="button" onclick="printRecord()" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</div>
			</div>
			<script type="text/javascript">
    		function printRecord() {
    			$("#btnPrint").hide();
    			window.print();
    		}
    	</script>
	</body>
</html>

