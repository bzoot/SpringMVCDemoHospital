<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>预约列表</title>
</head>
<body ng-app="app">
	<%@ include file="../../basePage/menus.jsp"%>

	<div class="mainContent" ng-controller="queryBookCtrl">
		<table style="width: 90%; margin-left: 5%; margin-right: 5%">
			<tr>
				<th ng-repeat="head in tableHead" style="width: {{head.width}}">{{head.label}}</th>
			</tr>
			<tr ng-repeat="row in tableContent">
				<td>{{row.bookDate}} {{row.time == 1 ? "上午" : "下午"}}</td>
				<td>{{row.departmentName}}</td>
				<td ng-if="doctor == 'null'">{{row.doctorName}}</td>
				<td ng-if="doctor != 'null'"><a href="" ng-click="viewUser(row)">{{row.userId}}</a></td>
				<td>{{doctorEnum.get(row.expert)}}</td>
				<td><a ng-if="row.bookDate > today && doctor == 'null'" href="" ng-click="deleteBookRecord(row)" style="margin-left: 15px">删除</a>
				<a ng-if="row.bookDate > today && doctor != 'null'" href="" ng-click="transBookRecord(row)" style="margin-left: 15px">转让</a></td>
			</tr>
		</table>
		<div style="width: 90%; margin-left: 5%; margin-right: 5%" ng-show="doctor == 'null'">
			<span><a href="" ng-show="pageNum > 1" ng-click="pageNum = pageNum - 1; queryBookRecord()"><</a></span>
			<span>第{{pageNum}}页，共{{totalPage}}页, {{total}}条</span>
			<span><a href="" ng-show="pageNum < totalPage" ng-click="pageNum = pageNum + 1; queryBookRecord()">></a></span>
		</div>
		<div style="width: 90%; margin-left: 5%; margin-right: 5%" ng-show="doctor != 'null'">
			<span><a href="" ng-show="pageNum > 1" ng-click="pageNum = pageNum - 1; queryBookedRecord()"><</a></span>
			<span>第{{pageNum}}页，共{{totalPage}}页, {{total}}条</span>
			<span><a href="" ng-show="pageNum < totalPage" ng-click="pageNum = pageNum + 1; queryBookedRecord()">></a></span>
		</div>
		
		<div id="book2TransSelect" title="选择转让医生">
			<%@ include file="./book2Trans.jsp" %>
		</div>
	</div>
	
	<%@ include file="../../basePage/footer.jsp"%>
</body>
<script language="JavaScript" src="./js/doctor/DoctorEnum.js"></script>
<script type="text/javascript">
	$(function() {    
	    $("#book2TransSelect").hide();    
	});

	app.controller('queryBookCtrl', function($scope, $http) {
		$scope.currentUserID = currentUserID;
		$scope.currentUserName = currentUserName;
		$scope.isCurrentAdmin = isCurrentAdmin == "true" ? 1 : 0;
		$scope.doctor = doctorID;
		
		$scope.pageNum = 1;
		$scope.pageSize = 10;
		$scope.total = 0;
		$scope.totalPage = parseInt($scope.total % $scope.pageSize == 0 ? $scope.total / $scope.pageSize : $scope.total / $scope.pageSize + 1);
		
		$scope.tableContent = [];
		$scope.today = "";
		$scope.tableHead = [
		                     { label: "预约时间", width: "20%"},
		                     { label: "部门", width: "20%"},
		                     { label: doctorID == 'null' ? "医生" : "预约者ID", width: "20%"},
		                     { label: "专家", width: "20%"},
		                     { label: "操作", width: "20%"},
						];
		$scope.doctorEnum = doctorEnum;
		$scope.queryBookRecord = function() {
			httpService($http ,'GET', 'querySelfBookRecord.do',{				
				pageNum: $scope.pageNum,
				pageSize: $scope.pageSize
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
		$scope.queryBookedRecord = function() {
			httpService($http ,'GET', 'querySelfBookedRecord.do', {				
				pageNum: $scope.pageNum,
				pageSize: $scope.pageSize,
				doctor: doctorID
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
		if (doctorID == 'null') {
			($scope.queryBookRecord());
		} else {
			($scope.queryBookedRecord());
		}
		
		$scope.viewUser = function(row) {
			window.open('toUserDetail.do?id='+row.userId);
		};
		$scope.deleteBookRecord = function(row) {
			httpService($http ,'POST', 'deleteBookRecord.do', row).
			then (function successCallback(response) {
				alert(response.data);
				$scope.queryBookRecord();
			}, function errorCallback(response) {
				alert(response.data);
			});
		};
		$scope.transBookRecord = function(row) {
			$scope.$broadcast('book2TransSelect', row);
			
			$("#book2TransSelect").dialog({ 
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
		                    	if (!!$("input[name='bookSelected']:checked").val()) {
		                    		httpService($http ,'POST', 'transBookRecord.do', {
		                    			transSourceID: row.id,
		                    			transTargetID: tableContent[$("input[name='bookSelected']:checked").val()].id
		                    		}).
		                			then (function successCallback(response) {
		                				console.log(response)
		                				alert(response.data);
		                				$scope.queryBookedRecord();
		                			}, function errorCallback(response) {
		                				alert(response.data);
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
	});
</script>
</html>