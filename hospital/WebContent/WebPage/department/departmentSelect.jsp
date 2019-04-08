<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body ng-app="app">
	<div ng-controller="queryDeptCtrl">
		<table style="width: 90%; margin-left: 5%; margin-right: 5%">
			<tr>
				<th style="width: 6%"></th>
				<th ng-repeat="head in tableHead" style="width: {{head.width}}">{{head.label}}</th>
			</tr>
			<tr ng-repeat="row in tableContent track by $index">
				<td><input type="radio" name="deptSelected" id="deptSelected{{$index}}" value="{{$index}}" /></td>
				<td>{{row.departmentName}}</td>
			</tr>
		</table>
		<div style="width: 90%; margin-left: 5%; margin-right: 5%">
			<span><a href="" ng-show="pageNum > 1" ng-click="pageNum = pageNum - 1; queryDepartment()"><</a></span>
			<span>第{{pageNum}}页，共{{totalPage}}页, {{total}}条</span>
			<span><a href="" ng-show="pageNum < totalPage" ng-click="pageNum = pageNum + 1; queryDepartment()">></a></span>
		</div>
	</div>
</body>
<script type="text/javascript">
	var tableContent = [];
	app.controller('queryDeptCtrl', function($scope, $http) {
		$scope.isCurrentAdmin = isCurrentAdmin == "true" ? 1 : 0;
		$scope.tableContent = [];
		$scope.pageNum = 1;
		$scope.pageSize = 10;
		$scope.total = 0;
		$scope.totalPage = parseInt($scope.total % $scope.pageSize == 0 ? $scope.total / $scope.pageSize : $scope.total / $scope.pageSize + 1);
		$scope.tableHead = [
		                     { label: "部门", width: "94%" },		                     
		                     //{ label: "操作", width: "47%" }
						]
		$scope.queryDepartment = function() {
			httpService($http ,'GET', 'queryDepartmentByPage.do',{
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
		($scope.queryDepartment());
	});
</script>