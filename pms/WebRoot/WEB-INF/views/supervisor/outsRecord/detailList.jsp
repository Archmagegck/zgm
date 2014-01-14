<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>出库单信息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		
		<script language="javascript" type="text/javascript" src="${ctx }/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx }/js/date/WdatePicker.js"></script>
		
		<script type="text/javascript">
    		
    	</script>
	
	<style type="text/css">
		.button{
			background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;
			border: 1px solid #D9E6F0;
		}
	</style>
	
	</head>

	<body>
		<form action="${ctx }/supervisor/outsRecord" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="center">
						出库单信息
					</h3>
					<div>
						&nbsp;
					</div>
					<div align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						单号：${outsRecord.code}
					</div>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>监管员姓名</th>
								<th>出库时间</th>
								<th>提货人姓名</th>
								<th>提货人身份证</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${outsRecord.supName}</td>
								<td>${outsRecord.date}</td>
								<td>${outsRecord.picker}</td>
								<td>${outsRecord.pickerIdCard}</td>
							</tr>
						</tbody>
					</table>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="8%">序号</th>
								<th>款式大类</th>
								<th>标明成色</th>
								<th>标明规格重量（kg/件）</th>
								<th>数量（件）</th>
								<th>总重量（kg）</th>
								<th>生产厂家</th>
								<th>标记/备注</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${detailList}" var="outsRecordDetail" varStatus="status">
							<tr>
								<td>${status.count}&nbsp;</td>
								<td>${outsRecordDetail.style.name}&nbsp;</td>
								<td>${outsRecordDetail.pledgePurity.name}&nbsp;</td>
								<td>${outsRecordDetail.specWeight}&nbsp;</td>
								<td>${outsRecordDetail.amount}&nbsp;</td>
								<td>${outsRecordDetail.sumWeight}&nbsp;</td>
								<td>${outsRecordDetail.company}&nbsp;</td>
								<td>${outsRecordDetail.desc}&nbsp;</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<div align="center" id="pager">
						<input type="button" value="查看并打印出库单" class="button" onclick="javascript:window.open('${ctx }/supervisor/outsRecord/${outsRecord.id}/printOutsRecord')" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="查看并打印提货通知书(回执)" class="button" onclick="javascript:window.open('${ctx }/supervisor/outsRecord/${outsRecord.id}/printPickRecord')" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="查看并打印质物清单" class="button" onclick="javascript:window.open('${ctx }/supervisor/outsRecord/${outsRecord.id}/printPledgeRecord')" />
					</div>
				</div>
			</div>
		</form>
	</body>
</html>

