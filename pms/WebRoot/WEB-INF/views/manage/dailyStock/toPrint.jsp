<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>库存表</title>

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
	filter: alpha(opacity =       60);
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
		<h3>库存表</h3>
		<br />
		<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;border-collapse: collapse;border: 1px #000 solid;"
				width="100%">
			<tr>
				<td>委托方：${delegator.name}</td>
				<td>日期：${date}&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<br />
		<c:forEach items="${stockMap}" var="item">
			<div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				监管客户：${item.key.name }</div>
			<c:set var="sumAmount" value="0"></c:set>
			<c:set var="sumWeight" value="0"></c:set>
			<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;border-collapse: collapse;border: 1px #000 solid;"
				width="100%">
				<thead>
					<tr>
						<th width="18%">款式大类</th>
						<th width="18%">标明成色</th>
						<th width="15%">标明规格重量</th>
						<th width="25%">存储地点</th>
						<th width="12%">数量</th>
						<th width="12%">总重</th>
					</tr>
				</thead>
				<c:forEach items="${item.value}" var="stock">
					<tr>
						<td>${stock.style.name }&nbsp;</td>
						<td>${stock.pledgePurity.name }&nbsp;</td>
						<td>${stock.specWeight }&nbsp;</td>
						<td>
						<c:if test="${stock.inStock == 1}">
							${stock.warehouse.address}&nbsp;
						</c:if>
						<c:if test="${stock.inStock == 0}">
							在途&nbsp;
						</c:if>
						</td>
						<td>${stock.amount}&nbsp;</td>
						<td>${stock.sumWeight }&nbsp;</td>
					</tr>
					<c:set var="sumAmount" value="${sumAmount + stock.amount}"></c:set>
					<c:set var="sumWeight" value="${sumWeight + stock.sumWeight}"></c:set>
					<c:set var="allSumAmount" value="${allSumAmount + stock.amount}"></c:set>
					<c:set var="allSumWeight" value="${allSumWeight + stock.sumWeight}"></c:set>
				</c:forEach>
				<tr>
					<td>合计</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>${sumAmount}&nbsp;</td>
					<td>${sumWeight}&nbsp;</td>
				</tr>
			</table>
			<br>
			<br>
		</c:forEach>
		<br>

		<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;border-collapse: collapse;border: 1px #000 solid;"
			width="100%">
			<tr>
				<th width="18%">合计</th>
				<th width="18%">&nbsp;</th>
				<th width="15%">&nbsp;</th>
				<th width="25%">&nbsp;</th>
				<th width="12%">${allSumAmount}</th>
				<th width="12%">${allSumWeight}</th>
			</tr>
		</table>

		<br> <br> <br>
		<div align="center" id="pager">
			<input type="button" value="打印并邮件发送给委托方" class="button"
				onclick="printSend()" />
		</div>
		<script type="text/javascript">
			function printSend() {
				$("#loading").show();
				$
						.ajax({
							url : "${ctx }/manage/dailyStock/list/print?delegatorId=${delegator.id}&supervisionCustomerId=${supervisionCustomerId}",
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
