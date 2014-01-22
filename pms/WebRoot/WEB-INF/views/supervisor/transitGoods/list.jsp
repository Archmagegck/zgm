<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>检测拒绝记录列表</title>

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

		//全选
		function selectAll(c){
			$("[name='idGroup']").attr("checked", $(c).is(':checked'));
		}
		
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
		<form action="${ctx }/supervisor/transitGoods/list" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						在途物质列表
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
					<input type="hidden" name="page.page" id="pageNo" value="${page.number+1}"/>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="8%">
									序号
								</th>
								<th width="12%">
									款式大类
								</th>
								<th width="12%">
									标明成色
								</th>
								<th>
									标明规格重量（kg/件）
								</th>
								<th>
									数量（件）
								</th>
								<th>
									总重量（kg）
								</th>
								<th>
									生产厂家
								</th>
								<th>
									是否封闭运输
								</th>
								<th>
									送货人姓名
								</th>
								<th>
									送货人身份证
								</th>
								<th>
									备注
								</th>
								<th>
									状态
								</th>
							</tr>
						</thead>
						<c:forEach items="${page.content}" var="transitGoods" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${transitGoods.style.name }&nbsp;
								</td>
								<td>
									${transitGoods.pledgePurity.name }&nbsp;
								</td>
								<td>
									${transitGoods.specWeight }&nbsp;
								</td>
								<td>
									${transitGoods.amount }&nbsp;
								</td>
								<td>
									${transitGoods.sumWeight }&nbsp;
								</td>
								<td>
									${transitGoods.company }&nbsp;
								</td>
								<td>
									<c:if test="${transitGoods.closedTran == 0}">
										否
									</c:if>
									<c:if test="${transitGoods.closedTran == 1}">
										是
									</c:if>
								</td>
								<td>
									${transitGoods.sender }&nbsp;
								</td>	
								<td>
									${transitGoods.senderIdCard }&nbsp;
								</td>	
								<td>
									${transitGoods.desc }&nbsp;
								</td>							
								<td>
									<c:if test="${transitGoods.state == 0 }">
										<a href="${ctx }/supervisor/transitGoods/in/${transitGoods.id }">入库</a>
									</c:if>
									<c:if test="${transitGoods.state == 1 }">
										入库时间${transitGoods.date}
									</c:if>
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

