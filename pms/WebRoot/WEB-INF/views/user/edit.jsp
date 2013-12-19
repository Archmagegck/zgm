<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>修改用户</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
	
	<script type="text/javascript" src="${ctx }/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx }/js/kindeditor/kindeditor.js"></script>
	<script type="text/javascript" src="${ctx }/js/validate/jquery.metadata.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx }/js/validate/jquery.validate.css">
	<script type="text/javascript" src="${ctx }/js/validate/jquery.validate.js"></script>
	<script type="text/javascript" src="${ctx }/js/caiot.js"></script>
	
	<script type="text/javascript">

		$(document).ready(function(){
			$("#myForm").validate();
			$("#userType").val("${user.userType}");
		});
		
		function changeRegionByPro(id){
			var url = "${ctx}/response/province/" + id + "/regions";
			iniSelect(url, "regionList");
		}
	</script>

  </head>
  
  <body>
    <form id="myForm" name="myForm" action="${ctx}/user/save" method="post">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="adduser">修改用户</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<legend><h3>请输入相关信息</h3></legend>
    			<br/>
    				<input type="hidden" name="id" value = "${user.id }" >
    				<table  cellpadding="0" cellspacing="0" width="100%"  class="list1">
					<tr>
						<td width="20%">
							用户姓名:
						</td>
						<td width="80%">
							<input id="realName" name="realName" value="${user.realName }" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							登录名:
						</td>
						<td width="80%">
							<input id="loginName" name="loginName" value="${user.loginName }" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							密码:
						</td>
						<td width="80%">
							<input id="loginPwd" name="loginPwd" value="${user.loginPwd }" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							用户类别:
						</td>
						<td width="80%">
							<select id="userType" name="userType">
			            		<option value="Farmers">养殖户 </option>
			            		<option value="Entrepot">集散地</option>
			            		<option value="Consumer">消费者</option>
			            		<option value="ProAduit">省级审核用户</option>
			            		<option value="Scientist">科学家</option>
			            	</select>
						</td>
					</tr>
					<tr>
						<td width="20%">
							手机号码:
						</td>
						<td width="80%">
							<input id="phone" name="phone" value="${user.phone }" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							邮箱:
						</td>
						<td width="80%">
							<input id="email" name="email" value="${user.email }" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					<tr>
						<td width="20%">
							地址:
						</td>
						<td width="80%">
							<input id="address" name="address" value="${user.address }" class="required" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
						</td>
					</tr>
					
					
					<tr>
						<td width="20%">
							省份:
						</td>
						<td width="80%">
						<select name = "province.id" class="required" onchange="changeRegionByPro(this.value)">
								<c:forEach items="${provinceList }" var = "province">
									<c:choose>
										<c:when test="${province.id == user.province.id }">
											<option selected="selected" value = "${province.id }">${province.name }</option>
										</c:when>
										<c:otherwise>
											<option value = "${province.id }">${province.name }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td width="20%">
							地区:
						</td>
						<td width="80%">
						<select name="region.id" id="regionList" class="required">
								<c:forEach items="${regionList }" var="region">
									<c:choose>
										<c:when test="${region.id == user.region.id }">
											<option selected="selected" value = "${region.id }">${region.name }</option>
										</c:when>
										<c:otherwise>
											<option value = "${region.id }">${region.name }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</select>
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
