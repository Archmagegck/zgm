<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
   	<title>上传文件</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		
	<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgcore.min.js"></script> 
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgdialog.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		var DG = frameElement.lhgDG;
		DG.addBtn( 'ok', '确定', function(){ commitForm(); });
		
		function commitForm () {
			if(isValidate()) {
				document.forms[0].submit();
			}
		}

		function isValidate() {
			var noticeFile = document.getElementById("picfile");
			if (noticeFile.value == "") {
				alert("请选择文件上传！");
				return false;
			} 
			return true;
		}

		
		
	</script>
	<c:if test="${message == 'ok'}">
    		<script>
				if (frameElement!=null)
				{
					alert("操作成功！");
					DG.curWin.refreshUpload('${filePath}');
					//DG.cancel();
				}
			</script>
    </c:if>
  </head>
  <body>
  		<form action="${ctx}/supervisor/outsRecord/uploadNoticeFile" id="addForm" method="post" enctype="multipart/form-data">
  			<table border="0" cellpadding="0" cellspacing="0" class="senfe1" width="100%">
  				<tr class="list_head">
					<td colspan="2" align="center">提货通知书上传&nbsp;${message}</td>
				</tr>
				<tr>
					<td>选择文件</td>
					<td>
						<input type="file" name="picfile" id="picfile" value="浏览"/>
					</td>
				</tr>
				<c:if test="${not empty error}">
					<td colspan="2">${error}</td>
				</c:if>
  			</table>
  		</form>
    </body>
</html>
