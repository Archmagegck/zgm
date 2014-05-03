<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>设置质押物要求</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
	
	<script type="text/javascript" src="${ctx }/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx }/js/validate/jquery.metadata.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx }/js/validate/jquery.validate.css">
	<script type="text/javascript" src="${ctx }/js/validate/jquery.validate.js"></script>
	
	<script type="text/javascript">

		$(document).ready(function(){
			$("#myForm").validate();
		});
		
		function replaceValue() {
			$("#minValue").val($("minValue").val().replace(",", ""));
		}
		
	</script>

  </head>
  
  <body>
    <form id="myForm" name="myForm" action="${ctx}/manage/pledgeConfig/update" method="post" onsubmit="replaceValue()">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="addpledgeConfig">设置质押物要求</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<legend><h3>请输入相关信息</h3></legend>
    			<br/>
    				<input type="hidden" name="id" value = "${pledgeConfig.id }" >
    				<table cellpadding="0" cellspacing="0" width="100%"  class="list1">
    				<tr>
						<td width="20%">
							委托方:
						</td>
						<td width="80%">
							${pledgeConfig.delegator.name}
							<input type="hidden" name="delegator.id" value="${pledgeConfig.delegator.id}" />
						</td>
					</tr>
					<tr>
						<td width="20%">
							监管客户:
						</td>
						<td width="80%">
							${pledgeConfig.supervisionCustomer.name}
							<input type="hidden" name="supervisionCustomer.id" value="${pledgeConfig.supervisionCustomer.id}" />
						</td>
					</tr>
					<tr>
						<td width="20%">
							监管员:
						</td>
						<td width="80%">
							${pledgeConfig.supervisor.name}
							<input type="hidden" name="supervisor.id" value="${pledgeConfig.supervisor.id}" />
						</td>
					</tr>
					<tr>
						<td width="20%">
							监管员出库重量(g):
						</td>
						<td width="80%">
							<input id="outWeight" name="outWeight" value="${pledgeConfig.outWeight }" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							质押物的最低重量(g):
						</td>
						<td width="80%">
							<input id="minWeight" name="minWeight" value="${pledgeConfig.minWeight }" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							质押物的最低价值（元）:
						</td>
						<td width="80%">
							<input id="minValue" name="minValue" value="<fmt:formatNumber value="${pledgeConfig.minValue }" pattern="#,#00.00#"/>" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							警戒线上限（%）:
						</td>
						<td width="80%">
							<input id="maxCordon" name="maxCordon" value="${pledgeConfig.maxCordon }" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							处置线（%）:
						</td>
						<td width="80%">
							<input id="minCordon" name="minCordon" value="${pledgeConfig.minCordon }" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							盘存误差范围（%）:
						</td>
						<td width="80%">
							<input id="ieRange" name="ieRange" value="${pledgeConfig.ieRange }" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
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
