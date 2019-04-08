<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门详情</title>
</head>
<body ng-app="app">
	<%@ include file="../../basePage/menus.jsp"%>
	
	<div class="mainContent" ng-controller="viewDepartmentCtrl">
		<div class="detailItem">
			<label class="detailLabel">部门名称：</label><span>${data.departmentName}</span>
		</div>
		<div class="detailItem">
			<label class="detailLabel">预约状态：</label>{{bookableEnum.get(${data.bookable})}}<span></span>
		</div>
		<div class="detailItem">
			<label class="detailLabel">部门简介：</label><p style="font-size: 18px; text-indent: 36px; width: 360px">${data.departmentDesc}</p>
		</div>
	</div>
	
	<%@ include file="../../basePage/footer.jsp"%>	
</body>
<script language="JavaScript" src="./js/doctor/DoctorEnum.js"></script>
<script type="text/javascript">
	app.controller('viewDepartmentCtrl', function($scope, $http) {
		$scope.bookableEnum = bookableEnum;
	});
</script>