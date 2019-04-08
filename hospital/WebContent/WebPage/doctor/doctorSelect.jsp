<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
</head>
<body ng-app="app">
	<div ng-controller="queryDocCtrl">
		<table style="width: 90%; margin-left: 5%; margin-right: 5%">
			<tr>
				<th style="width: 6%"></th>
				<th ng-repeat="head in tableHead" style="width: {{head.width}}">{{head.label}}</th>
			</tr>
			<tr ng-repeat="row in tableContent">
				<td><input type="radio" name="doctorSelect" id="doctorSelect{{$index}}" value="{{$index}}" /></td>
				<td>{{row.doctorName}}</td>
				<td>{{row.departmentName}}</td>
				<td>{{doctorEnum.get(row.expert)}}</td>
			</tr>
		</table>
		<div style="width: 90%; margin-left: 5%; margin-right: 5%">
			<span><a href="" ng-show="pageNum > 1" ng-click="pageNum = pageNum - 1; queryDoctor()"><</a></span>
			<span>第{{pageNum}}页，共{{totalPage}}页, {{total}}条</span>
			<span><a href="" ng-show="pageNum < totalPage" ng-click="pageNum = pageNum + 1; queryDoctor()">></a></span>
		</div>
	</div>
</body>
<script language="JavaScript" src="./js/doctor/DoctorEnum.js"></script>
<script type="text/javascript">
	app.controller('queryDocCtrl', function($scope, $http) {
		$scope.currentUserID = currentUserID;
		$scope.currentUserName = currentUserName;
		$scope.isCurrentAdmin = isCurrentAdmin == "true" ? 1 : 0;
		
		$scope.pageNum = 1;
		$scope.pageSize = 10;
		$scope.total = 0;
		$scope.totalPage = parseInt($scope.total % $scope.pageSize == 0 ? $scope.total / $scope.pageSize : $scope.total / $scope.pageSize + 1);
		
		$scope.tableContent = [];
		$scope.tableHead = [
		                     { label: "医生姓名", width: "32%"},
		                     { label: "部门", width: "32%"},
		                     { label: "专家", width: "30%"}
						]
		$scope.doctorEnum = doctorEnum;
		$scope.queryDoctor = function(department) {
			httpService($http ,'GET', 'queryDoctorForPage.do',{
				department: department,
				pageNum: $scope.pageNum,
				pageSize: $scope.pageSize
			}).
			then(function successCallback(response) {
	            $scope.tableContent = response.data.data;
	            $scope.total = response.data.total;
	    		$scope.totalPage = parseInt($scope.total % $scope.pageSize == 0 ? $scope.total / $scope.pageSize : $scope.total / $scope.pageSize + 1);
	    		tableContent = $scope.tableContent;
			}, function errorCallback(response) {
	            // 请求失败执行代码
	            alert(response.status + ", " + response.statusText);
	    	});
		};
		$scope.$on('queryDoctorForSelect', function (event, data) {
			$scope.queryDoctor(data);
		});
	});
</script>
</html>