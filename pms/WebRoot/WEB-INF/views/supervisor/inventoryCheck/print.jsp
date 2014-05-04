<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
		
	</head>

	<body>
		
		<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						盘存检测单
					</h3>
					<div align="left">
						盘存检测单号:${inventoryCheck.code} &nbsp;<br/>
						盘存检测单生成时间:${inventoryCheck.date} &nbsp;
					</div>
					
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="50%" border="1">
						<thead>
							<tr>
								<th width="30%">托盘号</th>
								<th>款式大类</th>
								<th>检测件数</th>
							</tr>
						</thead>
						<c:forEach items="${inventoryCheckDetailList}" var="detail" varStatus="status">
							<tr>
								<td>
									${detail.trayNo}&nbsp;
									<input type="hidden" name="inventoryCheckDetails[${status.index}].trayNo" value="${detail.trayNo}">
								</td>
								<td>
									
								</td>
								<td>
									
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		<div align="center" id="pager" >
			<input id="btnPrint" type="button" value="  打  印  " class="button" onclick="printRecord()" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<script type="text/javascript">
    		function printRecord() {
    			$("#btnPrint").hide();
    			window.print();
    		}
    	</script>
	</body>
</html>

