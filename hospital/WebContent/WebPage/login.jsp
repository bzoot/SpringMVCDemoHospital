<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录界面</title>
<script language="JavaScript" src="./js/GlobalScript.js"></script>
<style type="text/css">  
	.code{  
		background-image:url("./images/bc.jpg");
		font-family:Arial,宋体;  
		font-style:italic;  
		color:green;  
		border:0;  
		padding:2px 3px;  
		letter-spacing:3px;  
		font-weight:bolder;  
	}  
	.unchanged {  
		border:0;  
	}  
</style>  
</head>
<body onLoad="createCode();">
	<form id="loginForm" action="userLogin.do" method="post">
		<img src="./images/hospital.jpg" style="width: 20%; margin-left: 40%; margin-top: 10%"/>
		<div class="login">
			<ul>
				<li style="margin-bottom: 20px"><label style="width: 100px; display: inline-block; text-align-last: justify">用户名:</label>
					<input style="float: right; width: 180px;" name="userName" id="userName" type="text" /></li>
				<li style="margin-bottom: 20px"><label style="width: 100px; display: inline-block; text-align-last: justify">密码:</label>
					<input style="float: right; width: 180px" name="loginPassword" id="loginPassword" type="password" /></li>
				<li style="margin-bottom: 20px"><input type="button" id="checkCode" class="code" style="width:100px" onClick="createCode()" /> 
					<a href="#" onClick="createCode()"></a>  
					<input style="float: right; width: 180px" type="text" id="input1" /></li>
			</ul>
			<p style="width: 60%; margin-left: 20%; text-align-last: justify">
				<input name="" type="button" value="登录" class="button button-black" onclick="login()" /> 
				<a href="https://996.icu"><img src="https://img.shields.io/badge/link-996.icu-red.svg" alt="996.icu" /></a>
				<input name="" type="button" value="注册" class="button button-blue" onclick="toRegister()" />
			</p>
		</div>
	</form>
</body>
<script type="text/javascript">
	function login() {
		var flag = validate();
		if (flag == 1) {
			$.post('loginRecord.do', {
				userName: $('#userName').val(),
				loginPassword: $('#loginPassword').val()
			}, function rsp(data, status) {
				if (!data) { 
					alert("用户名或密码错误！");
					return;
				}
				document.cookie = "userID=" + data.id;
				document.cookie = "userName=" + data.name;
				document.cookie = "isAdmin=" + data.isAdmin;
				document.cookie = "doctorID=" + data.doctor;
				document.getElementById('loginForm').submit();
			});
		}
	}

	function toRegister() {
		window.open("toRegister.do");
	}
	
	var code ; //在全局 定义验证码  
	function createCode() {   
		code = new Array();  
		var codeLength = 4;//验证码的长度  
		var checkCode = document.getElementById("checkCode");  
		checkCode.value = "";  
		  
		var selectChar = new Array(2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');  
		  
		for(var i=0;i<codeLength;i++) {  
		   var charIndex = Math.floor(Math.random()*32);  
		   code +=selectChar[charIndex];  
		}  
		if(code.length != codeLength){  
		   createCode();  
		}  
		checkCode.value = code;  
	}  
	  
	function validate() {  
		var inputCode = document.getElementById("input1").value.toUpperCase();  
		  
		if(inputCode.length <=0) {  
		   alert("请输入验证码！");  
		   return 0;  
		}  
		else if(inputCode != code ){  
		   alert("验证码输入错误！");  
		   createCode();  
		   return 0;  
		}  
		else {  
		   return 1;  
		}  
	}  
</script>
</html>
