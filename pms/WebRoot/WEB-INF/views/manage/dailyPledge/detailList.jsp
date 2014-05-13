<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>质物清单</title>

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
			<h3>质物清单（代动产质押专用仓单）</h3>
			<h3>（监管人签发）</h3>
			</div>
			<div style="line-height:200%" align="right">编号：<u>${pledgeRecord.code }</u>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
			<div style="line-height:200%;font-family:宋体;font-size:14px">
				致：中国工商银行<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>分行
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>支行（质权人）<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;本公司已按照相关协议于
				<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>年
				<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>月
				<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>日接收下表货物并开始履行监管责任。<br/>
				本公司了解，出质人已将下表所列货物质押给贵行，并对质物的真实性、合法性负责。
				本公司业已收到贵行与出质人共同签发的编号为
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
				的《质物种类、价格、最低要求通知书（代出质通知书）》，
				本公司同意接受贵行委托并将按照贵行的指示代为监管质物。
				本公司确认上述质物（详见下表）已存放于我公司拥有使用权的仓库/场地，上述质物确已在本公司的占有、保管、监管之下。
				本公司将严格按照编号为
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
				的《黄金产业链融资项下黄金制品质押监管协议》的规定履行占有、保管、监管责任。
				本质物清单为编号为
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
				《黄金产业链融资项下黄金制品质押监管协议》不可分割的附件。<br/>
				
				&nbsp;&nbsp;&nbsp;&nbsp;在本公司监管期间，质物的最低价值/最低数量始终不得低于人民币
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
				万元或者
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
				黄金含量为99%以上的不低于
				<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
				吨，在质物的实际价值/实际数量等于质权人要求的质物最低价值/最低数量时，
				我公司保证按照《黄金产业链融资项下黄金制品质押监管协议》的约定给与出质人（及其指定人）办理提货手续。<br/>
				
				&nbsp;&nbsp;&nbsp;&nbsp;货物明细为：<br/>
				<table class="stats">
					<tr align="center">
						<td width="4%">序号</td>
						<td width="6%">款式大类</td>
						<td>成色</td>
						<td width="6%">重量规格</td>
						<td>数量</td>
						<td width="13%">总重量=重量规格*数量</td>
						<td>生产厂家</td>
						<td width="6%">是否封闭运输</td>
						<td>存储地点</td>
						<td width="6%">光谱法抽检占比</td>
						<td width="6%">溶金法抽检占比</td>
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
						<td>&nbsp;</td>
						<td>${pledgeRecordDetail.storage}&nbsp;</td>
						<td><fmt:formatNumber value="${pledgeRecordDetail.spectrumRate}" pattern="0.0000"/>&nbsp;</td>
						<td><fmt:formatNumber value="${pledgeRecordDetail.dissolveRate}" pattern="0.0000"/>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					</c:forEach>
				</table>
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;本《质物清单》构成对质物、质押生效的确认。<br/><br/>
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

