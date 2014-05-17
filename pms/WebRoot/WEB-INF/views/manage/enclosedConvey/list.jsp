<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>封闭运输记录列表</title>

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
		<form action="${ctx }/manage/enclosedConvey/list" method="get" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						封闭运输记录列表
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
						&nbsp;&nbsp;&nbsp;根据委托方
						&nbsp;&nbsp; 查询:
						<span id="values">
							<select id="delegatorId" name="delegatorId" class="required" onchange="changeStyle();">
								<option value="" selected="selected">--请选择--</option>
								<c:forEach items="${delegatorList}" var="delegator">
									<option value = "${delegator.id}">${delegator.name}</option>
								</c:forEach>
							</select>		
						</span>
						&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
						<input type="hidden" name="page.page" id="pageNo" value="${page.number+1}"/>
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>序号</th>
								<th>单号</th>
								<th>委托方</th>
								<th>目的地</th>
								<th>起运地</th>
								<th>起运时间</th>
								<th>预计到达时间</th>
								<th>重量（g）</th>
								<th>登记时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<c:forEach items="${page.content}" var="enclosedConvey" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${enclosedConvey.code }&nbsp;
								</td>
								<td>
									${enclosedConvey.delegator.name }&nbsp;
								</td>
								<td>
									${enclosedConvey.warehouse.name }&nbsp;
								</td>
								<td>
									${enclosedConvey.startPlace }&nbsp;
								</td>
								<td>
									<fmt:formatDate value="${enclosedConvey.startDate }" pattern="yyyy-MM-dd"/>&nbsp;
								</td>
								<td>
									<fmt:formatDate value="${enclosedConvey.endDate }" pattern="yyyy-MM-dd"/>&nbsp;
								</td>
								<td>
									${enclosedConvey.weight }&nbsp;
								</td>
								<td>
									${enclosedConvey.registerDate }&nbsp;
								</td>
								<td>
									<a href="${ctx }/manage/enclosedConvey/delete/${enclosedConvey.id}">已到达</a>
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

