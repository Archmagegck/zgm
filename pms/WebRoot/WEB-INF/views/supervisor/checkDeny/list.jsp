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
		<form action="${ctx }/supervisor/checkDeny/list" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						检测拒绝记录列表
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
						&nbsp;&nbsp;&nbsp;根据拒绝日期
						&nbsp;&nbsp; 查询:
						<span id="values">
						从
						<input name="beginDate" value="${beginDate}" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						&nbsp; 
						到
						<input name="endDate" value="${endDate}" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						&nbsp;&nbsp;
						</span>
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
						<input type="button" value="添加" class="button" onclick="location.href='${ctx}/supervisor/checkDeny/add'" />
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
									款式大类
								</th>
								<th>
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
									检测成色
								</th>
								<th>
									检测规格重量（kg/件）
								</th>
								<th>
									检测数量（件）
								</th>
								<th>
									检测重量（kg）
								</th>
								<th>
									检测方法
								</th>
								<th>
									送货人姓名
								</th>
								<th>
									送货人身份证号
								</th>
								<th>
									监管员姓名
								</th>
								<th>
									记录时间
								</th>
								<th>
									标记/备注
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<c:forEach items="${page.content}" var="checkDeny" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${checkDeny.style.name }&nbsp;
								</td>
								<td>
									${checkDeny.pledgePurity.name }&nbsp;
								</td>
								<td>
									${checkDeny.specWeight }&nbsp;
								</td>
								<td>
									${checkDeny.amount }&nbsp;
								</td>
								<td>
									${checkDeny.sumWeight }&nbsp;
								</td>
								<td>
									${checkDeny.company }&nbsp;
								</td>
								<td>
									${checkDeny.checkPurity }&nbsp;
								</td>
								<td>
									${checkDeny.checkSpecWeight }&nbsp;
								</td>
								<td>
									${checkDeny.checkAmount }&nbsp;
								</td>
								<td>
									${checkDeny.checkWeight }&nbsp;
								</td>
								<td>
									${checkDeny.checkMethod }&nbsp;
								</td>
								<td>
									${checkDeny.sender }&nbsp;
								</td>
								<td>
									${checkDeny.senderIdCard }&nbsp;
								</td>
								<td>
									${checkDeny.supervisor.name }&nbsp;
								</td>
								<td>
									${checkDeny.date }&nbsp;
								</td>
								<td>
									${checkDeny.desc }&nbsp;
								</td>							
								<td>
									<a href="${ctx }/supervisor/checkDeny/edit/${checkDeny.id }">编辑</a>
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

