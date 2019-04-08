<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户详情</title>
</head>
<body ng-app="app">
	<%@ include file="../../basePage/menus.jsp"%>
	
	<div class="mainContent">
		<div class="detailItem">
			<label class="detailLabel">用户姓名：</label><span>${data.trueName}</span>
		</div>
		<div class="detailItem">
			<label class="detailLabel">手机：</label><span>${data.phone}</span>
		</div>
	</div>
	
	<%@ include file="../../basePage/footer.jsp"%>	
</body>