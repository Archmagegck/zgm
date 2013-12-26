<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>款式管理</title>

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
			
		//删除
		function del(){
			if($("[name='idGroup']:checked").length <= 0){
				alert("请选择一个您要删除的！");
			} else{
				if (confirm("确定要删除吗？")){
					$("#myForm").attr("action","${ctx}/manage/style/delete");
					$("#myForm").submit();
				}
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
		<form action="${ctx }/manage/style/list" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						款式管理
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
						&nbsp;&nbsp;&nbsp;根据
						&nbsp;&nbsp;
						<select name="queryName" id="queryName">
							<option value="purity" selected="selected">款式名称</option>
						</select>
						&nbsp;&nbsp; 查询:
						<span id="values"><input type="text" name="queryValue" id="queryValue" value="${queryValue}" /> </span>
						&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
						<input type="button" value="添加" class="button" onclick="location.href='${ctx}/manage/style/add'" />
						<input type="button" value="删除" class="button" onclick="del()" />
						<input type="hidden" name="page.page" id="pageNo" value="${page.number+1}"/>
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="15%">
									全选
									<input name="selAll" id="all" type="checkbox" onClick="selectAll(this)" />
								</th>
								<th width="20%">
									序号
								</th>
								<th>
									款式名称
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<c:forEach items="${page.content}" var="style" varStatus="status">
							<tr>
								<td>
									<input type="checkbox" name="idGroup" value="${style.id }" />
								</td>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${style.name }&nbsp;
								</td>
								<td>
									<a href="${ctx }/manage/style/edit/${style.id }">编辑</a>
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

