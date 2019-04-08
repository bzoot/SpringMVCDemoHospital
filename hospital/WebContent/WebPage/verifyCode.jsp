<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
	<head>  
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
		<script language="javascript" type="text/javascript">  
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
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	</head>
</html>  