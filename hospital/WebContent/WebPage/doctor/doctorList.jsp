<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>医生</title>
</head>
<body ng-app="app">
	<%@ include file="../../basePage/menus.jsp"%>

	<div class="mainContent" ng-controller="queryDocCtrl">
		<button class="button button-black" style="margin-left: 5%; margin-top: 10%" ng-if="isCurrentAdmin" onclick="javascript:location.href='toAddDoctor.do'">添加医生</button>
		<table style="width: 90%; margin-left: 5%; margin-right: 5%;">
			<tr>
				<th ng-repeat="head in tableHead" style="width: {{head.width}}">{{head.label}}</th>
			</tr>
			<tr ng-repeat="row in tableContent">
				<td>{{row.doctorName}}</td>
				<td>{{row.departmentName}}</td>
				<td>{{doctorEnum.get(row.expert)}}</td>
				<td><a ng-if="isCurrentAdmin" href="" ng-click="deleteDoctor(row)" style="margin-left: 15px">删除</a>
					<a ng-if="isCurrentAdmin" href="" ng-click="updateDoctor(row)" style="margin-left: 15px">更新</a>
					<a href="" ng-click="viewDoctor(row)" style="margin-left: 15px">查看</a></td>
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
<script language="JavaScript" src="./js/doctor/DoctorEnum.js"></script>
<script type="text/javascript">
	app.controller('queryDocCtrl', function($scope, $http) {
		$scope.currentUserID = currentUserID;
		$scope.currentUserName = currentUserName;
		$scope.isCurrentAdmin = isCurrentAdmin == "true" ? 1 : 0;
		
		$scope.pageNum = 1;
		$scope.pageSize = 10;
		$scope.total = 0;
		$scope.totalPage = parseInt($scope.total % $scope.pageSize == 0 ? $scope.total / $scope.pageSize : $scope.total / $scope.pageSize + 1);
		
		$scope.tableContent = [];
		$scope.tableHead = [
		                     { label: "医生姓名", width: "25%"},
		                     { label: "部门", width: "25%"},
		                     { label: "专家", width: "25%"},
		                     { label: "操作", width: "25%"},
						]
		$scope.doctorEnum = doctorEnum;
		$scope.queryDoctor = function() {
			httpService($http ,'GET', 'queryDoctorForPage.do',{				
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
		($scope.queryDoctor());
		
		$scope.viewDoctor = function(row) {
			/* location.href='toDoctorDetail.do?'+'id='+row.id+'&doctorName='+row.doctorName+'&doctorDesc='
					+row.doctorDesc+'&expert='+row.expert+'&department='+row.department+'&departmentName='
					+row.departmentName; */
			window.open('toDoctorDetail.do?id='+row.id);
		}
		$scope.deleteDoctor = function(row) {
			httpService($http ,'POST', 'deleteDoctor.do', row).
			then (function successCallback(response) {
				alert(response.data);
				$scope.queryDoctor();
			}, function errorCallback(response) {
				alert(response.data);
			});
		};
		$scope.updateDoctor = function(row) {
			location.href='toUpdateDoctor.do?id='+row.id;
			/*location.href='toUpdateDoctor.do?'+'id='+row.id+'&doctorName='+row.doctorName+'&doctorDesc='
					+row.doctorDesc+'&expert='+row.expert+'&department='+row.department+'&departmentName='
					+row.departmentName;
					
			document.write('<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></head>');
			document.write("<html><body><form action='toUpdateDoctor.do' method='post' name='toUpdateDoctor' style=''>");
			document.write("<input type='hidden' name='id' value='"+row.id+"'/>");
			document.write("<input type='hidden' name='doctorName' value='"+row.doctorName+"'/>");
			document.write("<input type='hidden' name='doctorDesc' value='"+row.doctorDesc+"'/>");
			document.write("<input type='hidden' name='expert' value='"+row.expert+"'/>");
			document.write("<input type='hidden' name='department' value='"+row.department+"'/>");
			document.write("<input type='hidden' name='departmentName' value='"+row.departmentName+"'/>");
			document.write("</form></body></html>");
			document.toUpdateDoctor.submit(); */
		}
	});
</script>
</html>