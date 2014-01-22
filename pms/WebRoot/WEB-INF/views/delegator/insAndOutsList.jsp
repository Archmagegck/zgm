<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>${supervisionCustomer.name}出入库信息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/admin/theme1.css">
		
		<script language="javascript" type="text/javascript" src="${ctx }/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx }/js/date/WdatePicker.js"></script>
		
		
		<script type="text/javascript">
    	$(document).ready(function(){
			var query = "${queryName}";
			if(query!=null && query.length > 0){
				$("#queryName").val(query);
			}
		});

		//全选
		function selectAll(c){
			$("[name='idGroup']").attr("checked", $(c).is(':checked'));
		}
		
		//转向
		function gotoPage(pageNo){
			$("#pageNo").val(pageNo);
			$("#myForm").submit();
		}
			
/* 		//删除
		function del(){
			if($("[name='idGroup']:checked").length <= 0){
				alert("请选择一个您要删除的！");
			} else{
				if (confirm("确定要删除吗？")){
					$("#myForm").attr("action","${ctx}/supervisor/checkDeny/delete");
					$("#myForm").submit();
				}
			}
		} */

    </script>
	
	<style type="text/css">
		.button{
			background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;
			border: 1px solid #D9E6F0;
		}
	</style>
	
	</head>

	<body>
		<form action="${ctx }/delegator/insAndOuts/${supervisionCustomer.id}/list" method="post" id="myForm" name="myForm">
			<div align="center" id="content"">
				<div id="box">
					<h3 align="left">
						${supervisionCustomer.name}出入库信息
					</h3>
					<div>
						&nbsp;
					</div>
					<div align="left" style="vertical-align: middle;">
						&nbsp;&nbsp;&nbsp;根据出入库时间查询:
						<span id="values">
						从
						<input name="beginDate" value="${beginDate}" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						&nbsp; 
						到
						<input name="endDate" value="${endDate}" onFocus="WdatePicker({isShowClear:false,isShowWeek:true,readOnly:true,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						&nbsp;&nbsp;
						</span>
						操作类别:
						<select name="state">
							<option selected="selected" value="0">入库</option>
							<option value="1">出库</option>
						</select>
						<input type="button" value="查询" class="button" onclick="gotoPage(1)" />
						<input type="hidden" name="page.page" id="pageNo" value="${page.number+1}"/>
					</div>
					<br/>
					<table style="text-align: center; font: 12px/ 1.5 tahoma, arial, 宋体;" width="100%">
						<thead>
							<tr>
								<th width="20%">
									序号
								</th>
								<th>
									<c:if test="${state==0}">
										入库单号
									</c:if>
									<c:if test="${state==1}">
										出库单号
									</c:if>
								</th>
								<th>
									总重量
								</th>
								<th>
									<c:if test="${state==0}">
										送货人姓名
									</c:if>
									<c:if test="${state==1}">
										提货人姓名
									</c:if>
								</th>
								<th>
									<c:if test="${state==0}">
										送货人身份证号
									</c:if>
									<c:if test="${state==1}">
										提货人身份证号
									</c:if>
								</th>
								<th>
									存储地点
								</th>
								<th>
									操作监管员姓名
								</th>
								<th>
									操作类别
								</th>
								<c:if test="${state==0}">
									<th>
										是否封闭运输
									</th>	
								</c:if>
								<th>
									<c:if test="${state==0}">
										入库时间
									</c:if>
									<c:if test="${state==1}">
										出库时间
									</c:if>
								</th>
								<th>
									质物清单
								</th>
								<c:if test="${state==1}">
									<th>
										提货通知书(回执)
									</th>
								</c:if>
							</tr>
						</thead>
						<c:if test="${state==0}">
							<c:forEach items="${page.content}" var="ins" varStatus="status">
								<tr>
									<td>
										${status.count}&nbsp;
									</td>
									<td>
										${ins.code}&nbsp;
									</td>
									<td>
										${ins.sumWeight}&nbsp;
									</td>
									<td>
										${ins.sender}&nbsp;
									</td>
									<td>
										${ins.senderIdCard}&nbsp;
									</td>
									<td>
										${sessionScope.warehouse.address}&nbsp;
									</td>
									<td>
										${ins.supervisor.name}&nbsp;
									</td>
									<td>
										<c:if test="${state==0}">
											入库
										</c:if>
										<c:if test="${state==1}">
											出库
										</c:if>
									</td>
									<td>
										<c:if test="${ins.closedTran==0}">
											否
										</c:if>
										<c:if test="${ins.closedTran==1}">
											是
										</c:if>
									</td>
									<td>
										${ins.date}
									</td>
									<td>								
										<c:choose>	
											<c:when test="${not empty ins.pledgeRecord。recordFile}">
												<a href="${ctx}/images/${ins.pledgeRecord。recordFile }" target=_blank>${ins.pledgeRecord.code}</a>
											</c:when>
											<c:otherwise>
												未上传
											</c:otherwise>
										</c:choose>									 
									</td>
									<td>
										<a href="${ctx }/delegator/ins/${supervisionCustomer.id}/${ins.id}">查看</a>&nbsp;
									</td>						
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${state==1}">
								<c:forEach items="${page.content}" var="outs" varStatus="status">
								<tr>
									<td>
										${status.count}&nbsp;
									</td>
									<td>
										${outs.code}&nbsp;
									</td>
									<td>
										${outs.sumWeight}&nbsp;
									</td>
									<td>
										${outs.picker}&nbsp;
									</td>
									<td>
										${outs.pickerIdCard}&nbsp;
									</td>
									<td>
										${sessionScope.warehouse.address}&nbsp;
									</td>
									<td>
										${outs.supName}&nbsp;
									</td>
									<td>
										<c:if test="${state==0}">
											入库
										</c:if>
										<c:if test="${state==1}">
											出库
										</c:if>
									</td>
									<td>
										${outs.date}
									</td>
									<td> 									
										<c:choose>	
											<c:when test="${not empty outs.pledgeRecord.recordFile}">
											<a href="${ctx}/images/${outs.pledgeRecord.recordFile}" target=_blank>${outs.pledgeRecord.code}</a>
										</c:when>
											<c:otherwise>
												未上传
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>	 									
											<c:when test="${not empty outs.pickFeedbackUrl}">
												<a href="${ctx}/images/${outs.pickFeedbackUrl} target=_blank">回执的编号在哪儿？</a>
											</c:when>
											<c:otherwise>
												未上传
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<a href="${ctx }/delegator/outs/${supervisionCustomer.id}/${outs.id}">查看</a>&nbsp;
									</td>						
								</tr>
							</c:forEach>
						</c:if>
					</table>
					<input id="button2" type="button" value="返回" onclick="javascript:history.back();" style="cursor: pointer;font-weight: bold;margin-left: 8px;padding-right: 5px;width: 205px;background: url('${ctx}/images/admin/images/form_blue.gif') repeat-x scroll left top #FFFFFF;border: 1px solid #D9E6F0;">
					<div align="left" id="pager">
						<jsp:include page="../common/page.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>

