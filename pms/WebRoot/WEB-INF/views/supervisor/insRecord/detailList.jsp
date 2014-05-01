<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>入库单信息</title>

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
		<form action="${ctx }/supervisor/insRecord" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="center">
						入库单信息
					</h3>
					<div>
						&nbsp;
					</div>
					<div align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						入库单号：${insRecord.code}
						<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						入库单加密号：${insRecord.secretCode}
					</div>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="8%">序号</th>
								<th>款式大类</th>
								<th>标明成色</th>
								<th>生产厂家</th>
								<th>数量（件）</th>
								<th>重量（g）</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${detailList}" var="insRecordDetail" varStatus="status">
							<tr>
								<td>${status.count}&nbsp;</td>
								<td>${insRecordDetail.style.name}&nbsp;</td>
								<td>${insRecordDetail.pledgePurity.name}&nbsp;</td>
								<td>${insRecordDetail.company}&nbsp;</td>
								<td>${insRecordDetail.amount}&nbsp;</td>
								<td>
									<fmt:formatNumber value="${insRecordDetail.weight}" pattern="#,#00.00#"/>&nbsp;							
								</td>								
								<td>${insRecordDetail.desc}&nbsp;</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<div align="center" id="pager">
						<a href="${ctx }/images/${insRecord.attach}">入库单扫描文件查看</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="打印入库单加密号" class="button" onclick="javascript:window.open('${ctx }/supervisor/insRecord/${insRecord.id}/print')" />
					</div>
				</div>
			</div>
		</form>
	</body>
</html>

