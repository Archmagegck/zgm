<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>出入库明细报表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
<style type="text/css">
table {
	border-collapse: collapse;
}

table td {
	border: 1px #000 solid;
}

table th {
	border: 1px #000 solid;
}

#loading {
	position: fixed !important;
	position: absolute;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	z-index: 999;
	background: #000 no-repeat center;
	opacity: 0.6;
	filter: alpha(opacity =   60);
	font-size: 14px;
	line-height: 20px;
}

#loading p {
	color: #fff;
	position: absolute;
	top: 50%;
	left: 50%;
	margin: 50px 0 0 -50px;
	padding: 3px 10px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {

	});
</script>
</head>

<body>
	<div style="width: 80%">
		<h3>出入库明细报表</h3>
		<br />
		<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;border-collapse: collapse;border: 1px #000 solid;"
				width="100%">
			<tr>
				<td>委托方：${delegator.name}</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>起始日期：${beginDate}</td>
				<td>结束日期：${endDate}</td>
			</tr>
		</table>
		<br />
		<c:forEach items="${inoutsMap}" var="item">
			<div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				监管客户：${item.key.name }</div>
			<c:set var="inSumAmount" value="0"></c:set>
			<c:set var="outSumAmount" value="0"></c:set>
			<c:set var="inSumweight" value="0"></c:set>
			<c:set var="outSumweight" value="0"></c:set>
			<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;border-collapse: collapse;border: 1px #000 solid;"
				width="100%">
				<thead>
					<tr>
						<th width="20%">日期</th>
						<th width="14%">操作类型</th>
						<th width="14%">款式大类</th>
						<th width="14%">标明成色</th>
						<th width="14%">标明规格重量</th>
						<th width="12%">数量</th>
						<th width="12%">总重</th>
					</tr>
				</thead>
				<c:forEach items="${item.value}" var="inOutsRecord">
					<tr>
						<td>${inOutsRecord.dateStr}&nbsp;</td>
						<td>${inOutsRecord.method }&nbsp;</td>
						<td>${inOutsRecord.style }&nbsp;</td>
						<td>${inOutsRecord.pledgePurity }&nbsp;</td>
						<td>${inOutsRecord.specWeight }&nbsp;</td>
						<td>${inOutsRecord.amount}&nbsp;</td>
						<td>${inOutsRecord.sumWeight }&nbsp;</td>
					</tr>
					<c:if test="${inOutsRecord.method eq '入库'}">
						<c:set var="inSumAmount"
							value="${inSumAmount + inOutsRecord.amount}"></c:set>
						<c:set var="inSumweight"
							value="${inSumweight + inOutsRecord.sumWeight}"></c:set>
						<c:set var="inAllSumAmount"
							value="${inAllSumAmount + inSumAmount}"></c:set>
						<c:set var="inAllSumweight"
							value="${inAllSumweight + inSumweight}"></c:set>
					</c:if>
					<c:if test="${inOutsRecord.method eq '出库'}">
						<c:set var="outSumAmount"
							value="${outSumAmount + inOutsRecord.amount}"></c:set>
						<c:set var="outSumweight"
							value="${outSumweight + inOutsRecord.sumWeight}"></c:set>
						<c:set var="outAllSumAmount"
							value="${outAllSumAmount + outSumAmount}"></c:set>
						<c:set var="outAllSumweight"
							value="${outAllSumweight + outSumweight}"></c:set>
					</c:if>
				</c:forEach>
				<tr>
					<td rowspan="2" width="20%">合计</td>
					<td width="14%">入库&nbsp;</td>
					<td width="14%">&nbsp;</td>
					<td width="14%">&nbsp;</td>
					<td width="14%">&nbsp;</td>
					<td width="12%">${inSumAmount}&nbsp;</td>
					<td width="12%">${inSumweight}&nbsp;</td>
				</tr>
				<tr>
					<td width="14%">出库&nbsp;</td>
					<td width="14%">&nbsp;</td>
					<td width="14%">&nbsp;</td>
					<td width="14%">&nbsp;</td>
					<td width="12%">${outSumAmount}&nbsp;</td>
					<td width="12%">${outSumweight}&nbsp;</td>
				</tr>
			</table>
			<br>
		</c:forEach>
		<br>

		<table  style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;border-collapse: collapse;border: 1px #000 solid;"
			width="100%">
			<tr>
				<td rowspan="2" width="20%">合计</td>
				<td width="14%">入库&nbsp;</td>
				<td width="14%">&nbsp;</td>
				<td width="14%">&nbsp;</td>
				<td width="14%">&nbsp;</td>
				<td width="12%">${inAllSumAmount}&nbsp;</td>
				<td width="12%">${inAllSumweight}&nbsp;</td>
			</tr>
			<tr>
				<td width="14%">出库&nbsp;</td>
				<td width="14%">&nbsp;</td>
				<td width="14%">&nbsp;</td>
				<td width="14%">&nbsp;</td>
				<td width="12%">${outAllSumAmount}&nbsp;</td>
				<td width="12%">${outAllSumweight}&nbsp;</td>
			</tr>
		</table>

		<br> <br>
		<br>
		<div align="center" id="pager">
			<input type="button" value="打印并邮件发送给委托方" class="button"
				onclick="printSend()" />
		</div>
		<script type="text/javascript">
			function printSend() {
				$("#loading").show();
				$.ajax({
							url : "${ctx }/manage/inOutsRecord/list/print?delegatorId=${delegator.id}&supervisionCustomerId=${supervisionCustomerId}&beginDate=${beginDate}&endDate=${endDate}",
							cache : false,
							error : function() {
								alert('发送通知失败，请再次点击重新发送!');
							},
							success : function(str) {
								if (str == 'ok') {
									$("#loading").hide();
									window.print();
								}
							}
						});
			}
		</script>
		<br>
		<div id="loading" style="display: none;">
			<p id="loading-one">邮件发送中,请稍后...</p>
		</div>
	</div>
</body>
</html>
