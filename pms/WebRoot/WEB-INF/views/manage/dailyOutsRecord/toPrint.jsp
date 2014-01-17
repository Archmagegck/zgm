<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>日常出货统计表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
<style type="text/css">
table.stats {
	border-collapse: collapse;
}

table.stats td {
	border: 1px #000 solid;
}
#loading {  
	position:fixed !important;  
	position:absolute;  
	top:0;  
	left:0;  
	height:100%;   
	width:100%;   
	z-index:999;   
	background:#000 no-repeat center;   
	opacity:0.6;   
	filter:alpha(opacity=60);  
	font-size:14px;  
	line-height:20px;
}
#loading p {  
	color:#fff;  
	position:absolute;   
	top:50%;   
	left:50%;   
	margin:50px 0 0 -50px;   
	padding:3px 10px;
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		
		
	});
</script>
</head>

<body>
	<div style="width: 80%">
		<h3>日常出货统计表</h3>
		<br />
		<table class="stats">
			<thead>
				<tr>
					<td>序号</td>
					<td>委托方</td>
					<td>监管客户</td>
					<td>日期</td>
					<td>款式大类</td>
					<td>标明成色</td>
					<td>标明规格重量（kg/件）</td>
					<td>数量（件）</td>
					<td>总重量（kg）</td>
					<td>生产厂家</td>
					<td>出货时间</td>
					<td>出货后质物总量（kg）</td>
				</tr>
			</thead>
			<c:forEach items="${dailyOutsRecordList}" var="outsRecordDetail"
				varStatus="status">
				<tr>
					<td>${status.count}&nbsp;</td>
					<td>${outsRecordDetail.delegator.name}&nbsp;</td>
					<td>${outsRecordDetail.supervisionCustomer.name}&nbsp;</td>
					<td>${outsRecordDetail.dateStr}&nbsp;</td>
					<td>${outsRecordDetail.style.name}&nbsp;</td>
					<td>${outsRecordDetail.pledgePurity.name}&nbsp;</td>
					<td>${outsRecordDetail.specWeight}&nbsp;</td>
					<td>${outsRecordDetail.amount}&nbsp;</td>
					<td>${outsRecordDetail.sumWeight}&nbsp;</td>
					<td>${outsRecordDetail.company}&nbsp;</td>
					<td>${outsRecordDetail.timeStr}&nbsp;</td>
					<td>${outsRecordDetail.remainWeight}&nbsp;</td>
				</tr>
			</c:forEach>
		</table>
		<br><br>
		<div align="right">
			统计人：${sessionScope.user.name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<br>
		<div align="right">
			日期：${date}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<br>
		<div align="center" id="pager">
			<input type="button" value="打印并邮件发送给委托方" class="button" onclick="printSend()" />
		</div>
		<script type="text/javascript">
			function printSend() {
				$("#loading").show();
				$.ajax({
					url:"${ctx }/manage/dailyOutsRecord/list/print?delegatorId=${delegatorId}&supervisionCustomerId=${supervisionCustomerId}&date=${seldate}",
					cache : false,
					error: function(){  
            			alert('发送通知失败，请再次点击重新发送!');
        			},  
			        success: function(str){  
			            if(str == 'ok') {
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
