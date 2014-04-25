<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>修改款式</title>
    
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
    <form id="myForm" name="myForm" action="${ctx}/manage/style/save" method="post">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="addstyle">今日价格：${price}</h3><br/>
    		<h3 id="addstyle">以下仓库低于最低价值的115%:</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="10%">
									序号
								</th>
								<th width="20%">
									委托方
								</th>
								<th width="20%">
									监管客户
								</th>
								<th width="20%">
									仓库名称
								</th>
								<th width="30%">
									存储位置
								</th>
								
							</tr>
						</thead>
						<c:forEach items="${lowerMinPriceListShowList}" var="lowerMinPriceListShow" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${lowerMinPriceListShow.delegator.name }&nbsp;
								</td>
								<td>
									${lowerMinPriceListShow.supervisionCustomer.name }&nbsp;
								</td>
								<td>
									${lowerMinPriceListShow.warehouse.name }&nbsp;
								</td>
								<td>
									${lowerMinPriceListShow.warehouse.address }&nbsp;
								</td>								
							</tr>
						</c:forEach>
					</table>
    		</fieldset>
    		<br/>
    		
    	</div>
    	</div>
    </form>
  </body>
</html>
