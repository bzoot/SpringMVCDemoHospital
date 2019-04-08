<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医院挂号系统</title>
</head>
<body ng-app="app">
	<%@ include file="../basePage/menus.jsp"%>
	
	<div class="mainContent" ng-controller="queryUserCtrl">
		<div>
			<!-- <ul>
				<li><p>用&nbsp;&nbsp;户&nbsp;&nbsp;名:</p> <input name="userName"
					id="userName" type="text" ng-model="userName" /></li>
				<li><p>是否为审核员：</p> <input type="radio" ng-model="isAdmin" name="isAdmin" id="true" value="true" /> 
					<label for="true">是</label>
									<input type="radio" ng-model="isAdmin" name="isAdmin" id="false" value="false" /> 
					<label for="false">否</label> </li>
			</ul>
			<p>
				<input name="" type="button" value="查&nbsp;&nbsp;询" class="button" ng-click="queryUser(userName, isAdmin)" />
			</p> -->
		</div>
		
		<div>
			<ul ng-show="show">
				<li ng-repeat="user in users">
					<p>{{user.userName}}</p>
				</li>
			</ul>
		</div>
	</div>
	
	<%@ include file="../basePage/footer.jsp"%>
</body>
<script type="text/javascript">
	app.controller('queryUserCtrl', function($scope, $http) {
		$scope.userName = currentUserName;
		$scope.isAdmin = isCurrentAdmin;
		
		$scope.users = [];
		$scope.show = false;
		$scope.queryUser = function(userName, isAdmin) {
			httpService($http, 'GET', 'queryUser.do',
			{
				userName: userName,
				admin: isAdmin
			}).then(function successCallback(response) {
				console.log(response);
	            $scope.users = response.data.data;
	            $scope.show = true;
	        }, function errorCallback(response) {
	            // 请求失败执行代码
	    	});
		};
	});
</script>
</html>