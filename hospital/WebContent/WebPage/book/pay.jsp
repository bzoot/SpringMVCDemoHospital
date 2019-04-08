<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body ng-app="app">
	<div ng-controller="payCtrl">
		<p>您正在为时间为 <span style="color: red; font-size: 22px">{{bookDate}} {{time}}</span>的
			<span style="color: red; font-size: 22px">{{departmentName}}</span>的
			<span style="color: red; font-size: 22px">{{doctorName}}</span>医生的预约挂号付费。
		</p>
		<form style="margin-top: 100px">
			<div>
				<ul class="addForm">
					<li><span>信用卡号：</span> <input name="cardNum" id="cardNum" type="text" /></li>
					<li><span>持卡人姓名：</span> <input name="holder" id="holder" type="text" /></li>
					<li><span>到期时间：</span> <input style="width:60px;" name="month" id="month" type="text" /> 月
						<input style="width:85px;" name="year" id="year" type="text" /> 年 </li>
				</ul>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	app.controller('payCtrl', function($scope, $http) {
		$scope.$on('pay4book', function (event, data) {
			$scope.bookDate = data.bookDate;
			$scope.time = data.time == 1 ? "上午" : "下午";
			$scope.departmentName = data.departmentName;
			$scope.doctorName = data.doctorName.length > 0 ? data.doctorName : "任意";
		});
	});
</script>