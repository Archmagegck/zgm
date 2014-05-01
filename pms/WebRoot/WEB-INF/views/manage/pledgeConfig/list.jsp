<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>质押物要求信息列表</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		
		<script language="javascript" type="text/javascript" src="${ctx }/js/jquery.js"></script>
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
		<form action="${ctx }/manage/pledgeConfig/list" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						质押物要求信息列表
					</h3>
					<div>
						&nbsp;
					</div>
					<div align="left">
						<c:if test="${not empty messageOK}">
							<div class="flash notice">
								&nbsp;&nbsp;${messageOK}
							</div>
						</c:if>
						<c:if test="${not empty messageErr}">
							<div class="flash error">
								&nbsp;&nbsp;${messageErr}
							</div>
						</c:if>
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
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
						<input type="hidden" name="page.page" id="pageNo" value="${page.number+1}"/>
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>
									委托方
								</th>
								<th>
									监管客户
								</th>
								<th>
									监管员
								</th>
								<th>
									监管员出库重量（g）
								</th>
								<th>
									质押物的最低重量要求（g）
								</th>
								<th>
									质押物的最低价值要求（元）
								</th>
								<th>
									警戒线上限（%）
								</th>
								<th>
									警戒线下限（%）
								</th>
								<th>
									盘存误差范围（%）
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<c:forEach items="${page.content}" var="pledgeConfig" varStatus="status">
							<tr>
								<td>
									${pledgeConfig.delegator.name }&nbsp;
								</td>
								<td>
									${pledgeConfig.supervisionCustomer.name }&nbsp;
								</td>
								<td>
									${pledgeConfig.supervisor.name }&nbsp;
								</td>
								<td>
									<fmt:formatNumber value="${pledgeConfig.outWeight }" pattern="#,#00.00#"/>&nbsp;								
								</td>
								<td>
									<fmt:formatNumber value="${pledgeConfig.minWeight }" pattern="#,#00.00#"/>&nbsp;								
								</td>
								<td>
									<fmt:formatNumber value="${pledgeConfig.minValue }" pattern="#,#00.00#"/>&nbsp;								
								</td>
								
								<td>
									${pledgeConfig.maxCordon }&nbsp;
								</td>
								<td>
									${pledgeConfig.minCordon }&nbsp;
								</td>
								<td>
									${pledgeConfig.ieRange }&nbsp;
								</td>
								<td>
									<a href="${ctx }/manage/pledgeConfig/edit/${pledgeConfig.id }">编辑</a>
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

