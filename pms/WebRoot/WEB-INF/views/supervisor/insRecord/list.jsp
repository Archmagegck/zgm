<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>入库单列表</title>

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
		function selectlist(){
			$("#myForm").submit();
		}
		
		//导出excel
		function exportExcel(){
			$("#myForm").attr("action","${ctx}/supervisor/insRecord/export");
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
		<form action="${ctx }/supervisor/insRecord/list" method="get" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						入库单列表
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
						&nbsp;&nbsp;&nbsp;根据入库时间
						&nbsp;&nbsp; 查询:
						<span id="values">
						<input name="date" value="${date}" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						&nbsp;&nbsp;
						</span>
						&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询" class="button" onclick="selectlist()" />
						&nbsp;&nbsp;&nbsp;
						<input type="button" value="导出" class="button" onclick="exportExcel()" />
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="8%">序号</th>
								<th>入库单号</th>
								<th>总重量（g）</th>
								<th>存储地点</th>
								<th>入库时间</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<c:set var="allSumWeight" value="0"></c:set>
						<c:forEach items="${insRecordList}" var="insRecord" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${insRecord.code }&nbsp;
								</td>
								<td>
									<fmt:formatNumber value="${insRecord.sumWeight }" pattern="#,#00.00#"/>&nbsp;
									<c:set var="allSumWeight" value="${allSumWeight + insRecord.sumWeight}"></c:set>
								</td>								
								<td>
									${insRecord.warehouse.address}&nbsp;
								</td>
								<td>
									${insRecord.date }&nbsp;
								</td>
								<td>
									<a href="${ctx }/supervisor/insRecord/${insRecord.id }/details">查看</a>
									&nbsp;
								</td>
							</tr>
						</c:forEach>
						<c:if test="${not empty insRecordList}">
							<tr>
								<td>
									合计&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									${allSumWeight }&nbsp;
								</td>								
								<td>
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</c:if>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>

