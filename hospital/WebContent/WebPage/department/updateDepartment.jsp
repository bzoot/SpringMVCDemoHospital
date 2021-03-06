<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加部门</title>
</head>
<body ng-app="app">	
	<%@ include file="../../basePage/menus.jsp"%>
	
	<div class="mainContent" ng-controller="updateDeptCtrl">
		<form name="updateDeptForm" novalidate>
			<div>
				<ul class="addForm">
					<li><span>部门名称：</span> <input ng-model="deptName" name="deptName"
						id="deptName" type="text" required />
						<span style="color:red" ng-show="updateDeptForm.deptName.$invalid">
						<span style="width: 200px" ng-show="updateDeptForm.deptName.$error.required">部门名称是必须的。</span>
						</span>
					</li>
					<li><p>部门简介：</p> <textarea ng-model="deptDesc" name="deptDesc"
						id="deptDesc" style="font-size:1.2em;" rows="10" cols="40" /></textarea></li>
					<li><span>是否可预约：</span> <input type="radio" ng-model="bookable" name="bookable" id="bookable_true" value="1" /> 
						<label for="bookable_true">是</label>
										<input type="radio" ng-model="bookable" name="bookable" id="bookable_false" value="0" /> 
						<label for="bookable_false">否</label> </li>
				</ul>
				<p style="margin-top: 400px">
					<input name="" type="button" value="确认更新" class="button button-blue" ng-click="updateDepartment(updateDeptForm.$valid)" />
					<input name="" type="button" value="返回" class="button button-black" onclick="javascript:location.href='toDepartmentList.do';" />
				</p>
			</div>
		</form>
	</div>
	
	<%@ include file="../../basePage/footer.jsp"%>
</body>
<script type="text/javascript">
	app.controller('updateDeptCtrl', function($scope, $http) {
		$scope.departments;
		
		$scope.id = "${data.id}"
		$scope.deptName = "${data.departmentName}";
		$scope.deptDesc = "${data.departmentDesc}";
		$scope.bookable = "${data.bookable}";
	    
		$scope.updateDepartment = function(valid) {
			if (!valid) {return;}
			httpService($http ,'POST', 'updateDepartment.do',{
				id: $scope.id,
				departmentName: $scope.deptName,
				departmentDesc: $scope.deptDesc,
				bookable: $scope.bookable
			}).
			then(function successCallback(response) {
				alert(response.data, function(flag) {
					if (flag == '更新成功！') {
						location.href='toDepartmentList.do';
					}
				});
			}, function errorCallback(response) {
				alert("错误"+response.status);
	    	});
		};
	});
</script>
</html>