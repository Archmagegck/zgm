<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>提货通知书（回执）（适用解除部分质押监管）</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
		<style type="text/css">
		table.stats {
		    border-collapse:collapse;
		    }
		table.stats td {
		               border: 1px #000 solid;
		               }
		
		</style>
	</head>

	<body>
		<div style="width: 90%">
			<div align="center" id="content" style="line-height:120%">
			<h3>提货通知书（回执）（适用解除部分质押监管）</h3>
			<h3>（监管人签发）</h3>
			</div>
			<div style="line-height:200%" align="right">编号：
			<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
			<div style="line-height:200%;font-family:宋体;font-size:14px">
				致：中国工商银行<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>分行
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>支行（质权人）<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;贵行签发的
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
				号《提货通知书》已经收悉。根据编号为
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
				的《黄金产业链融资项下黄金制品质押监管协议》的约定，
				本公司已办理贵行签发的以上《提货通知书》载明质物的提货、出库手续，提货人为
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
				公司。本次提货明细如下表：<br/>
				
				<table class="stats">
					<tr align="center">
						<td width="4%">序号</td>
						<td width="6%">款式大类</td>
						<td>成色</td>
						<td width="6%">重量规格</td>
						<td>数量</td>
						<td width="13%">总重量=重量规格*数量</td>
						<td>生产厂家</td>
						<td>货物存放位置</td>
						<td>标记</td>
					</tr>
					<c:forEach items="${detailList}" var="pledgeRecordDetail" varStatus="status">
					<tr align="center">
						<td>${status.count}&nbsp;</td>
						<td>${pledgeRecordDetail.style.name}&nbsp;</td>
						<td>${pledgeRecordDetail.pledgePurity.name}&nbsp;</td>
						<td>${pledgeRecordDetail.specWeight}&nbsp;</td>
						<td>${pledgeRecordDetail.amount}&nbsp;</td>
						<td>${pledgeRecordDetail.sumWeight}&nbsp;</td>
						<td>${pledgeRecordDetail.company}&nbsp;</td>
						<td>${address}&nbsp;</td>
						<td>${pledgeRecordDetail.desc}&nbsp;</td>
					</tr>
					</c:forEach>
				</table>
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;本次提货后，质物的最低价值为
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>万元或者最低数量为
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>。
				<br/><br/>
			</div>
			<div align="right" style="line-height:150%;font-family:宋体;font-size:14px">
				监管人：
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
				公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
				（预留印鉴及指定签字人签字）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
			<div align="center">
				<input id="btnPrint" type="button" value="  打  印  " onclick="printRecord()" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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

