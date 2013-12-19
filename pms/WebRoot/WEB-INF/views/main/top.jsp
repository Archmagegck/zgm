<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>管理页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script language=JavaScript>
		function logout(){
			parent.location.href = "${ctx}/admin/exit";
		}
	</script>
		
		<link href="${ctx }/css/skin.css" rel="stylesheet" type="text/css">
  </head>
  
  <body leftmargin="0" topmargin="0">
<table width="100%" height="64" border="0" cellpadding="0" cellspacing="0" class="admin_topbg">
  <tr>
    <td width="61%" height="64"><img src="${ctx}/images/admin/images/logo1.jpg" width="262" height="64" style="border: 0px"></td>
    <td width="39%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="74%" height="38" class="admin_txt">
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	欢迎：<b>${sessionScope.user.realName}</b>  &nbsp;&nbsp;您好,感谢登陆使用！
        </td>
        <td width="22%"><a href="#" target="_self" onClick="logout();"><img src="${ctx}/images/admin/images/out.gif" alt="安全退出" width="46" height="20" border="0"></a></td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td height="19" colspan="3">&nbsp;</td>
        </tr>
    </table></td>
  </tr>
</table>
</body>

</html>

