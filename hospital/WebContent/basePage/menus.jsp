<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script language="JavaScript" src="./js/GlobalScript.js"></script>
<div class="container" ng-controller="leftMenuCtrl">
	<div class="box box1" onclick="javascript:location.href='toUpdateUser.do?id='+currentUserID">
		{{currentUserName}}
	</div>
	
<!-- 	<div ng-if="!isCurrentAdmin" class="box box2">
		<p ng-if="!isCurrentAdmin">明星医生</p>
		<ul ng-if="!isCurrentAdmin">
			<li style="margin-top: 16px; margin-bottom: 16px" ng-repeat="expert in experts track by expert.id">
				<a href="" ng-click="viewDoctor(expert)">{{expert.doctorName}}</a>
			</li>
		</ul>
	</div> -->
		
	<div class="box box2" onclick="javascript:location.href='toBook.do'">挂号</div>
	<div class="box box3" onclick="javascript:location.href='toDoctorList.do'">医生</div>
	<div class="box box4" onclick="javascript:location.href='toDepartmentList.do'">部门</div>
	<div ng-if="!isCurrentAdmin" class="box box5" onclick="logoff()">退出登录</div>
	<div ng-if="isCurrentAdmin" class="box box5" onclick="javascript:location.href='toUserList.do'">用户</div>
	<div ng-if="isCurrentAdmin" class="box box6" onclick="logoff()">退出登录</div>
</div>
<script type="text/javascript">
	var app = angular.module('app', []);
	app.controller('leftMenuCtrl', function($scope, $http) {
		$scope.currentUserID = currentUserID;
		$scope.currentUserName = currentUserName;
		$scope.isCurrentAdmin = isCurrentAdmin == "true" ? 1 : 0;
		
		$scope.experts;
		httpService($http ,'GET', 'getExperts.do',{}).
			then(function successCallback(response) {
	            $scope.experts = response.data.data;
	        }, function errorCallback(response) {
	            // 请求失败执行代码
	    	});
		$scope.viewDoctor = function(row) {
			/* location.href='toDoctorDetail.do?'+'id='+row.id+'&doctorName='+row.doctorName+'&doctorDesc='
					+row.doctorDesc+'&expert='+row.expert+'&department='+row.department+'&departmentName='
					+row.departmentName; */
			window.open('toDoctorDetail.do?id='+row.id);
		}
	});
	
	function logoff() {
		clearCookie("userID");
		clearCookie("userName");
		clearCookie("isAdmin");
		location.href = "toLogin.do";
	}
</script>