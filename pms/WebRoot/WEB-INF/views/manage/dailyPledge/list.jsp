<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>每日质物清单</title>

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
		<form action="${ctx }/manage/dailyPledge/list" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						每日质物清单
					</h3>
					<div>
						&nbsp;
					</div>
					<div align="left" style="vertical-align: middle;">
						&nbsp;&nbsp;&nbsp;委托方：
						<select id="selDelegator" name = "delegatorId" class="required">
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
						&nbsp;&nbsp;
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>序号</th>
								<th>委托方</th>
								<th>监管客户</th>
								<th>操作</th>
							</tr>
						</thead>
						<c:forEach items="${pledgeRecordList}" var="pledgeRecord" varStatus="status">
							<tr>
								<td>${status.count}&nbsp;</td>
								<td>${pledgeRecord.delegator.name}&nbsp;</td>
								<td>${pledgeRecord.supervisionCustomer.name}&nbsp;</td>
								<td><a href="${ctx }/manage/dailyPledge/${pledgeRecord.id}/details">打印质物清单</a>&nbsp;</td>
							</tr>
						</c:forEach>
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
				window.open('${ctx }/manage/dailyOutsRecord/list/toPrint?delegatorId=${delegatorId}&supervisionCustomerId=${supervisionCustomerId}&date=${date}');
			}
		</script>
	</body>
</html>

