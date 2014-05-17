<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>待出库信息列表</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		
	
	<style type="text/css">
		.button{
			background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;
			border: 1px solid #D9E6F0;
		}
	</style>
	<script type="text/javascript">
    	$(document).ready(function(){
		});

		//转向
		function gotoPage(pageNo){
			$("#pageNo").val(pageNo);
			$("#myForm").submit();
		}

    </script>
	</head>

	<body>
		<form action="${ctx }/manage/waitAuditOutsRecord" method="get" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						待出库信息列表
					</h3>
					<div>
						<input type="hidden" name="page.page" id="pageNo" value="${page.number+1}"/>
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
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="8%">序号</th>
								<th>出库单号</th>
								<th>总重量（g）</th>
								<th>存储地点</th>
								<th>操作监管员姓名</th>
								<th>监管客户</th>
								<th>委托方</th>
								<th>申请时间</th>
								<th>实时重量比值（%）</th>
								<th>实时价值比值（%）</th>
								<th>状态</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<c:forEach items="${page.content}" var="outsRecord" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${outsRecord.code }&nbsp;
								</td>
								<td>
									<fmt:formatNumber value="${outsRecord.sumWeight  }" pattern="#,#00.00#"/>&nbsp;	
								</td>
								<td>
									${outsRecord.warehouse.address }&nbsp;
								</td>
								<td>
									${outsRecord.supName}&nbsp;
								</td>
								<td>
									${outsRecord.supervisionCustomer.name }&nbsp;
								</td>
								<td>
									${outsRecord.delegator.name }&nbsp;
								</td>
								<td>
									${outsRecord.dateStr }&nbsp;
								</td>
								<td>
									${outsRecord.weightRate }&nbsp;
								</td>
								<td>
									${outsRecord.priceRate }&nbsp;
								</td>
								<td>
									${outsRecord.auditState.title }&nbsp;
								</td>
								<td>
									<a href="${ctx }/manage/refuseOutsRecord/${outsRecord.id }/details">查看</a>
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

