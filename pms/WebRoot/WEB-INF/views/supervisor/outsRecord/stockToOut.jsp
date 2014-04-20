<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>货物信息表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="${ctx }/js/validate/jquery.validate.css">

	<script type="text/javascript" src="${ctx }/js/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="${ctx }/js/validate/jquery.metadata.js"></script>
	<script type="text/javascript" src="${ctx }/js/validate/jquery.validate.js"></script>
	
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgcore.min.js"></script> 
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgdialog.min.js"></script>
	
	<style type="text/css">
		* {
		    margin: 0;
		    padding: 0;
		}
		
		body {
		    font-family: Arial,Tahoma,Verdana;
		    font-size: 12px;
		}

		#box {
    		padding: 2px;
		}
		
		thead {
		    background: none repeat scroll 0 0 #F3F9FF;
		}
		
		
		#box h3 {
		    background: none repeat scroll 0 0 #F3F9FF;
		    border-bottom: 1px solid #D9E6F0;
		    color: #375B91;
		    font-size: 14px;
    		padding: 5px;
		}
		
		#box table {
    		background: none repeat scroll 0 0 transparent;
    		border-collapse: collapse;
		    margin: 5px;
		    width: 98%;
		}
		
		#box table td {
			border: 1px solid #D9E6F0;
			padding: 2px;
		} 
		
		#box table th {
			border:1px solid #D9E6F0;
		} 
		
		.button {
    		background: url("${ctx }/images/admin/images/form_blue.gif") repeat-x scroll left top #FFFFFF;
    		border: 1px solid #D9E6F0;
		}
		input {
		    padding: 2px;
		}


	
	</style>
	
	<script type="text/javascript">
		jQuery.noConflict();
		jQuery(document).ready(function(){
			jQuery("#myForm").validate();
		});
		
		var dg = new J.dialog({ 
			id:'dd2', 
			title:'上传文件',
			iconTitle:false,
			page:'${ctx}/supervisor/outsRecord/toUpload', 
			cover:true,
			bgcolor:'#000',
			drag:false, 
			resize:false
		}); 
		
		function upload() {
			dg.ShowDialog(); 
		}
		
		function refreshUpload(filePath) {
			dg.cancel(); 
			location.reload();
			//jQuery("#showPic").html("<a href='${ctx }/" + filePath + "'>查看</a>");
		}
		
		
	</script>
	
  </head>
  
  <body>
    <form id="myForm" name="myForm" action="${ctx}/supervisor/outsRecord/saveOutsRecord" method="post" class="myform" enctype="multipart/form-data">
    	<div id="content">
    		<div style="margin-bottom: 10px;padding: 5px 10px;" id="box">
    		<h3 id="adduser">货物信息表</h3>
    		<br/>
    		<fieldset style="padding: 5px 10px;" id="personal">
    			<legend><h3>请填写出库内容</h3></legend>
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
								<th>重量（g）</th>
							</tr>
						</thead>
						<c:forEach items="${stockList}" var="stock" varStatus="status">
							<tr>
								<td>
									${status.count}&nbsp;
								</td>
								<td>
									${stock.style.name }&nbsp;
								</td>
								<td>
									${stock.pledgePurity.name }&nbsp;
								</td>
								<td>
									<input id="stocks[${status.index}].sumWeight" name="outStocks[${status.index}].sumWeight" class="{number:true,max:${stock.sumWeight}}" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
									<input type="hidden" name="outStocks[${status.index}].stockId" value="${stock.id }">
									&nbsp;
								</td>
							</tr>
						</c:forEach>
					</table>
    			<br/>
    			<table  cellpadding="0" cellspacing="0" width="100%"  class="myTable">
					<tr>
						<td width="30%">出库单扫描件上传：</td>
						<td width="70%" colspan="3">
							<input type="file" name="picfile" id="picfile" value="浏览" class="required" />
						</td>
					</tr>
					<tr>
						<td width="30%">备注：</td>
						<td width="70%" colspan="3">
							<input id="desc" name="desc" style="background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;"/>
						</td>
					</tr>
				</table>
    		</fieldset>
    		<br/>
    		<div style="margin-bottom: 5px;padding: 3px;" align="center">
    			<input id="button1" type="submit" value="出库" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px; background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;"/>
    		</div>
    	</div>
    	</div>
    </form>
  </body>
</html>
