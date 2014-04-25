<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>每日盘点</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		
		<script language="javascript" type="text/javascript" src="${ctx }/js/jquery.js"></script>
		<script type="text/javascript" src="/pms/js/date/WdatePicker.js"></script>
		
		<script type="text/javascript">
    	$(document).ready(function(){
		});

		//转向
		function gotoPage(pageNo){
			$("#pageNo").val(pageNo);
			$("#myForm").submit();
		}
			
    </script>
	
	<style type="text/css">
		.button{
			background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;
			border: 1px solid #D9E6F0;
		}
	</style>
	
	</head>

	<body>
		<form action="${ctx }/manage/dailyInventoryRecord/list" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						每日盘点
					</h3>
					<div>
						&nbsp;
					</div>
					<div align="left" style="vertical-align: middle;">
						&nbsp;&nbsp;&nbsp;仓库名称：
						<select id="selWarehouse" name = "warehouseId" class="required">
							<option selected="selected" value="">--请选择--</option>
							<c:forEach items="${warehouseList }" var = "warehouse">
								<c:choose>
									<c:when test="${warehouse.id == warehouseId }">
										<option selected="selected" value = "${warehouse.id }">${warehouse.name }</option>
									</c:when>
									<c:otherwise>
										<option value = "${warehouse.id }">${warehouse.name }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						
						&nbsp;&nbsp;&nbsp;
						选择日期
						&nbsp;&nbsp;
						<input name="date" value="${date }" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
					</div>
					<div>
						<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>序号</th>
								<th>价格</th>
							</tr>
						</thead>
						<c:forEach items="${purityPrice}" var="priceObj" varStatus="status">
							<tr>
								<td>${status.count}&nbsp;</td>
								<td>${priceObj.price}&nbsp;</td>								
							</tr>
						</c:forEach>
						</table>
					</div>
					<br/>
					
					
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>序号</th>
								<th>仓库名称</th>
								<th>盘点总重量（g）</th>
								<th>盘点总价值（元）</th>
							</tr>
						</thead>
						
						<c:forEach items="${inventoryShows}" var="inventoryShowlist" varStatus="status">
							<tr>
								<td>${status.count}&nbsp;</td>
								<td>${inventoryShowlist.warehouse.name}&nbsp;</td>
								<td>${inventoryShowlist.inventoryWeight}&nbsp;</td>
								<td>${inventoryShowlist.inventoryPrice}&nbsp;</td>
								
							</tr>
						</c:forEach>
					</table>
					<br>
					
					<br>
				</div>
			</div>
		</form>
		<script type="text/javascript">
			function generalAndPrint() {
				if('${delegatorId}' == '') {
					alert('请先选择委托方查询！');
					return;
				}
				window.open('${ctx }/manage/dailyOutsRecord/list/toPrint?delegatorId=${delegatorId}&supervisionCustomerId=${supervisionCustomerId}&date=${date}');
			}
		</script>
	</body>
</html>

