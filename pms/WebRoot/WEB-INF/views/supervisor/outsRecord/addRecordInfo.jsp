<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>提货人信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="${ctx }/js/validate/jquery.validate.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">

	<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="${ctx }/js/validate/jquery.metadata.js"></script>
	<script type="text/javascript" src="${ctx }/js/validate/jquery.validate.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#myForm").validate();
		});
	</script>
	
  </head>
  
  <body>
    <form id="myForm" name="myForm" action="${ctx}/supervisor/outsRecord/saveOutsRecord" method="post"  enctype="multipart/form-data">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="adduser">提货人信息</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<legend><h3>请输入相关信息</h3></legend>
    			<br/>
    				<table  cellpadding="0" cellspacing="0" width="100%"  class="list1">
					<tr>
						<td width="30%">
							提货人姓名:
						</td>
						<td width="70%">
							<input id="picker" name="picker" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="30%">
							提货人身份证号:
						</td>
						<td width="70%">
							<input id="pickerIdCard" name="pickerIdCard" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="30%">
							身份证扫描件:
						</td>
						<td width="70%">
							<input id="picfile" type="file" name="picfile" style="background: url('${ctx}/static/css/admin/img/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;">
						</td>
					</tr>
					<tr>
						<td width="30%">
							提货类型:
						</td>
						<td width="70%">
							<select id="pickType" name="pickType">
			            		<option value="Part" selected="selected">接触部分质押监管</option>
			            		<option value="All">接触全部质押监管</option>
			            	</select>
						</td>
					</tr>
					<tr>
						<td width="30%">
							操作监管员员姓名:
						</td>
						<td width="70%">
							${sessionScope.user.name}
							<input id="supName" name="supName" type="hidden" value="${sessionScope.user.name}"/>
							<input id="supervisor" name="supervisor.id" type="hidden" value="${sessionScope.user.id}"/>
							<input id="warehouse.id" name="warehouse.id" type="hidden" value="${sessionScope.warehouse.id}"/>
							<input type="hidden" name="warehouse.pledgeRecordCode" value="${sessionScope.warehouse.pledgeRecordCode}"/>
							<input id="storage" name="storage" type="hidden" value="${sessionScope.warehouse.address}"/>
						</td>
					</tr>
					</table>
    			<br/>
    		</fieldset>
    		<br/>
    		<div style="margin-bottom: 5px;padding: 3px;" align="center">
    			<input id="button1" type="submit" value="下一步" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px; background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
    		
    			<input id="button2" type="button" value="返回" onclick="javascript:history.back();" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px;background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;">
    		</div>
    	</div>
    	</div>
    </form>
  </body>
</html>
