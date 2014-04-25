<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>解压管理</title>

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
			
		//解压
		function del(){
			if (confirm("确定要解压吗？")){
					$("#myForm").attr("action","${ctx}//manage/removePledge/delete");
					$("#myForm").submit();
			}
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
		<form action="${ctx }/manage/removePledge/list" method="post" id="myForm" name="myForm">
			<div align="center" id="content">
				<div id="box">
					<h3 align="left">
						解压管理
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
						&nbsp;&nbsp;&nbsp;委托方
						&nbsp;&nbsp;
						<select name="delegators" id="queryName" varStatus="status">
							<option selected="selected" value="">--请选择--</option>
							<c:forEach items="${delegatorList}" var="delegators">
								<option value="${delegators.id}">
								${delegators.name}
								</option>
							</c:forEach>
						</select>
						&nbsp;&nbsp;&nbsp;监管客户
						&nbsp;&nbsp;
						<select name = "supervisionCustomerId" class="required" >
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
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
						<input type="hidden" name="page.page" id="pageNo" value="${page.number+1}"/>
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>
									序号
								</th>
								<th>
									委托方
								</th>
								<th>
									监管客户
								</th>
								<th>
									仓库名称
								</th>
								<th>
									监管员
								</th>
								<th>
									存储位置
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<c:forEach items="${removePledgeList}" var="removePledge" varStatus="status">
							<tr>
								<td>
									${status.count}
								</td>
								<td>
									${removePledge.delegator.name }&nbsp;
								</td>
								<td>
									${removePledge.supervisionCustomer.name }&nbsp;
								</td>
								<td>
									${removePledge.warehouse.name }&nbsp;
								</td>
								<td>
									${removePledge.supervisor.name }&nbsp;
								</td>
								<td>
									${removePledge.warehouse.address }&nbsp;
								</td>
								<td><input type="button" value="解压" class="button" onclick="del()"/></td>
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

