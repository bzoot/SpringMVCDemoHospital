<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>用户</title>
</head>
<body ng-app="app">
	<%@ include file="../../basePage/menus.jsp"%>

	<div class="mainContent" ng-controller="queryUserCtrl">
		<table style="width: 90%; margin-left: 5%; margin-right: 5%; margin-top: 10%">
			<tr>
				<th ng-repeat="head in tableHead" style="width: {{head.width}}">{{head.label}}</th>
			</tr>
			<tr ng-repeat="row in tableContent">
				<td>{{row.trueName}}</td>
				<td>{{row.phone}}</td>
				<td><a href="" ng-click="viewUser(row)">查看</a></td>
			</tr>
		</table>
		<div style="width: 90%; margin-left: 5%; margin-right: 5%">
			<span><a href="" ng-show="pageNum > 1" ng-click="pageNum = pageNum - 1; queryDoctor()"><</a></span>
			<span>第{{pageNum}}页，共{{totalPage}}页, {{total}}条</span>
			<span><a href="" ng-show="pageNum < totalPage" ng-click="pageNum = pageNum + 1; queryDoctor()">></a></span>
		</div>
	</div>

	<%@ include file="../../basePage/footer.jsp"%>
</body>
<script type="text/javascript">
	app.controller('queryUserCtrl', function($scope, $http) {
		$scope.currentUserID = currentUserID;
		$scope.currentUserName = currentUserName;
		$scope.isCurrentAdmin = isCurrentAdmin == "true" ? 1 : 0;
		
		$scope.pageNum = 1;
		$scope.pageSize = 10;
		$scope.total = 0;
		$scope.totalPage = parseInt($scope.total % $scope.pageSize == 0 ? $scope.total / $scope.pageSize : $scope.total / $scope.pageSize + 1);
		
		$scope.tableContent = [];
		$scope.tableHead = [
		                     { label: "用户姓名", width: "34%"},
		                     { label: "手机", width: "34%"},
		                     { label: "操作", width: "32%"},
						]
		$scope.queryUser = function() {
			httpService($http ,'GET', 'queryUserForPage.do',{				
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
		($scope.queryUser());
		
		$scope.viewUser = function(row) {
			/* location.href='toDoctorDetail.do?'+'id='+row.id+'&doctorName='+row.doctorName+'&doctorDesc='
					+row.doctorDesc+'&expert='+row.expert+'&department='+row.department+'&departmentName='
					+row.departmentName; */
			window.open('toUserDetail.do?id='+row.id);
		}
	});
</script>
</html>