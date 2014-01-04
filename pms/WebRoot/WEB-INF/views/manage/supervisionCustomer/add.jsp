<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>添加用户</title>
    
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
    <form id="myForm" name="myForm" action="${ctx}/manage/supervisionCustomer/save" method="post">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="adduser">添加监管客户</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<legend><h3>请输入相关信息</h3></legend>
    			<br/>
    				<table  cellpadding="0" cellspacing="0" width="100%"  class="list1">
					<tr>
						<td width="20%">
							监管客户编号:
						</td>
						<td width="80%">
							<input id="code" name="code" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							监管客户名称:
						</td>
						<td width="80%">
							<input id="name" name="name" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							地址:
						</td>
						<td width="80%">
							<input id="address" name="address" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							联系人:
						</td>
						<td width="80%">
							<input id="contact" name="contact" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							联系电话:
						</td>
						<td width="80%">
							<input id="phone" name="phone" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							邮箱:
						</td>
						<td width="80%">
							<input id="email" name="email" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							担保方（核心企业）:
						</td>
						<td width="80%">
							<input id="guarantor" name="guarantor" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
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
						<td width="20%">
							仓库:
						</td>
						<td width="80%">
							<select name="warehouse.id" class="required">
								<option value="" selected="selected">--请选择--</option>
								<c:forEach items="${warehouseList}" var = "warehouse">
									<option value = "${warehouse.id }">${warehouse.name }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="20%">
							监管员:
						</td>
						<td width="80%">
							<select name="supervisor.id" class="required">
								<c:forEach items="${supervisorList}" var="supervisor">
									<option value = "${supervisor.id }">${supervisor.name }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="20%">
							贷款方式:
						</td>
						<td width="80%">
							<select id="loans" name="loans">
			            		<option value="Rent" selected="selected">租赁方式</option>
			            		<option value="Finance">融资方式</option>
			            	</select>
						</td>
					</tr>
					<tr>
						<td width="20%">
							开票信息:
						</td>
						<td width="80%">
							<input id="invoices" name="invoices" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							备注:
						</td>
						<td width="80%">
							<input id="desc" name="desc" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					
					</table>
    			<br/>
    		</fieldset>
    		<br/>
    		<div style="margin-bottom: 5px;padding: 3px;" align="center">
    				<input id="button1" type="submit" value="提交" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px; background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
    				<input id="button2" type="button" value="返回" onclick="javascript:history.back();" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px;background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;">
    		</div>
    	</div>
    	</div>
    </form>
  </body>
</html>
