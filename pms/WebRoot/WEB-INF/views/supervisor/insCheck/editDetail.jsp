<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>编辑</title>
    
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
			
			$("#checkMethod").val('${insCheckDetail.checkMethod}');
			$("#checkResult").val('${insCheckDetail.checkResult}');
		});
		
		function changeStyle() {
			var selStyle = $("#style").find("option:selected").text();
			$("#styleName").val(selStyle);
		}
		
	</script>
	
  </head>
  
  <body>
    <form id="myForm" name="myForm" action="${ctx}/supervisor/insCheck/updateDetail/${index}" method="post">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="adduser">编辑</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<legend><h3>请输入相关信息</h3></legend>
    			<br/>
    				<table  cellpadding="0" cellspacing="0" width="100%"  class="list1">
					<tr>
						<td width="30%">
							款式大类:
						</td>
						<td width="70%">
							<select id="style" name="style.id" class="required" onchange="changeStyle()">
								<c:forEach items="${styleList}" var="style">
									<c:choose>
										<c:when test="${style.id == insCheckDetail.style.id }">
											<option selected="selected" value = "${style.id }">${style.name }</option>
										</c:when>
										<c:otherwise>
											<option value = "${style.id }">${style.name }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							<input type="hidden" id="styleName" name="style.name" value="${insCheckDetail.style.name}">
						</td>
					</tr>
					<tr>
						<td width="30%">
							检测方法:
						</td>
						<td width="70%">
							<select id="checkMethod" name="checkMethod">
			            		<option value="Spectrum" selected="selected">光谱法</option>
			            		<option value="Dissolve">溶金法</option>
			            	</select>
						</td>
					</tr>
					<tr>
						<td width="30%">
							检测重量
						</td>
						<td width="70%">
							<input id="checkWeight" name="checkWeight" value="${insCheckDetail.checkWeight}" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
					<tr>
						<td width="30%">
							检测结果:
						</td>
						<td width="70%">
							<select id="checkResult" name="checkResult">
			            		<option value="Ok" selected="selected">合格</option>
			            		<option value="Fail">不合格</option>
			            	</select>
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
