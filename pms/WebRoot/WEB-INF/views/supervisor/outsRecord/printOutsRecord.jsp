<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>出库单</title>

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
						北京中工美国际货物运输代理有限责任公司监管出库单
					</h3>
					<div>
						&nbsp;
					</div>
					<table style="text-align: left; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
							<tr>
								<td>监管客户（出质人）名称:</td>
								<td>${supervisionCustomerName}</td>
								<td>出库单编号：</td>
								<td>${outsRecord.code}</td>
							</tr>
							<tr>
								<td>提货人姓名：</td>
								<td>${outsRecord.picker}</td>
								<td>提货人身份证号：</td>
								<td>${outsRecord.pickerIdCard}</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>出库日期：</td>
								<td>${outsRecord.dateStr}</td>
							</tr>
					</table>
					<table style="text-align: left; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>款式</th>
								<th>标明成色</th>
								<th>标明规格重量</th>
								<th>存储地点</th>
								<th>数量</th>
								<th>总重</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${detailList}" var="outsRecordDetail" >
							<tr>
								<td>${outsRecordDetail.style.name}&nbsp;</td>
								<td>${outsRecordDetail.pledgePurity.name}&nbsp;</td>
								<td>${outsRecordDetail.specWeight}&nbsp;</td>
								<td>${outsRecord.warehouse.address}&nbsp;</td>
								<td>${outsRecordDetail.amount}&nbsp;</td>
								<td>${outsRecordDetail.sumWeight}&nbsp;</td>
							</tr>
						</c:forEach>
							<tr>
								<td>合计&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>${sumAmount}&nbsp;</td>
								<td>${sumWeight}&nbsp;</td>
							</tr>
							<tr>
								<td colspan="6">
								备注:&nbsp;
								<br><br><br><br>
								</td>
							</tr>
						</tbody>
					</table>
					<br><br>
					<div align="left">
						监管员：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						提货人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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

