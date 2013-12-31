<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>修改仓库</title>
    
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
	</script>

  </head>
  
  <body>
    <form id="myForm" name="myForm" action="${ctx}/manage/warehouse/save" method="post">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="addwarehouse">修改仓库</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<legend><h3>请输入相关信息</h3></legend>
    			<br/>
    				<input type="hidden" name="id" value = "${warehouse.id }" >
    				<table  cellpadding="0" cellspacing="0" width="100%"  class="list1">
					<tr>
						<td width="20%">
							仓库名称:
						</td>
						<td width="80%">
							<input id="name" name="name" value="${warehouse.name }" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							存储地址:
						</td>
						<td width="80%">
							<input id="address" name="address" value="${warehouse.address }" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							备注:
						</td>
						<td width="80%">
							<input id="desc" name="desc" value="${warehouse.desc }" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
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
