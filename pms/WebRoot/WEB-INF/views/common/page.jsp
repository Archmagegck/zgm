<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

每页${page.size}条 共${page.totalElements}条记录 当前第${page.number+1}页 共${page.totalPages}页
<c:if test="${page.number > 0}">
	<a href="javascript:gotoPage(1)">首页</a>
	<a href="javascript:gotoPage(${page.number})">上一页</a>
</c:if>

<c:if test="${((page.number+1)*page.size)<page.totalElements}">
	<a href="javascript:gotoPage(${page.number + 2})">下一页</a>
	<a href="javascript:gotoPage(${page.totalPages})">尾页</a>
</c:if>