<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>封闭运输登记</title>
    
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

	<script type="text/javascript" src="${ctx }/js/date/WdatePicker.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#myForm").validate();
		});
		
		function changeStyle() {
			var selStyle = $("#style").find("option:selected").text();
			$("#styleName").val(selStyle);
		}
		
	</script>
	
  </head>
  
  <body>
    <form id="myForm" name="myForm" action="${ctx}/manage/enclosedConvey/save" method="post">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="adduser">封闭运输登记</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<legend><h3>请输入相关信息</h3></legend>
    			<br/>
    				<table  cellpadding="0" cellspacing="0" width="100%"  class="list1">
    				<tr>
						<td width="30%">
							单号
						</td>
						<td width="70%">
							<input id="code" name="code" class="{required:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="30%">
							委托方:
						</td>
						<td width="80%">
							<select name="delegator.id" class="required">
								<option value="" selected="selected">--请选择--</option>
								<c:forEach items="${delegatorList}" var = "delegator">
									<option value = "${delegator.id }">${delegator.name }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="30%">
							目的地 
						</td>
						<td width="70%">
							<select name="warehouse.id" class="required">
								<option value="" selected="selected">--请选择--</option>
								<c:forEach items="${warehouseList}" var = "warehouse">
									<option value = "${warehouse.id }">${warehouse.name }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="30%">
							起运地
						</td>
						<td width="70%">
							<input id="startPlace" name="startPlace" class="{required:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="30%">
							起运时间
						</td>
						<td width="70%">
 							<input id="startDate" name="startDate" class="{required:true,number:true}" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
					<tr>
						<td width="30%">
							预计到达时间
						</td>
						<td width="70%">
							<input id="endDate" name="endDate" class="{required:true,number:true}" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
					<tr>
						<td width="30%">
							重量
						</td>
						<td width="70%">
							<input id="weight" name="weight" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					</table>
    			<br/>
    		</fieldset>
    		<br/>
    		<div style="margin-bottom: 5px;padding: 3px;" align="center">
    				<input id="button1" type="submit" value="保存" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px; background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
    				<input id="button2" type="button" value="取消" onclick="javascript:history.back();" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px;background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;">
    		</div>
    	</div>
    	</div>
    </form>
  </body>
</html>
