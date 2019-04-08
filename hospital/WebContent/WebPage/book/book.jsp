<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预约</title>
</head>
<body ng-app="app">
	<%@ include file="../../basePage/menus.jsp"%>

	<div class="mainContent" ng-controller="bookCtrl">
		<button class="button button button-black" ng-if="isCurrentAdmin" ng-click="generateOneDayRootData()">生成2周后数据（定时任务）</button>
		<button class="button button button-black" ng-if="isCurrentAdmin" ng-click="generateTwoWeekData()">补齐数据（谨慎使用）</button>
		
		<ul ng-if="doctorID=='null'" class="addForm">
			<li><span>部门：</span> <input name="department" id="department" type="text" readonly="readonly" />
				<button class="button button-black" style="margin-top: 0px; margin-bottom: 0px" ng-click="departmentSelect()">选择部门</button>
			</li>
			<li><span>医生：</span> <input name="doctor" id="doctor" type="text" readonly="readonly" />
				<button class="button button-black" style="margin-top: 0px; margin-bottom: 0px" ng-click="doctorSelect()">选择医生</button>
				<button class="button button-black" style="margin-top: 0px; margin-bottom: 0px" ng-click="cancalDoctorSelect()">取消选择</button>
				<button id="nothing" style="display: none" ng-click="nothing()"></button>
			</li>
			<li ng-if="isCurrentAdmin">
				<span>日期：</span> <input name="bookDate" id="bookDate" ng-model="$parent.$parent.bookDate" type="text" />
			</li>
			<li><span>专家：{{expertZ}}</span>
				<span style="min-height: 30px" class="" data-control="BOX" id="Box_points_switch">
				    <label><input id="switch" ng-click="switchChange()" class="mui-switch mui-switch-anim" type="checkbox"></label>
				</span>
			</li>
		</ul>
		
		<button class="button button-black" ng-if="!isCurrentAdmin && doctorID=='null'" ng-click="queryBookData()">查询</button>
		<button class="button button-black" ng-if="!isCurrentAdmin" ng-click="toBookRecordList()" style="margin-left: 20px">已预约</button>
		<button class="button button-black" ng-if="isCurrentAdmin && doctorID=='null'" ng-click="statisticBookRecord()">统计</button>
		
		<table ng-if="!isCurrentAdmin && doctorID=='null'" class="bookTable" ng-show="showTable == 1">
			<tbody>
				<tr ng-repeat="time in tableContent1">
					<td ng-if="$index == 0">上午<br>8:00 - 12:00</td>
					<td ng-if="$index == 1">下午<br>13:30 - 17:30</td>
					<td ng-repeat="root in time">
						<div ng-if="root.total > 0" ng-click="book(root)" style="background-color: pink;height:126px;padding-top:38px">
							<p>{{root.bookDate}}</p>
							<p ng-if="root.total > 0">预约剩余：{{root.total}}</p>
						</div>
						<div ng-if="root.total <= 0">
							<p>{{root.bookDate}}</p>
							<p ng-if="root.total <= 0">不可预约</p>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<table ng-if="!isCurrentAdmin && doctorID=='null'" class="bookTable" ng-show="showTable == 2">
			<tbody class="bookTable">
				<tr ng-repeat="time in tableContent2">
					<td ng-if="$index == 0">上午<br>8:00 - 12:00</td>
					<td ng-if="$index == 1">下午<br>13:30 - 17:30</td>
					<td ng-repeat="root in time">
						<div ng-if="root.total > 0" ng-click="book(root)" style="background-color: pink;height:126px;padding-top:38px">
							<p>{{root.bookDate}}</p>
							<p ng-if="root.total > 0">预约剩余：{{root.total}}</p>
						</div>
						<div ng-if="root.total <= 0">
							<p>{{root.bookDate}}</p>
							<p ng-if="root.total <= 0">不可预约</p>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div ng-show="!isCurrentAdmin && doctorID=='null'">
			<span><a href="" ng-show="showTable > 1" ng-click="showTable = showTable - 1">上一周</a></span>
			<span><a href="" ng-show="showTable < 2" ng-click="showTable = showTable + 1">下一周</a></span>
		</div>
		
		<table style="width: 90%" ng-show="isCurrentAdmin">
			<tr>
				<th ng-repeat="head in tableHead" style="width: {{head.width}}">{{head.label}}</th>
			</tr>
			<tr ng-repeat="row in tableContent">
				<td>{{row.bookDate}} {{row.time == 1 ? "上午" : "下午"}}</td>
				<td>{{row.departmentName}}</td>
				<td>{{row.doctorName}}</td>
				<td><a href="" ng-click="viewUser(row)">{{row.userId}}</a></td>
				<td>{{doctorEnum.get(row.expert)}}</td>
			</tr>
		</table>
		<div style="width: 90%" ng-show="isCurrentAdmin">
			<span><a href="" ng-show="pageNum > 1" ng-click="pageNum = pageNum - 1; statisticBookRecord()"><</a></span>
			<span>第{{pageNum}}页，共{{totalPage}}页, {{total}}条</span>
			<span><a href="" ng-show="pageNum < totalPage" ng-click="pageNum = pageNum + 1; statisticBookRecord()">></a></span>
		</div>
		
		<div ng-if="doctorID=='null'" id="departmentSelect" title="选择部门">
			<%@ include file="../department/departmentSelect.jsp"%>
		</div>
		<div ng-if="doctorID=='null'" id="doctorSelect" title="选择医生">
			<%@ include file="../doctor/doctorSelect.jsp"%>
		</div>
		<div ng-if="!isCurrentAdmin && doctorID=='null'" id="pay" title="付费">
			<%@ include file="./pay.jsp"%>
		</div>
	</div>

	<%@ include file="../../basePage/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function() {    
	    $("#departmentSelect").hide();    
	});
	$(function() {    
	    $("#doctorSelect").hide();    
	});
	$(function() {    
	    $("#pay").hide();    
	});
	
	app.controller('bookCtrl', function($scope, $http) {
		$scope.currentUserID = currentUserID;
		$scope.currentUserName = currentUserName;
		$scope.doctorID = doctorID;
		$scope.isCurrentAdmin = isCurrentAdmin == "true" ? 1 : 0;

		$scope.date = new Array(14);
		$scope.day = new Array(14);
		$scope.tableContent1 = [
		                       [
		                       	{ id: "", bookDate: $scope.date[0], time: 1, day: $scope.day[0], total: 0, departmentName: "", doctorName: "" },
		                       	{ id: "", bookDate: $scope.date[1], time: 1, day: $scope.day[1], total: 0, departmentName: "", doctorName: "" },
		                       	{ id: "", bookDate: $scope.date[2], time: 1, day: $scope.day[2], total: 0, departmentName: "", doctorName: "" },
		                       	{ id: "", bookDate: $scope.date[3], time: 1, day: $scope.day[3], total: 0, departmentName: "", doctorName: "" },
		                       	{ id: "", bookDate: $scope.date[4], time: 1, day: $scope.day[4], total: 0, departmentName: "", doctorName: "" },
		                       	{ id: "", bookDate: $scope.date[5], time: 1, day: $scope.day[5], total: 0, departmentName: "", doctorName: "" },
		                       	{ id: "", bookDate: $scope.date[6], time: 1, day: $scope.day[6], total: 0, departmentName: "", doctorName: "" },
		                       ],
		                       [
								{ id: "", bookDate: $scope.date[0], time: 2, day: $scope.day[0], total: 0, departmentName: "", doctorName: "" },
								{ id: "", bookDate: $scope.date[1], time: 2, day: $scope.day[1], total: 0, departmentName: "", doctorName: "" },
								{ id: "", bookDate: $scope.date[2], time: 2, day: $scope.day[2], total: 0, departmentName: "", doctorName: "" },
								{ id: "", bookDate: $scope.date[3], time: 2, day: $scope.day[3], total: 0, departmentName: "", doctorName: "" },
								{ id: "", bookDate: $scope.date[4], time: 2, day: $scope.day[4], total: 0, departmentName: "", doctorName: "" },
								{ id: "", bookDate: $scope.date[5], time: 2, day: $scope.day[5], total: 0, departmentName: "", doctorName: "" },
								{ id: "", bookDate: $scope.date[6], time: 2, day: $scope.day[6], total: 0, departmentName: "", doctorName: "" },
		                       ]
		                       ];
		$scope.tableContent2 = [
			                       [
			                       	{ id: "", bookDate: $scope.date[7], time: 1, day: $scope.day[7], total: 0, departmentName: "", doctorName: "" },
			                       	{ id: "", bookDate: $scope.date[8], time: 1, day: $scope.day[8], total: 0, departmentName: "", doctorName: "" },
			                       	{ id: "", bookDate: $scope.date[9], time: 1, day: $scope.day[9], total: 0, departmentName: "", doctorName: "" },
			                       	{ id: "", bookDate: $scope.date[10], time: 1, day: $scope.day[10], total: 0, departmentName: "", doctorName: "" },
			                       	{ id: "", bookDate: $scope.date[11], time: 1, day: $scope.day[11], total: 0, departmentName: "", doctorName: "" },
			                       	{ id: "", bookDate: $scope.date[12], time: 1, day: $scope.day[12], total: 0, departmentName: "", doctorName: "" },
			                       	{ id: "", bookDate: $scope.date[13], time: 1, day: $scope.day[13], total: 0, departmentName: "", doctorName: "" },
			                       ],
			                       [
									{ id: "", bookDate: $scope.date[7], time: 2, day: $scope.day[7], total: 0, departmentName: "", doctorName: "" },
									{ id: "", bookDate: $scope.date[8], time: 2, day: $scope.day[8], total: 0, departmentName: "", doctorName: "" },
									{ id: "", bookDate: $scope.date[9], time: 2, day: $scope.day[9], total: 0, departmentName: "", doctorName: "" },
									{ id: "", bookDate: $scope.date[10], time: 2, day: $scope.day[10], total: 0, departmentName: "", doctorName: "" },
									{ id: "", bookDate: $scope.date[11], time: 2, day: $scope.day[11], total: 0, departmentName: "", doctorName: "" },
									{ id: "", bookDate: $scope.date[12], time: 2, day: $scope.day[12], total: 0, departmentName: "", doctorName: "" },
									{ id: "", bookDate: $scope.date[13], time: 2, day: $scope.day[13], total: 0, departmentName: "", doctorName: "" },
			                       ]
			                       ];
		$scope.showTable = 1;
		
		$scope.tableHead = [
		                     { label: "预约时间", width: "20%"},
		                     { label: "部门", width: "20%"},
		                     { label: "医生", width: "20%"},
		                     { label: "预约者ID", width: "20%" },
		                     { label: "专家", width: "20%"}
						];
		$scope.tableContent = [];
		$scope.pageNum = 1;
		$scope.pageSize = 10;
		$scope.total = 0;
		$scope.totalPage = parseInt($scope.total % $scope.pageSize == 0 ? $scope.total / $scope.pageSize : $scope.total / $scope.pageSize + 1);
		$scope.doctorEnum = doctorEnum;

		$scope.selectedDept = 0;
		$scope.selectedDeptName = "";
		$scope.selectedDoctor = 0;
		$scope.selectedDoctorName = "";
		$scope.expert = 0;
		$scope.expertZ = "非专家";
		$scope.bookDate = "";
		
		$scope.queryBookData = function() {
			if ($scope.selectedDept == 0) {
     			alert("请先选择部门！");
     			return;
     		}
			httpService($http, 'GET', 'queryBookData.do', {
				department: $scope.selectedDept,
				doctor: $scope.selectedDoctor,
				expert: $scope.expert
			}).then(function successCallback(response) {
				for (var i = 0; i < 14; i++) {
					$scope.date[i] = response.data.dates[i];
					$scope.day[i] = response.data.days[i];
				}
				$scope.tableContent1 = [
					                       [
					                       	{ id: "", bookDate: $scope.date[0], time: 1, day: $scope.day[0], total: 0, departmentName: "", doctorName: "" },
					                       	{ id: "", bookDate: $scope.date[1], time: 1, day: $scope.day[1], total: 0, departmentName: "", doctorName: "" },
					                       	{ id: "", bookDate: $scope.date[2], time: 1, day: $scope.day[2], total: 0, departmentName: "", doctorName: "" },
					                       	{ id: "", bookDate: $scope.date[3], time: 1, day: $scope.day[3], total: 0, departmentName: "", doctorName: "" },
					                       	{ id: "", bookDate: $scope.date[4], time: 1, day: $scope.day[4], total: 0, departmentName: "", doctorName: "" },
					                       	{ id: "", bookDate: $scope.date[5], time: 1, day: $scope.day[5], total: 0, departmentName: "", doctorName: "" },
					                       	{ id: "", bookDate: $scope.date[6], time: 1, day: $scope.day[6], total: 0, departmentName: "", doctorName: "" },
					                       ],
					                       [
											{ id: "", bookDate: $scope.date[0], time: 2, day: $scope.day[0], total: 0, departmentName: "", doctorName: "" },
											{ id: "", bookDate: $scope.date[1], time: 2, day: $scope.day[1], total: 0, departmentName: "", doctorName: "" },
											{ id: "", bookDate: $scope.date[2], time: 2, day: $scope.day[2], total: 0, departmentName: "", doctorName: "" },
											{ id: "", bookDate: $scope.date[3], time: 2, day: $scope.day[3], total: 0, departmentName: "", doctorName: "" },
											{ id: "", bookDate: $scope.date[4], time: 2, day: $scope.day[4], total: 0, departmentName: "", doctorName: "" },
											{ id: "", bookDate: $scope.date[5], time: 2, day: $scope.day[5], total: 0, departmentName: "", doctorName: "" },
											{ id: "", bookDate: $scope.date[6], time: 2, day: $scope.day[6], total: 0, departmentName: "", doctorName: "" },
					                       ]
					                       ];
					$scope.tableContent2 = [
						                       [
						                       	{ id: "", bookDate: $scope.date[7], time: 1, day: $scope.day[7], total: 0, departmentName: "", doctorName: "" },
						                       	{ id: "", bookDate: $scope.date[8], time: 1, day: $scope.day[8], total: 0, departmentName: "", doctorName: "" },
						                       	{ id: "", bookDate: $scope.date[9], time: 1, day: $scope.day[9], total: 0, departmentName: "", doctorName: "" },
						                       	{ id: "", bookDate: $scope.date[10], time: 1, day: $scope.day[10], total: 0, departmentName: "", doctorName: "" },
						                       	{ id: "", bookDate: $scope.date[11], time: 1, day: $scope.day[11], total: 0, departmentName: "", doctorName: "" },
						                       	{ id: "", bookDate: $scope.date[12], time: 1, day: $scope.day[12], total: 0, departmentName: "", doctorName: "" },
						                       	{ id: "", bookDate: $scope.date[13], time: 1, day: $scope.day[13], total: 0, departmentName: "", doctorName: "" },
						                       ],
						                       [
												{ id: "", bookDate: $scope.date[7], time: 2, day: $scope.day[7], total: 0, departmentName: "", doctorName: "" },
												{ id: "", bookDate: $scope.date[8], time: 2, day: $scope.day[8], total: 0, departmentName: "", doctorName: "" },
												{ id: "", bookDate: $scope.date[9], time: 2, day: $scope.day[9], total: 0, departmentName: "", doctorName: "" },
												{ id: "", bookDate: $scope.date[10], time: 2, day: $scope.day[10], total: 0, departmentName: "", doctorName: "" },
												{ id: "", bookDate: $scope.date[11], time: 2, day: $scope.day[11], total: 0, departmentName: "", doctorName: "" },
												{ id: "", bookDate: $scope.date[12], time: 2, day: $scope.day[12], total: 0, departmentName: "", doctorName: "" },
												{ id: "", bookDate: $scope.date[13], time: 2, day: $scope.day[13], total: 0, departmentName: "", doctorName: "" },
						                       ]
						                       ];
				for (var i = 0; i < response.data.data.length; i++) {
					var value = response.data.data[i];
					for (var j = 0; j < $scope.tableContent1[0].length; j++) {
						var item = $scope.tableContent1[0][j];
						if (value.bookDate == item.bookDate && value.time == item.time) {
							$scope.tableContent1[0][j].id = value.id;
							$scope.tableContent1[0][j].total = value.total;
							$scope.tableContent1[0][j].departmentName = value.departmentName;
							$scope.tableContent1[0][j].doctorName = value.doctorName;
						}
					}
					for (var j = 0; j < $scope.tableContent1[1].length; j++) {
						var item = $scope.tableContent1[1][j];
						if (value.bookDate == item.bookDate && value.time == item.time) {
							$scope.tableContent1[1][j].id = value.id;
							$scope.tableContent1[1][j].total = value.total;
							$scope.tableContent1[1][j].departmentName = value.departmentName;
							$scope.tableContent1[1][j].doctorName = value.doctorName;
						}
					}
					for (var j = 0; j < $scope.tableContent2[0].length; j++) {
						var item = $scope.tableContent2[0][j];
						if (value.bookDate == item.bookDate && value.time == item.time) {
							$scope.tableContent2[0][j].id = value.id;
							$scope.tableContent2[0][j].total = value.total;
							$scope.tableContent2[0][j].departmentName = value.departmentName;
							$scope.tableContent2[0][j].doctorName = value.doctorName;
						}
					}
					for (var j = 0; j < $scope.tableContent2[1].length; j++) {
						var item = $scope.tableContent2[1][j];
						if (value.bookDate == item.bookDate && value.time == item.time) {
							$scope.tableContent2[1][j].id = value.id;
							$scope.tableContent2[1][j].total = value.total;
							$scope.tableContent2[1][j].departmentName = value.departmentName;
							$scope.tableContent2[1][j].doctorName = value.doctorName;
						}
					}
				}
			}, function errorCallback(response) {
	            // 请求失败执行代码
	    	});
		};
		$scope.statisticBookRecord = function() {
			httpService($http ,'GET', 'statisticBookRecord.do', {				
				pageNum: $scope.pageNum,
				pageSize: $scope.pageSize,
				department: $scope.selectedDept,
				doctor: $scope.selectedDoctor,
				expert: $scope.expert,
				bookDate: $scope.bookDate
			}).
			then(function successCallback(response) {
	            $scope.tableContent = response.data.data;
	            $scope.total = response.data.total;
	            $scope.today = response.data.date;
	    		$scope.totalPage = parseInt($scope.total % $scope.pageSize == 0 ? $scope.total / $scope.pageSize : $scope.total / $scope.pageSize + 1);
			}, function errorCallback(response) {
	            // 请求失败执行代码
	            alert(response.status + ", " + response.statusText);
	    	});
		};
		$scope.viewUser = function(row) {
			window.open('toUserDetail.do?id='+row.userId);
		};
		$scope.toBookRecordList = function() {
			location.href='toBookRecordList.do';
		}
		$scope.book = function(root) {
			if (!currentUserID) { return; }
			$scope.$broadcast('pay4book', root);
			$("#pay").dialog({ 
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
		                    	if (true) {
		                    		httpService($http, 'POST', 'book.do',{
		                				id: root.id,
		                				bookDate: root.bookDate
		                			}).
		                			then(function successCallback(response) {
		                				$scope.queryBookData();
		                				alert(response.data);
		                			}, function errorCallback(response) {
		                	            // 请求失败执行代码
		                	    	});
			            	    	$(this).dialog("close");
		                    	}
		                    },
		            		"取消": function() {
		                		$(this).dialog("close");
	            			}
	            		}
	     	 });
		};
		$scope.generateOneDayRootData = function() {
			httpService($http, 'POST', 'generateOneDayRootData.do',{}).
			then(function successCallback(response) {

			}, function errorCallback(response) {
	            // 请求失败执行代码
	    	});
		};
		$scope.generateTwoWeekData = function() {
			httpService($http, 'POST', 'generateTwoWeekData.do',{}).
			then(function successCallback(response) {

	        }, function errorCallback(response) {
	            // 请求失败执行代码
	    	});
		}
		
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
			                    	$('#department').val($scope.selectedDeptName);
			            	    	$(this).dialog("close");
		                    	}
		                    },
		            		"取消": function() {
		                		$(this).dialog("close");
	            			}
	            		}
	     	 });
    	 };
    	 $scope.doctorSelect = function() {
    		if ($scope.selectedDept == 0) {
      			alert("请先选择部门！");
      			return;
      		}
    		$scope.$broadcast('queryDoctorForSelect', $scope.selectedDept);

     		$("#doctorSelect").dialog({ 
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
 		                    	if (!!$("input[name='doctorSelect']:checked").val()) {
 			                    	$scope.selectedDoctor = tableContent[$("input[name='doctorSelect']:checked").val()].id;
 			            	    	$scope.selectedDoctorName = tableContent[$("input[name='doctorSelect']:checked").val()].doctorName;
 			            	    	$scope.expert = tableContent[$("input[name='doctorSelect']:checked").val()].expert;
 			            	    	if ($scope.expert == 1) {
 			               	        	$scope.expertZ = "专家";
 			            	    	} else {
 			            	    		$scope.expertZ = "非专家";
 			            	    	}
 			            	    	$("#nothing").click();
 			            	    	$('#doctor').val($scope.selectedDoctorName);
 			            	    	$(this).dialog("close");
 			            	    	$("#switch").hide();
 		                    	}
 		                    },
 		            		"取消": function() {
 		                		$(this).dialog("close");
 	            			}
 	            		}
 	     	 });
     	 };
     	 $scope.cancalDoctorSelect = function() {
     		$scope.selectedDoctor = 0;
 	    	$scope.selectedDoctorName = "";
 	    	$('#doctor').val($scope.selectedDoctorName);
 	    	if ($("input[type='checkbox']").is(':checked') == true) {
		        $scope.expert = 1;
		        $scope.expertZ = "专家";
		    } else {
		    	$scope.expert = 0;
		        $scope.expertZ = "非专家";
		    }
 	    	$("#switch").show();
     	 };
     	 $scope.nothing = function() {};
     	 
     	 $scope.switchChange = function() {
	 		if ($("input[type='checkbox']").is(':checked') == true) {
		        $scope.expert = 1;
		        $scope.expertZ = "专家";
		    } else {
		    	$scope.expert = 0;
		        $scope.expertZ = "非专家";
		    }
		 };
	});
</script>
</html>