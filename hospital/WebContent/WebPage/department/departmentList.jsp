<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门</title>
</head>
<body ng-app="app">
	<%@ include file="../../basePage/menus.jsp"%>

	<div class="mainContent" ng-controller="queryDeptCtrl">
		<button class="button button-black" style="margin-left: 5%; margin-top: 10%" ng-if="isCurrentAdmin" onclick="javascript:location.href='toAddDepartment.do'">添加部门</button>
		<table style="width: 90%; margin-left: 5%; margin-right: 5%;">
			<tr>
				<th ng-repeat="head in tableHead" style="width: {{head.width}}">{{head.label}}</th>
			</tr>
			<tr ng-repeat="row in tableContent track by $index">
				<td>{{row.departmentName}}</td>
				<td><a ng-if="isCurrentAdmin" href="" ng-click="deleteDept(row)" style="margin-left: 15px">删除</a>
					<a ng-if="isCurrentAdmin" href="" ng-click="updateDept(row)" style="margin-left: 15px">更新</a>
					<a href="" ng-click="viewDept(row)" style="margin-left: 15px">查看</a></td>
			</tr>
		</table>
		<div style="width: 90%; margin-left: 5%; margin-right: 5%">
			<span><a href="" ng-show="pageNum > 1" ng-click="pageNum = pageNum - 1; queryDepartment()"><</a></span>
			<span>第{{pageNum}}页，共{{totalPage}}页, {{total}}条</span>
			<span><a href="" ng-show="pageNum < totalPage" ng-click="pageNum = pageNum + 1; queryDepartment()">></a></span>
		</div>
	</div>
	
	<%@ include file="../../basePage/footer.jsp"%>
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
		                     { label: "部门", width: "47%" },		                     
		                     { label: "操作", width: "47%" }
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
		
		$scope.viewDept = function(row) {
			window.open('toDepartmentDetail.do?id='+row.id);
		}
		$scope.deleteDept = function(row) {
			httpService($http ,'POST', 'deleteDepartment.do', row).
			then (function successCallback(response) {
				alert(response.data);
				$scope.queryDepartment();
			}, function errorCallback(response) {
				alert(response.data);
			});
		};
		$scope.updateDept = function(row) {
			location.href='toUpdateDepartment.do?id='+row.id;
		}
	});
</script>