<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新医生</title>
</head>
<body ng-app="app">
	<%@ include file="../../basePage/menus.jsp"%>
	
	<div class="mainContent" ng-controller="updateDocCtrl">
		<form name="updateDocForm" novalidate>
			<div>
				<ul class="addForm">
					<li><span>医生姓名：</span> <input ng-model="doctorName" name="doctorName"
						id="doctorName" type="text" required />
						<span style="color:red" ng-show="updateDocForm.doctorName.$invalid">
						<span style="width: 200px" ng-show="updateDocForm.doctorName.$error.required">医生姓名是必须的。</span>
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
						<span style="color:red" ng-show="updateDocForm.selectedDeptName.$invalid">
						<span style="width: 200px" ng-show="updateDocForm.selectedDeptName.$error.required">所属部门是必须的。</span>
						</span>
					</li>
				</ul>
				<p style="margin-top:250px">
					<input name="" type="button" value="确认更新" class="button button-blue" ng-click="updateDoctor(updateDocForm.$valid)" />
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

	app.controller('updateDocCtrl', function($scope, $http) {
		$scope.departments;

		$scope.docId = "${data.id}";
		$scope.doctorName = "${data.doctorName}";
		$scope.doctorDesc = "${data.doctorDesc}";
		$scope.selectedExpert = "${data.expert}";
		$scope.bookable = "${data.bookable}";
		$scope.onDuty = [
		                 {label: '周一', value: 1, selected: false},
		                 {label: '周二', value: 2, selected: false},
		                 {label: '周三', value: 3, selected: false},
		                 {label: '周四', value: 4, selected: false},
		                 {label: '周五', value: 5, selected: false},
		                 {label: '周六', value: 6, selected: false},
		                 {label: '周日', value: 7, selected: false},
		                 ];
		var list2Show = "${data.onDuty}";
		if (list2Show != "") {
			list2Show = eval("("+list2Show+")");
		}
		for (var i = 0; i < list2Show.length; i++) {
			$scope.onDuty[list2Show[i] - 1].selected = true;
		}
		$scope.selectedDept = "${data.department}";
		$scope.selectedDeptName = "${data.departmentName}";
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
		$scope.updateDoctor = function(valid) {
			if (!valid) {return;}
			var list = [];
			for (var i = 0; i < $scope.onDuty.length; i++) {
				if ($scope.onDuty[i].selected) {
					list.push($scope.onDuty[i].value);
				}
			}
			httpService($http ,'POST', 'updateDoctor.do',{
				id: $scope.docId,
				doctorName: $scope.doctorName,
				doctorDesc: $scope.doctorDesc,
				expert: $scope.selectedExpert,
				bookable: $scope.bookable,
				onDuty: list,
				department: $scope.selectedDept,
				departmentName: $scope.selectedDeptName
			}).
			then(function successCallback(response) {
				alert(response.data, function(flag) {
					if (flag == '更新成功！') {
						location.href='toDoctorList.do';
					}
				});
			}, function errorCallback(response) {
				alert('错误'+response.status);
	    	});
		};
		$scope.nothing = function() {};
	});
</script>
</html>