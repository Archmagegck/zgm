<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		
		<script language="javascript" type="text/javascript" src="${ctx }/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx }/js/date/WdatePicker.js"></script>
		
		<script type="text/javascript">
    	$(document).ready(function(){
			var query = "${queryName}";
			if(query!=null && query.length > 0){
				$("#queryName").val(query);
			}
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
		<form action="${ctx }/manage/dailyStock/list" method="get" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						库存表
					</h3>
					<div>
						&nbsp;
					</div>
					<div align="left" style="vertical-align: middle;">
					&nbsp;&nbsp;&nbsp;委托方：
						<select name = "delegatorId" class="required">
							<option selected="selected" value="">--请选择--</option>
							<c:forEach items="${delegatorList }" var = "delegator">
								<c:choose>
									<c:when test="${delegator.id == delegatorId }">
										<option selected="selected" value = "${delegator.id }">${delegator.name }</option>
									</c:when>
									<c:otherwise>
										<option value = "${delegator.id }">${delegator.name }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						&nbsp;&nbsp; 监管客户：
						<select name = "supervisionCustomerId" class="required">
							<option selected="selected" value="">--请选择--</option>
							<c:forEach items="${supervisionCustomerList }" var = "supervisionCustomer">
								<c:choose>
									<c:when test="${supervisionCustomer.id == supervisionCustomerId }">
										<option selected="selected" value = "${supervisionCustomer.id }">${supervisionCustomer.name }</option>
									</c:when>
									<c:otherwise>
										<option value = "${supervisionCustomer.id }">${supervisionCustomer.name }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						&nbsp;&nbsp;&nbsp;
						<span id="values">
						日期:${date}
						&nbsp;&nbsp;
						</span>
						&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
						<input type="hidden" name="page.page" id="pageNo" value="${page.number+1}"/>
					</div>
					<br/>
					<c:set var="allSumWeight" value="0"></c:set>
					<c:set var="allSumValue" value="0"></c:set>
					<c:forEach items="${stockMap}" var="item" >
						<div align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						监管客户：${item.key.name }
						</div>
						<c:set var="sumWeight" value="0"></c:set>
						<c:set var="sumValue" value="0"></c:set>
						<c:set var="minWeight" value="100000000"></c:set>
						<c:set var="minValue" value="100000000"></c:set>
						<c:set var="warehouseId" value="0"></c:set>
						<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="18%">款式大类</th>
								<th width="25%">存储地点 </th>
								<th width="18%">标明成色</th>
								<th width="15%">重量（g）</th>
								<th width="12%">总价值 （元）</th>
							</tr>
						</thead>
						<c:forEach items="${item.value}" var="stock" >
							<tr>
								<td>
									${stock.style.name }&nbsp;
									<c:set var="warehouseId" value="${stock.warehouse.id }"></c:set>
								</td>
								<td>
									${stock.warehouse.address}&nbsp;
								</td>
								<td>
									${stock.pledgePurity.name }&nbsp;
								</td>
								<td>
									<fmt:formatNumber value="${stock.sumWeight}" pattern="#,#00.00#"/>&nbsp;
									
								</td>
								<td>
									<fmt:formatNumber value="${stock.sumWeight * value }" pattern="#,#00.00#"/>&nbsp;
								</td>
							</tr>
							<c:set var="sumValue" value="${sumValue + stock.sumWeight * value}"></c:set>
							<c:set var="sumWeight" value="${sumWeight + stock.sumWeight}"></c:set>
							<c:set var="allSumValue" value="${allSumValue + stock.sumWeight * value}"></c:set>
							<c:set var="allSumWeight" value="${allSumWeight + stock.sumWeight}"></c:set>
							<c:if test="${stock.sumWeight < minWeight }">
								<c:set var="minWeight" value="${stock.sumWeight}"></c:set>
							</c:if>
							<c:if test="${stock.sumWeight * value < minValue }">
								<c:set var="minValue" value="${stock.sumWeight * value}"></c:set>
							</c:if>
						</c:forEach>
						<tr>
							<td>合计</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><fmt:formatNumber value="${sumWeight}" pattern="#,#00.00#"/>&nbsp;</td>
							<td><fmt:formatNumber value="${sumValue}" pattern="#,#00.00#"/>&nbsp;</td>
						</tr>
						<tr>
							<td>比对值</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><fmt:formatNumber value="${sumWeight / minWeight}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>&nbsp;</td>
							<td><fmt:formatNumber value="${sumValue / minValue}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>&nbsp;</td>
						</tr>
						<tr>
							<td colspan="5">&nbsp;</td>
						</tr>
						<tr>
							<td>在途物质</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><fmt:formatNumber value="${(weightValueMap[warehouseId])[0]}" pattern="#,#00.00#"/>&nbsp;</td>
							<td><fmt:formatNumber value="${(weightValueMap[warehouseId])[1]}" pattern="#,#00.00#"/>&nbsp;</td>
						</tr>
					</table>
					<br><br>
					</c:forEach>
					<br><br>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<tr>
							<th width="18%">合计</th>
							<th width="25%">&nbsp;</th>
							<th width="18%">&nbsp;</th>
							<th width="15%"><fmt:formatNumber value="${allSumWeight}" pattern="#,#00.00#"/>&nbsp;</th>
							<th width="12%"><fmt:formatNumber value="${allSumValue}" pattern="#,#00.00#"/> </th>
						</tr>
					</table>
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
				window.open('${ctx }/manage/dailyStock/list/toPrint?delegatorId=${delegatorId}&supervisionCustomerId=${supervisionCustomerId}');
			}
		</script>
	</body>
</html>

