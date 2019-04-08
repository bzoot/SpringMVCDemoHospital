<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body ng-app="app">
	<div ng-controller="queryBook2TransCtrl">
		<table style="width: 90%; margin-left: 5%; margin-right: 5%">
			<tr>
				<th style="width: 6%"></th>
				<th ng-repeat="head in tableHead" style="width: {{head.width}}">{{head.label}}</th>
			</tr>
			<tr ng-repeat="row in tableContent track by $index">
				<td><input type="radio" name="bookSelected" id="bookSelected{{$index}}" value="{{$index}}" /></td>
				<td>{{row.doctorName}}</td>
			</tr>
		</table>
		<div style="width: 90%; margin-left: 5%; margin-right: 5%">
			<span><a href="" ng-show="pageNum > 1" ng-click="pageNum = pageNum - 1; queryBookData4Trans()"><</a></span>
			<span>第{{pageNum}}页，共{{totalPage}}页, {{total}}条</span>
			<span><a href="" ng-show="pageNum < totalPage" ng-click="pageNum = pageNum + 1; queryBookData4Trans()">></a></span>
		</div>
	</div>
</body>
<script type="text/javascript">
	var tableContent = [];
	app.controller('queryBook2TransCtrl', function($scope, $http) {
		$scope.isCurrentAdmin = isCurrentAdmin == "true" ? 1 : 0;
		$scope.tableContent = [];
		$scope.pageNum = 1;
		$scope.pageSize = 10;
		$scope.total = 0;
		$scope.totalPage = parseInt($scope.total % $scope.pageSize == 0 ? $scope.total / $scope.pageSize : $scope.total / $scope.pageSize + 1);
		$scope.tableHead = [
		                     { label: "医生", width: "94%" },		                     
		                     //{ label: "操作", width: "47%" }
						]
		$scope.queryBookData4Trans = function(rawData) {
			httpService($http ,'GET', 'queryBookData4Trans.do',{
				doctor: rawData.doctor,
				department: rawData.department,
				bookDate: rawData.bookDate,
				time: rawData.time,
				
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
		$scope.$on('book2TransSelect', function (event, data) {
			$scope.queryBookData4Trans(data);
		});
	});
</script>