<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加医生</title>
</head>
<body ng-app="app">	
	<%@ include file="../../basePage/menus.jsp"%>
	
	<div class="mainContent" ng-controller="addDocCtrl">
		<form name="addDocForm" novalidate>
			<div>
				<ul class="addForm">
					<li><span>医生姓名：</span> <input ng-model="doctorName" name="doctorName"
						id="doctorName" type="text" required />
						<span style="color:red" ng-show="addDocForm.doctorName.$invalid">
						<span style="width: 200px" ng-show="addDocForm.doctorName.$error.required">医生姓名是必须的。</span>
						</span>
					</li>
					<li><p>医生简介：</p> <textarea ng-model="doctorDesc" name="doctorDesc"
						id="doctorDesc" style="font-size:1.2em;" rows="10" cols="40" /></textarea></li>
					<li><span>是否为专家：</span> <input type="radio" ng-model="selectedExpert" name="expert" id="expert_true" value="1" /> 
						<label for="expert_true">是</label>
										<input type="radio" ng-model="selectedExpert" name="expert" id="expert_false" value="0" /> 
						<label for="expert_false">否</label> </li>
					<li><span>是否可预约：</span> <input type="radio" ng-model="bookable" name="bookable" id="bookable_true" value="1" /> 
						<label for="bookable_true">是</label>
										<input type="radio" ng-model="bookable" name="bookable" id="bookable_false" value="0" /> 
						<label for="bookable_false">否</label> </li>
					<li ng-hide="bookable == 0"><p>当班日：</p>
						<span style="display: inline-block; width: 80px" ng-repeat="duty in onDuty">
							<input type="checkbox" style="margin-right: 5px" ng-model="duty.selected" />{{duty.label}}
						</span>
					</li>
					<!-- <li><p>所属部门</p> <select id="department" name="department" style="width: 200px"
										ng-model="selectedDept" 
										ng-options="dept.departmentName for dept in departments track by dept.id">
										</select></li> -->
					<li><span>所属部门：</span> <input name="selectedDeptName" ng-model="selectedDeptName"
						id="selectedDeptName" type="text" required /><button class="button button-black" style="margin-top: 0px; margin-bottom: 0px" ng-click="departmentSelect()">选择部门</button>
						<button id="nothing" style="display: none" ng-click="nothing()"></button>
						<span style="color:red" ng-show="addDocForm.selectedDeptName.$invalid">
						<span style="width: 200px" ng-show="addDocForm.selectedDeptName.$error.required">所属部门是必须的。</span>
						</span>
					</li>
					<li><span>用户名:</span> <input name="userName" ng-model="userName"
						id="userName" type="text" required />
						<span style="color:red" ng-show="addDocForm.userName.$invalid">
						<span style="width: 200px" ng-show="addDocForm.userName.$error.required">用户名是必须的。</span>
						</span>
					</li>
					<li><span>手机:</span> <input name="phone" ng-model="phone"
						id="phone" type="text" required ng-pattern="/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/" />
						<span style="color:red" ng-show="addDocForm.phone.$invalid">
						<span ng-show="addDocForm.phone.$error.required">手机是必须的。</span>
						<span ng-show="addDocForm.phone.$error.pattern">手机格式不对。</span>
						</span>
					</li>
				</ul>
				<p style="margin-top:150px">
					<input name="" type="button" value="确认添加" class="button button-blue" ng-click="addDoctor(addDocForm.$valid)" />
					<input name="" type="button" value="返回" class="button button-black" onclick="javascript:location.href='toDoctorList.do';" />
				</p>
			</div>
		</form>
		<div id="departmentSelect" title="选择部门">
			<%@ include file="../department/departmentSelect.jsp"%>
		</div>
	</div>
	
	<%@ include file="../../basePage/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function() {    
	    $("#departmentSelect").hide();    
	});

	app.controller('addDocCtrl', function($scope, $http) {
		$scope.doctorName = "";
		$scope.doctorDesc = "";
		$scope.selectedExpert = 0;
		$scope.bookable = 1;
		$scope.onDuty = [
		                 {label: '周一', value: 1, selected: true},
		                 {label: '周二', value: 2, selected: true},
		                 {label: '周三', value: 3, selected: true},
		                 {label: '周四', value: 4, selected: true},
		                 {label: '周五', value: 5, selected: true},
		                 {label: '周六', value: 6, selected: false},
		                 {label: '周日', value: 7, selected: false},
		                 ];
		$scope.selectedDept = 0;
		$scope.selectedDeptName = "";
		$scope.userName = "";
		$scope.phone = "";
		
	    $scope.departmentSelect = function() {
	    		$("#departmentSelect").dialog({ 
		            height: 600,
		            width: 800,
		            // 模态开启
		            modal: true,
		            // 是否可拖拽
		            draggable: true,
		            // 最小宽度
		            minWidth: 600,
		            buttons: {
			                    "提交": function() {
			                    	if (!!$("input[name='deptSelected']:checked").val()) {
				                    	$scope.selectedDept = tableContent[$("input[name='deptSelected']:checked").val()].id;
				            	    	$scope.selectedDeptName = tableContent[$("input[name='deptSelected']:checked").val()].departmentName;
				                    	$('#selectedDeptName').val($scope.selectedDeptName);
				                    	$("#nothing").click();
				            	    	$(this).dialog("close");
			                    	}
			                    },
			            		"取消": function() {
			                		$(this).dialog("close");
		            			}
		            		}
		     	});
	    };
		$scope.addDoctor = function(valid) {
			if (!valid) {return;}
			var list = [];
			for (var i = 0; i < $scope.onDuty.length; i++) {
				if ($scope.onDuty[i].selected) {
					list.push($scope.onDuty[i].value);
				}
			}
			httpService($http ,'POST', 'addDoctor.do',{
				doctorName: $scope.doctorName,
				doctorDesc: $scope.doctorDesc,
				expert: $scope.selectedExpert,
				bookable: $scope.bookable,
				onDuty: list,
				department: $scope.selectedDept,
				departmentName: $scope.selectedDeptName,
				
				userName: $scope.userName,
				phone: $scope.phone
			}).
			then(function successCallback(response) {
				alert(response.data, function(flag) {
					if (flag == '添加成功！') {
						location.href='toDoctorList.do';
					}
				});
			}, function errorCallback(response) {
				alert("错误"+response.status);
	    	});
		};
		$scope.nothing = function() {};
	});
</script>
</html>