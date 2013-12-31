<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>每日价格记录</title>

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
			//$("#pageNo").val(pageNo);
			$("#myForm").submit();
		}
			
		//删除
		function del(index){
			if (confirm("确定要删除吗？")){
				$("#myForm").attr("action","${ctx }/manage/purityPrice/" + index + "delete");
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
		<form action="${ctx }/manage/purityPrice/list" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						每日价格记录
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
						&nbsp;&nbsp;&nbsp;选择日期
						&nbsp;&nbsp;
						<input name="date" value="${date}" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="添加今日价格" class="button" onclick="location.href='${ctx}/manage/purityPrice/add'" />
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="10%">
									序号
								</th>
								<th width="30%">
									日期
								</th>
								<th width="30%">
									标明成色
								</th>
								<th width="30%">
									价格
								</th>
							</tr>
						</thead>
						<c:forEach items="${purityPriceList}" var="purityPrice" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${purityPrice.date }&nbsp;
								</td>
								<td>
									${purityPrice.pledgePurity.name }&nbsp;
								</td>
								<td>
									${purityPrice.price }&nbsp;
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>

