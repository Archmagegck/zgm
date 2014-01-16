<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>库存盘点及成色检测记录明细</title>

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
		<form action="${ctx }/manage/inventory/list" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						库存盘点及成色检测记录明细
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
						选择日期
						&nbsp;&nbsp;
						<input name="date" value="${date }" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
						<input type="hidden" name="page.page" id="pageNo" value="${page.number+1}"/>
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>序号</th>
								<th>委托方</th>
								<th>监管客户</th>
								<th>监管员</th>
								<th>款式大类</th>
								<th>标明成色</th>
								<th>标明规格重量（kg/件）</th>
								<th>数量（件）</th>
								<th>实际数量（件）</th>
								<th>实际成色</th>
								<th>实际检测重量（kg）</th>
								<th>实际检测方法</th>
								<th>是否相符</th>
								<th>时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<c:forEach items="${page.content}" var="inventoryDetail" varStatus="status">
							<tr>
								<td>${status.count}&nbsp;</td>
								<td>${inventoryDetail.delegator.name}&nbsp;</td>
								<td>${inventoryDetail.supervisionCustomer.name}&nbsp;</td>
								<td>${inventoryDetail.supervisionCustomer.supervisor.name}&nbsp;</td>
								<td>${inventoryDetail.style.name}&nbsp;</td>
								<td>${inventoryDetail.pledgePurity.name}&nbsp;</td>
								<td>${inventoryDetail.specWeight}&nbsp;</td>
								<td>${inventoryDetail.amount}&nbsp;</td>
								<td>${inventoryDetail.realAmount}&nbsp;</td>
								<td>${inventoryDetail.realPledgePurity}&nbsp;</td>
								<td>${inventoryDetail.realWeight}&nbsp;</td>
								<td>${inventoryDetail.checkMethod.title}&nbsp;</td>
								<td>
									<c:if test="${inventoryDetail.equation == 1}">盘存一致</c:if>
									<c:if test="${inventoryDetail.equation == 0}">盘存不一致</c:if>
								</td>
								<td>${inventoryDetail.dateStr}&nbsp;</td>
								<td>
									<a href="${ctx}/manage/inventory/${inventoryDetail.id}/audit">通过</a>
								</td>
							</tr>
						</c:forEach>
					</table>
					<div align="left" id="pager">
						<jsp:include page="../../common/page.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>

