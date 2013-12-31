<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<title>管理页面</title>
	
	<script src="${ctx}/js/prototype.lite.js" type="text/javascript"></script>
	<script src="${ctx}/js/moo.fx.js" type="text/javascript"></script>
	<script src="${ctx}/js/moo.fx.pack.js" type="text/javascript"></script>
	<style>
	html,body{
		height: 100%;
	}
	body {
		font:12px Arial, Helvetica, sans-serif;
		color: #000;
		background-color: #EEF2FB;
		margin: 0px;
	}
	#container {
		width: 182px;
	}
	H1 {
		font-size: 12px;
		margin: 0px;
		width: 182px;
		cursor: pointer;
		height: 30px;
		line-height: 20px;	
	}
	H1 a {
		display: block;
		width: 182px;
		color: #000;
		height: 30px;
		text-decoration: none;
		moz-outline-style: none;
		background-image: url(${ctx}/images/admin/images/menu_bgs.gif);
		background-repeat: no-repeat;
		line-height: 30px;
		text-align: center;
		margin: 0px;
		padding: 0px;
	}
	.content{
		width: 182px;
		height: 26px;
		
	}
	.MM ul {
		list-style-type: none;
		margin: 0px;
		padding: 0px;
		display: block;
	}
	.MM li {
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		line-height: 26px;
		color: #333333;
		list-style-type: none;
		display: block;
		text-decoration: none;
		height: 26px;
		width: 182px;
		padding-left: 0px;
	}
	.MM {
		width: 182px;
		margin: 0px;
		padding: 0px;
		left: 0px;
		top: 0px;
		right: 0px;
		bottom: 0px;
		clip: rect(0px,0px,0px,0px);
	}
	.MM a:link {
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		line-height: 26px;
		color: #333333;

		background-repeat: no-repeat;
		height: 26px;
		width: 182px;
		display: block;
		text-align: center;
		margin: 0px;
		padding: 0px;
		overflow: hidden;
		text-decoration: none;
	}
	.MM a:visited {
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		line-height: 26px;
		color: #333333;

		background-repeat: no-repeat;
		display: block;
		text-align: center;
		margin: 0px;
		padding: 0px;
		height: 26px;
		width: 182px;
		text-decoration: none;
	}
	.MM a:active {
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		line-height: 26px;
		color: #333333;

		background-repeat: no-repeat;
		height: 26px;
		width: 182px;
		display: block;
		text-align: center;
		margin: 0px;
		padding: 0px;
		overflow: hidden;
		text-decoration: none;
	}
	.MM a:hover {
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		line-height: 26px;
		font-weight: bold;
		color: #006600;

		background-repeat: no-repeat;
		text-align: center;
		display: block;
		margin: 0px;
		padding: 0px;
		height: 26px;
		width: 182px;
		text-decoration: none;
	}
	</style>

  </head>

<body>
<table width="100%" height="280" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEF2FB">
  <tr>
    <td width="182" valign="top">
    
		<c:if test="${(sessionScope.user != null) && (sessionScope.type == 1)}">
			<h1 class="type"><a href="javascript:void(0)">用户管理</a></h1>
	      <div class="content">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td><img src="${ctx}/images/admin/images/menu_topline.gif" width="182" height="5" /></td>
	          </tr>
	        </table>
	        <ul class="MM">
	        	<li><a href="${ctx}/manage/supervisor" target="right">监管员管理</a></li>
	          	<li><a href="${ctx}/manage/supervisionCustomer" target="right">监管客户管理</a></li>
	          	<li><a href="${ctx}/manage/delegator" target="right">委托员管理</a></li>
	        </ul>
	      </div>
	      
	      <h1 class="type"><a href="javascript:void(0)">系统设置</a></h1>
	      <div class="content">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td><img src="${ctx}/images/admin/images/menu_topline.gif" width="182" height="5" /></td>
	          </tr>
	        </table>
	        <ul class="MM">
	        	<li><a href="${ctx}/manage/warehouse" target="right">仓库管理</a></li>
	        	<li><a href="${ctx}/manage/style" target="right">款式管理</a></li>
	        	<li><a href="${ctx}/manage/pledgePurity" target="right">质押物成色管理</a></li>
	        	<li><a href="${ctx}/manage/purityPrice" target="right">质押物每日价格设置</a></li>
	        	<li><a href="${ctx}/manage/pledgeConfig" target="right">质押物要求及警戒线设置</a></li>
	        </ul>
	      </div>
	      
	      <h1 class="type"><a href="javascript:void(0)">库存管理</a></h1>
	      <div class="content">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td><img src="${ctx}/images/admin/images/menu_topline.gif" width="182" height="5" /></td>
	          </tr>
	        </table>
	        <ul class="MM">
	        	<li><a href="${ctx}/manage/supervisor" target="right">总库存</a></li>
		        <li><a href="${ctx}/manage/supervisor" target="right">日常出货统计</a></li>
	        	<li><a href="${ctx}/manage/supervisor" target="right">当日进出库查询</a></li>
	        	<li><a href="${ctx}/manage/supervisor" target="right">当日库存查询</a></li>
	        	<li><a href="${ctx}/manage/supervisor" target="right">待审核出库单</a></li>
	        	<li><a href="${ctx}/manage/supervisor" target="right">质物清单记录</a></li>
	        	<li><a href="${ctx}/manage/supervisor" target="right">盘存记录</a></li>
	        </ul>
	      </div>
		</c:if>
		
		<c:if test="${(sessionScope.user != null) && (sessionScope.type == 2)}">
		  <h1 class="type"><a href="javascript:void(0)">入库管理</a></h1>
	      <div class="content">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td><img src="${ctx}/images/admin/images/menu_topline.gif" width="182" height="5" /></td>
	          </tr>
	        </table>
	        <ul class="MM">
	        	<li><a href="${ctx}/supervisor/insRecord" target="right">入库单列表</a></li>
	          	<li><a href="${ctx}/supervisor/insRecord/showDetailList" target="right">登记货物明细</a></li>
	          	<li><a href="${ctx}/supervisor/checkDeny/list" target="right">检测拒绝记录</a></li>
	        </ul>
	      </div>
	      
	      <h1 class="type"><a href="javascript:void(0)">出库管理</a></h1>
	      <div class="content">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td><img src="${ctx}/images/admin/images/menu_topline.gif" width="182" height="5" /></td>
	          </tr>
	        </table>
	        <ul class="MM">
	        	<li><a href="${ctx}/feedPriceIndex/list" target="right">出库单列表</a></li>
	        	<li><a href="${ctx}/productPriceIndex/list" target="right">登记出库货物明细</a></li>
	        </ul>
	      </div>
	      
	      <h1 class="type"><a href="javascript:void(0)">每日营业后操作</a></h1>
	      <div class="content">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td><img src="${ctx}/images/admin/images/menu_topline.gif" width="182" height="5" /></td>
	          </tr>
	        </table>
	        <ul class="MM">
	        	<li><a href="${ctx}/province/list" target="right">库存盘点及成色检测</a></li>
		        <li><a href="${ctx}/region/list" target="right">库存盘点检测记录</a></li>
	        	<li><a href="${ctx}/user/list" target="right">最低价值核对</a></li>
	        	<li><a href="${ctx}/config/show" target="right">生成质物清单</a></li>
	        </ul>
	      </div>
	      
	      <h1 class="type"><a href="javascript:void(0)">在途管理</a></h1>
	      <div class="content">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td><img src="${ctx}/images/admin/images/menu_topline.gif" width="182" height="5" /></td>
	          </tr>
	        </table>
	        <ul class="MM">
	        	<li><a href="${ctx}/province/list" target="right">在途质物登记</a></li>
		        <li><a href="${ctx}/region/list" target="right">在途质物记录</a></li>
	        </ul>
	      </div>
	      
	      <h1 class="type"><a href="javascript:void(0)">质物管理</a></h1>
	      <div class="content">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td><img src="${ctx}/images/admin/images/menu_topline.gif" width="182" height="5" /></td>
	          </tr>
	        </table>
	        <ul class="MM">
	        	<li><a href="${ctx}/province/list" target="right">实时库存信息</a></li>
		        <li><a href="${ctx}/region/list" target="right">每日质物清单</a></li>
	        </ul>
	      </div>
		</c:if>
		
		<c:if test="${(sessionScope.user != null) && (sessionScope.type == 3)}">
			<h1 class="type"><a href="javascript:void(0)">系统管理</a></h1>
		      <div class="content">
		        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td><img src="${ctx}/images/admin/images/menu_topline.gif" width="182" height="5" /></td>
		          </tr>
		        </table>
		        <ul class="MM">
		        	<li><a href="${ctx}/entrepotcoll/list" target="right">监管客户管理</a></li>
		          	<li><a href="${ctx}/farmerscoll/list" target="right">修改密码</a></li>
		        </ul>
		      </div>
		</c:if>
      
        <script type="text/javascript">
		var contents = document.getElementsByClassName('content');
		var toggles = document.getElementsByClassName('type');
	
		var myAccordion = new fx.Accordion(
			toggles, contents, {opacity: true, duration: 400}
		);
		myAccordion.showThisHideOpen(contents[0]);
		//myAccordion.showThisHideOpen(contents[1]);
		//myAccordion.showThisHideOpen(contents[2]);
		</script>
    </td>
  </tr>
</table>

</body>
</html>
