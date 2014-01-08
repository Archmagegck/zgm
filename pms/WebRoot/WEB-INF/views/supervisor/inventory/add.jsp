<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>库存盘点</title>
    
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
    <form id="myForm" name="myForm" action="${ctx}/supervisor/inventory/save" method="post" class="myform">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="adduser">库存盘点</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<legend><h3>请填写盘点内容</h3></legend>
    			<br/>
    			<c:if test="${not empty messageOK}">
					<div class="flash notice">
						&nbsp;&nbsp;${messageOK}
					</div>
				</c:if>
				<c:if test="${not empty messageErr}">
					<div class="flash error">
						&nbsp;&nbsp;${messageErr}
					</div>
				</c:if>
    				<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%" class="myTable">
						<thead>
							<tr>
								<th width="6%">序号</th>
								<th>款式大类</th>
								<th>标明成色</th>
								<th>标明规格重量（kg/件）</th>
								<th>生产厂商</th>
								<th>是否封闭运输</th>
								<th>存储地点</th>
								<th>实际数量(件)</th>
								<th>实际检测重量（kg）</th>
								<th>实际检测成色</th>
								<th>检测方法</th>
							</tr>
						</thead>
						<c:forEach items="${stockList}" var="stock" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${stock.style.name }&nbsp;
									<input type="hidden" name="inventoryDetails[${status.index}].style.Id" value="${stock.style.id }">
								</td>
								<td>
									${stock.pledgePurity.name }&nbsp;
									<input type="hidden" name="inventoryDetails[${status.index}].pledgePurity.Id" value="${stock.pledgePurity.id }">
								</td>
								<td>
									${stock.specWeight}&nbsp;
									<input type="hidden" name="inventoryDetails[${status.index}].specWeight" value="${stock.specWeight }">
									<input type="hidden" name="inventoryDetails[${status.index}].amount" value="${stock.amount }">
									<input type="hidden" name="inventoryDetails[${status.index}].sumWeight" value="${stock.sumWeight }">
								</td>
								<td>
									${stock.company}&nbsp;
									<input type="hidden" name="inventoryDetails[${status.index}].company" value="${stock.company }">
								</td>
								<td>
									<c:if test="${stock.closedTran == 0}">否</c:if>
									<c:if test="${stock.closedTran == 1}">是</c:if>
									<input type="hidden" name="inventoryDetails[${status.index}].closedTran" value="${stock.closedTran }">
								</td>
								<td>
									${sessionScope.warehouse.address}&nbsp;
								</td>
								<td>
									<input name="inventoryDetails[${status.index}].realAmount" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
									&nbsp;
								</td>
								<td>
									<input name="inventoryDetails[${status.index}].realWeight" class="{required:true,number:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
									&nbsp;
								</td>
								<td>
									<input name="inventoryDetails[${status.index}].realPledgePurity" class="{required:true}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
									&nbsp;
								</td>
								<td>
									<select id="checkMethod" name="inventoryDetails[${status.index}].checkMethod">
					            		<option value="Spectrum" selected="selected">光谱法</option>
					            		<option value="Dissolve">溶金法</option>
					            	</select>
									&nbsp;
								</td>
							</tr>
						</c:forEach>
					</table>
    			<br/>
    		</fieldset>
    		<br/>
    		<div style="margin-bottom: 5px;padding: 3px;" align="center">
    			<input id="button1" type="submit" value="添加" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px; background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
    		</div>
    	</div>
    	</div>
    </form>
  </body>
</html>
