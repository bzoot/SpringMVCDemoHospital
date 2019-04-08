<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新用户</title>
</head>
<body ng-app="app">
	<%@ include file="../../basePage/menus.jsp"%>
	<div class="mainContent" ng-controller="updateUserCtrl">
		<form class="addForm" name="updateUserForm" novalidate>
			<div>
				<ul>
					<li><span>手机:</span> <input ng-model="phone"
						name="phone" id="phone" type="text" value="${data.phone}" required ng-pattern="/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/" />
						<span style="color:red" ng-show="updateUserForm.phone.$invalid">
						<span ng-show="updateUserForm.phone.$error.required">手机是必须的。</span>
						<span ng-show="updateUserForm.phone.$error.pattern">手机格式不对。</span>
						</span>
					</li>
					<li><span>旧密码:</span> <input
						name="exLoginPassword" id="exLoginPassword" type="password" /></li>
					<li><span>新密码:</span> <input
						name="loginPassword" id="loginPassword" type="password" /></li>
					<li><span>确认密码:</span> <input
						name="loginPasswordConfirm" id="loginPasswordConfirm" type="password" /></li>
				</ul>
				<p style="margin-top: 600px">
					<input name="" type="button" value="更新" class="button button-blue" ng-click="update(addForm.$valid)" />
					<input name="" type="button" value="返回" class="button button-black" onclick="javascript:history.go(-1);location.reload()" />
				</p>
			</div>
		</form>
	</div>
	<%@ include file="../../basePage/footer.jsp"%>
</body>
<script type="text/javascript">
	app.controller('updateUserCtrl', function($scope, $http) {
		$scope.phone = "${data.phone}";
		
		$scope.update = function(valid) {
			if (!valid) { return; }
			if ($('#loginPassword').val() == $('#loginPasswordConfirm').val()) {
				ajaxService('POST', 'updateUser.do',
				{
					id: currentUserID,
					phone: $('#phone').val(),
					password: $('#loginPassword').val(),
					admin: isCurrentAdmin,
					
					exLoginPassword: $('#exLoginPassword').val()
				}, function(data, status) {
					alert(data, function(flag) {
						if (flag == '更新成功！') {
							history.go(-1);
							location.reload();
						}
					});
				});
			} else {
				alert("确认密码与新密码不符！");
			}
		}
	});
</script>
</html>