<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>出入库明细报表</title>

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
		<form action="${ctx }/manage/inOutsRecord/list" method="get" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						出入库明细报表
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
						起始日期:
						<input name="beginDate" value="${beginDate}" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						&nbsp; 
						结束日期:
						<input name="endDate" value="${endDate}" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						&nbsp;&nbsp;
						</span>
						&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
						<input type="hidden" name="page.page" id="pageNo" value="${page.number+1}"/>
					</div>
					<br/>
					<c:set var="inAllSumAmount" value="0"></c:set>
					<c:set var="inAllSumweight" value="0"></c:set>
					<c:set var="outAllSumAmount" value="0"></c:set>
					<c:set var="outAllSumweight" value="0"></c:set>
					<c:forEach items="${inoutsMap}" var="item" >
						<div align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						监管客户：${item.key.name }
						</div>
						<c:set var="inSumAmount" value="0"></c:set>
						<c:set var="outSumAmount" value="0"></c:set>
						<c:set var="inSumweight" value="0"></c:set>
						<c:set var="outSumweight" value="0"></c:set>
						<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="20%">日期</th>
								<th width="14%">操作类型</th>
								<th width="14%">款式大类</th>
								<th width="14%">标明成色</th>
								<th width="14%">标明规格重量</th>
								<th width="12%">数量 </th>
								<th width="12%">总重</th>
							</tr>
						</thead>
						<c:forEach items="${item.value}" var="inOutsRecord" >
							<tr>
								<td>
									${inOutsRecord.dateStr}&nbsp;
								</td>
								<td>
									${inOutsRecord.method }&nbsp;
								</td>
								<td>
									${inOutsRecord.style }&nbsp;
								</td>
								<td>
									${inOutsRecord.pledgePurity }&nbsp;
								</td>
								<td>
									${inOutsRecord.specWeight }&nbsp;
								</td>
								<td>
									${inOutsRecord.amount}&nbsp;
								</td>
								<td>
									${inOutsRecord.sumWeight }&nbsp;
								</td>
							</tr>
							<c:if test="${inOutsRecord.method eq '入库'}">
								<c:set var="inSumAmount" value="${inSumAmount + inOutsRecord.amount}"></c:set>
								<c:set var="inSumweight" value="${inSumweight + inOutsRecord.sumWeight}"></c:set>
								<c:set var="inAllSumAmount" value="${inAllSumAmount + inSumAmount}"></c:set>
								<c:set var="inAllSumweight" value="${inAllSumweight + inSumweight}"></c:set>
							</c:if>
							<c:if test="${inOutsRecord.method eq '出库'}">
								<c:set var="outSumAmount" value="${outSumAmount + inOutsRecord.amount}"></c:set>
								<c:set var="outSumweight" value="${outSumweight + inOutsRecord.sumWeight}"></c:set>
								<c:set var="outAllSumAmount" value="${outAllSumAmount + outSumAmount}"></c:set>
								<c:set var="outAllSumweight" value="${outAllSumweight + outSumweight}"></c:set>
							</c:if>
						</c:forEach>
						<tr>
							<td rowspan="2" width="20%">合计</td>
							<td width="14%">入库&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="12%">${inSumAmount}&nbsp;</td>
							<td width="12%">${inSumweight}&nbsp;</td>
						</tr>
						<tr>
							<td width="14%">出库&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="12%">${outSumAmount}&nbsp;</td>
							<td width="12%">${outSumweight}&nbsp;</td>
						</tr>
					</table>
					</c:forEach>
					<br>
					
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<tr>
							<td rowspan="2" width="20%">合计</td>
							<td width="14%">入库&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="12%">${inAllSumAmount}&nbsp;</td>
							<td width="12%">${inAllSumweight}&nbsp;</td>
						</tr>
						<tr>
							<td width="14%">出库&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="14%">&nbsp;</td>
							<td width="12%">${outAllSumAmount}&nbsp;</td>
							<td width="12%">${outAllSumweight}&nbsp;</td>
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
				window.open('${ctx }/manage/inOutsRecord/list/toPrint?delegatorId=${delegatorId}&beginDate=${beginDate}&endDate=${endDate}');
			}
		</script>
	</body>
</html>

