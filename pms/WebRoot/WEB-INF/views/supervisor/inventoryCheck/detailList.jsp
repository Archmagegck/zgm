<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>盘存检测单</title>

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
    		
    	</script>
	
	<style type="text/css">
		.button{
			background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;
			border: 1px solid #D9E6F0;
		}
	</style>
	
	</head>

	<body>
		<form action="${ctx }/supervisor/inventoryCheck/saveCheckResult" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="center">
						盘存检测单
					</h3>
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
					<br/>
					<div>
						<input id="inventoryCheckId" name="inventoryCheckId" value="${inventoryCheck.id}" type="hidden" class="required" />
						&nbsp;
					</div>
					<div align="left">
						盘存检测单号:${inventoryCheck.code} &nbsp;<br/>
						盘存检测单生成时间:${inventoryCheck.date} &nbsp;
					</div>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th>托盘号</th>
								<th>款式大类</th>
								<th>检测件数</th>
								<th>检测重量(g)</th>
								<th>检测结果</th>
								
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${detailList}" var="inventoryCheckDetail" varStatus="status">
							<tr>								
								<td>
									<input type="hidden" id="inventoryCheckDetailsTrayNo[${status.index}]" name="inventoryCheckDetails[${status.index}].trayNo" class="required" value="${inventoryCheckDetail.trayNo}" >  </input> 
									
									<input type="hidden" id="inventoryCheckDetailsId[${status.index}]" name="inventoryCheckDetails[${status.index}].id" value="${inventoryCheckDetail.id}" class="required" />&nbsp;
									<label>${inventoryCheckDetail.trayNo}</label>
								</td>
								<td>
									<select id="inventoryCheckDetailsStyle[${status.index}]" name="inventoryCheckDetails[${status.index}].style.id" class="required">
										<c:forEach items="${styleList}" var="style">
											<option value = "${style.id }">${style.name }</option>
										</c:forEach>
									</select>
														
								</td>
								<td>
									<input id="inventoryCheckDetailsAmount[${status.index}]" name="inventoryCheckDetails[${status.index}].amount" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
									&nbsp;
								</td>
								<td>
									<input id="inventoryCheckDetailsWeight[${status.index}]" name="inventoryCheckDetails[${status.index}].weight" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
									&nbsp;
								</td>
								<td>
									<select id="inventoryCheckDetailsCheckResult[${status.index}]" name="inventoryCheckDetails[${status.index}].checkResult" class="required">
										
											<option value="Ok" selected="selected">合格</option>
			            					<option value="Fail">不合格</option>										
									</select>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<!-- 
					<div align="center" id="pager">
						<input id="button2" type="summit" value="保存" onclick="javascript:history.back();"  style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px;background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;">
					
					</div>
					 -->
				</div>
			</div>
			<div style="margin-bottom: 5px;padding: 3px;" align="center">
    			<input id="button1" type="submit"  value="保存" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px; background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
    		</div>
		</form>
	</body>
</html>

