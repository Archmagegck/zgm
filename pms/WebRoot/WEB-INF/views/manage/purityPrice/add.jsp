<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>添加每日价格</title>
    
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
    <form id="myForm" name="myForm" action="${ctx}/manage/purityPrice/save" method="post">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="adduser">今日抵押物价格单</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<legend><h3>${nowDate}</h3></legend>
    			<br/>
    				<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="20%">
									序号
								</th>
								<th>
									标明成色
								</th>
								<th>
									价格
								</th>
							</tr>
						</thead>
						<c:forEach items="${pledgePurityList}" var="pledgePurity" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${pledgePurity.name }&nbsp;
								</td>
								<td>
									<input id="purityPrices[${status.index}].price" name="purityPrices[${status.index}].price" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
									&nbsp;
									<input type="hidden" name="purityPrices[${status.index}].pledgePurity.id" value="${pledgePurity.id }">
									<input type="hidden" name="purityPrices[${status.index}].date" value="${nowDate}">
								</td>
							</tr>
						</c:forEach>
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
