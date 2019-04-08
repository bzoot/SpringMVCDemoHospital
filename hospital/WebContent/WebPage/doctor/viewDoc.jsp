<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医生详情</title>
</head>
<body ng-app="app">
	<%@ include file="../../basePage/menus.jsp"%>
	
	<div class="mainContent" ng-controller="viewDocCtrl">
		<div class="detailItem">
			<label class="detailLabel">医生姓名：</label><span>${data.doctorName}</span>
		</div>
		<div class="detailItem">
			<label class="detailLabel">科室：</label><span>${data.departmentName}</span>
		</div>
		<div class="detailItem">
			<label class="detailLabel">专家：</label><span>{{doctorEnum.get(${data.expert})}}</span>
		</div>
		<div class="detailItem">
			<label class="detailLabel">预约状态：</label><span>{{bookableEnum.get(${data.bookable})}}</span>
		</div>
		<div class="detailItem" ng-if="'${data.bookable}' == '1'">
			<label class="detailLabel">当班日：</label>
			<span ng-repeat="duty in onDuty" style="margin-right: 5px">{{dutyEnum.get(duty)}}</span>
		</div>
		<div class="detailItem">
			<label class="detailLabel">医生简介：</label><p style="font-size: 18px; text-indent: 36px; width: 360px">${data.doctorDesc}</p>
		</div>
	</div>
	
	<%@ include file="../../basePage/footer.jsp"%>	
</body>
<script language="JavaScript" src="./js/doctor/DoctorEnum.js"></script>
<script type="text/javascript">
	app.controller('viewDocCtrl', function($scope, $http) {
		$scope.doctorEnum = doctorEnum;
		$scope.bookableEnum = bookableEnum;
		$scope.dutyEnum = dutyEnum;
		
		$scope.onDuty = eval("("+"${data.onDuty}"+")");
	});
</script>