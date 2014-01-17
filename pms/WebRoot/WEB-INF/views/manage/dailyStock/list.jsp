<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
					<c:set var="allSumAmount" value="0"></c:set>
					<c:set var="allSumWeight" value="0"></c:set>
					<c:forEach items="${stockMap}" var="item" >
						<div align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						监管客户：${item.key.name }
						</div>
						<c:set var="sumAmount" value="0"></c:set>
						<c:set var="sumWeight" value="0"></c:set>
						<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="18%">款式大类</th>
								<th width="18%">标明成色</th>
								<th width="15%">标明规格重量</th>
								<th width="25%">存储地点 </th>
								<th width="12%">数量 </th>
								<th width="12%">总重</th>
							</tr>
						</thead>
						<c:forEach items="${item.value}" var="stock" >
							<tr>
								<td>
									${stock.style.name }&nbsp;
								</td>
								<td>
									${stock.pledgePurity.name }&nbsp;
								</td>
								<td>
									${stock.specWeight }&nbsp;
								</td>
								<td>
									${stock.warehouse.address}&nbsp;
								</td>
								<td>
									${stock.amount}&nbsp;
								</td>
								<td>
									${stock.sumWeight }&nbsp;
								</td>
							</tr>
							<c:set var="sumAmount" value="${sumAmount + stock.amount}"></c:set>
							<c:set var="sumWeight" value="${sumWeight + stock.sumWeight}"></c:set>
							<c:set var="allSumAmount" value="${allSumAmount + sumAmount}"></c:set>
							<c:set var="allSumWeight" value="${allSumWeight + sumWeight}"></c:set>
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
					<br><br>
					</c:forEach>
					<br><br>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<tr>
							<th width="18%">合计</th>
							<th width="18%">&nbsp;</th>
							<th width="15%">&nbsp;</th>
							<th width="25%">&nbsp; </th>
							<th width="12%">${allSumAmount} </th>
							<th width="12%">${allSumWeight}</th>
						</tr>
					</table>
					<br>
					<div align="center" id="pager">
						<input type="button" value="生成报表并打印" class="button" onclick="generalAndPrint();" />
					</div>
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
				window.open('${ctx }/manage/dailyStock/list/toPrint?delegatorId=${delegatorId}');
			}
		</script>
	</body>
</html>

