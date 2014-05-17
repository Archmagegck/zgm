<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>质物清单</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx }/js/validate/jquery.validate.css">
		
		<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
		<script type="text/javascript" src="${ctx }/js/validate/jquery.metadata.js"></script>
		<script type="text/javascript" src="${ctx }/js/validate/jquery.validate.js"></script>
		
		<style type="text/css">
		table.stats {
		    border-collapse:collapse;
		    }
		table.stats td {
		               border: 1px #000 solid;
		               }
		#pcontent input {
		    border-top-width: 0px;
		    border-right-width: 0px;
		    border-left-width: 0px;
		    border-bottom: #787878 1px solid;
		    background-color: #ffffff;
		    
		}
		</style>
		
		<script type="text/javascript">
		$(document).ready(function(){
			$("#myForm").validate();
		});
		</script>
	</head>

	<body>
		<div style="width: 90%">
		<form action="${ctx }/manage/iniPledgePrint/${iniPledgeRecord.id }/updateInput" method="get" id="myForm" name="myForm">
			<div align="center" id="content" style="line-height:120%">
			<h3>质物清单（代动产质押专用仓单）</h3>
			<h3>（监管人签发）</h3>
			</div>
			<div style="line-height:200%" align="right">编号：<u>${iniPledgeRecord.code }</u>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
			<div style="line-height:200%;font-family:宋体;font-size:14px" id="pcontent">
				致：
				<u>${iniPledgeRecord.delegator.name }</u>
				（质权人）<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;本公司已按照相关协议于
				<input type="text" name="iyear" value="${iniPledgeRecord.recYear }" size="5" class="{required:true,number:true}"/>年
				<input type="text" name="imonth" value="${iniPledgeRecord.recMonth }" size="5" class="{required:true,number:true}"/>月
				<input type="text" name="iday" value="${iniPledgeRecord.recDay }" size="5" class="{required:true,number:true}"/>日
				接收下表货物并开始履行监管责任。<br/>
				本公司了解，出质人已将下表所列货物质押给贵行，并对质物的真实性、合法性负责。
				本公司业已收到贵行与出质人共同签发的编号为
				<input type="text" name="preCode" value="${iniPledgeRecord.preCode }" size="20" class="required"/>
				的《质物种类、价格、最低要求通知书（代出质通知书）》，
				本公司同意接受贵行委托并将按照贵行的指示代为监管质物。
				本公司确认上述质物（详见下表）已存放于我公司拥有使用权的仓库/场地，上述质物确已在本公司的占有、保管、监管之下。
				本公司将严格按照编号为
				<input type="text" name="proCode" value="${iniPledgeRecord.proCode }" size="20" class="required"/>
				的《黄金产业链融资项下黄金制品质押监管协议》的规定履行占有、保管、监管责任。
				本质物清单为编号为
				<u>${iniPledgeRecord.code }</u>
				《黄金产业链融资项下黄金制品质押监管协议》不可分割的附件。<br/>
				
				
				&nbsp;&nbsp;&nbsp;&nbsp;在本公司监管期间，质物的最低价值、最低重量、最低成色在任意时点均不得低于以下要求：<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;质物的最低价值始终不低于人民币
				<u>${config.minValue }</u>
				万元；质物的最低重量始终不低于
				<u>${config.minWeight }</u>
				公斤；质物的最低成色始终不低于
				<u>${pledgePurityName }</u>
				。<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;在质物的实际价值或实际数量或实际成色等于或低于质权人上述最低要求时，我公司保证按照《黄金产业供应链商品融资质押监管协议》的约定给与出质人（及其指定人）办理提货手续。<br/>
				
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
					<c:forEach items="${detailList}" var="iniPledgeRecordDetail" varStatus="status">
					<tr align="center">
						<td>${status.count}&nbsp;</td>
						<td>${iniPledgeRecordDetail.style.name}&nbsp;</td>
						<td>${iniPledgeRecordDetail.pledgePurity.name}&nbsp;</td>
						<td>${iniPledgeRecordDetail.specWeight}&nbsp;</td>
						<td>${iniPledgeRecordDetail.amount}&nbsp;</td>
						<td>${iniPledgeRecordDetail.sumWeight}&nbsp;</td>
						<td>${iniPledgeRecordDetail.company}&nbsp;</td>
						<td>&nbsp;</td>
						<td>${iniPledgeRecordDetail.storage}&nbsp;</td>
						<td><fmt:formatNumber value="${iniPledgeRecordDetail.spectrumRate}" pattern="0.0000"/>&nbsp;</td>
						<td><fmt:formatNumber value="${iniPledgeRecordDetail.dissolveRate}" pattern="0.0000"/>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					</c:forEach>
				</table>
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;本《质物清单》构成对质物、质押生效的确认。<br/><br/>
			</div>
			<div align="right" style="line-height:150%;font-family:宋体;font-size:14px">
				监管人：
				<u>北京中工美国际货物运输代理有限责任公司</u>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
				（预留印鉴及指定签字人签字）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;${year}&nbsp;年&nbsp;${month}&nbsp;月&nbsp;${day}&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
			<div align="center">
				<input id="updateBtn" type="button" value="  保  存  " onclick="updateRecord()" />
				<c:if test="${iniPledgeRecord.writeIn == 1}">
				<input id="btnPrint" type="button" value="  打  印  " onclick="printRecord()" />
				</c:if>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</form>
		</div>
		
		<script type="text/javascript">
    		function printRecord() {
    			$("#updateBtn").hide();
    			$("#btnPrint").hide();
    			window.print();
    		}
    		
    		function updateRecord() {
    			$("#myForm").submit();
    		}
    	</script>
	</body>
</html>

