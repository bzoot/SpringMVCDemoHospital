<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src="./js/GlobalScript.js"></script>
<title>注册</title>
</head>
<body ng-app="app">
	<div ng-controller="registCtrl">
		<form style="margin-left: 45%; margin-top: 15%" name="registForm" novalidate>	
			<ul class="addForm">
				<li><p>用户名:</p> <input name="userName" ng-model="userName"
					id="userName" type="text" required />
					<span style="color:red" ng-show="registForm.userName.$invalid">
					<span ng-show="registForm.userName.$error.required">用户名是必须的。</span>
					</span>
				</li>
				<li><p>密码:</p> <input name="loginPassword" ng-model="loginPassword"
					id="loginPassword" type="password" required />
					<span style="color:red" ng-show="registForm.loginPassword.$invalid">
					<span ng-show="registForm.loginPassword.$error.required">密码是必须的。</span>
					</span>
				</li>
				<li><p>手机:</p> <input ng-model="phone"
					name="phone" id="phone" type="text" required ng-pattern="/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/"/>
					<span style="color:red" ng-show="registForm.phone.$invalid">
					<span ng-show="registForm.phone.$error.required">手机是必须的。</span>
					<span ng-show="registForm.phone.$error.pattern">手机格式不对。</span>
					</span>
				</li>
				<li><p>真实姓名:</p> <input ng-model="trueName"
					name="trueName" id="trueName" type="text" required />
					<span style="color:red" ng-show="registForm.trueName.$invalid">
					<span ng-show="registForm.trueName.$error.required">真实姓名是必须的。</span>
					</span>
				</li>
				<li><p>身份证号:</p> <input ng-model="identify"
					name="identify" id="identify" type="text" required ng-pattern="/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/" />
					<span style="color:red" ng-show="registForm.identify.$invalid">
					<span ng-show="registForm.identify.$error.required">身份证号是必须的。</span>
					<span ng-show="registForm.identify.$error.pattern">身份证格式不对。</span>
					</span>
				</li>
				<!-- <li><p>是否为审核员：</p> <input type="radio" name="isAdmin" id="true" value="true" /> 
					<label for="true">是</label>
									<input type="radio" name="isAdmin" id="false" value="false" /> 
					<label for="false">否</label> </li> -->
			</ul>
			<p style="margin-left: 5%">
				<input name="" type="button" value="注册" class="button button-blue" ng-click="register(registForm.$valid)" />
			</p>
		</form>
	</div>
</body>
<script type="text/javascript">
	var app = angular.module('app', []);
	app.controller('registCtrl', function($scope, $http) {
		$scope.userName = "";
		$scope.loginPassword = "";
		$scope.phone = "";
		$scope.trueName = "";
		$scope.identify = "";
		
		$scope.register = function(valid) {
			if (!valid) { return; }
			$.post("register.do", {
				userName: $('#userName').val(),
				password: $('#loginPassword').val(),
				admin: false, //$("input[name='isAdmin']:checked").val(),
				phone: $('#phone').val(),
				trueName: $('#trueName').val(),
				identify: $('#identify').val(),
			}, function(data, status) {
				alert(data, function(flag) {
					if (flag == '注册成功！') {
						window.close();
					}
				});
			});
		}
	});
</script>
</html>