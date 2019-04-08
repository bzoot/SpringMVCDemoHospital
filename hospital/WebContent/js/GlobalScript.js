document.write("<script language='JavaScript' src='./js/angular.min.js'></script>");
document.write("<script language='JavaScript' src='./js/jquery-3.2.1.min.js'></script>");
document.write("<script language='JavaScript' src='./js/jquery-ui.min.js'></script>");
document.write("<script language='JavaScript' src='./js/ui.js'></script>");
document.write("<link rel='stylesheet' type='text/css' href='./css/jquery-ui.min.css'/>");
document.write("<link rel='stylesheet' type='text/css' href='./css/jquery-ui.structure.min.css'/>");
document.write("<link rel='stylesheet' type='text/css' href='./css/jquery-ui.theme.min.css'/>");
document.write("<link rel='stylesheet' type='text/css' href='./css/MainCss.css'/>");
document.write("<link rel='stylesheet' type='text/css' href='./css/menu/style.css'>");
document.write("<link rel='stylesheet' type='text/css' href='./css/button/style.css'>");
document.write("<link rel='stylesheet' type='text/css' href='css/alert/style.css'>");

var currentUserID = getCookie("userID");
var currentUserName = getCookie("userName");
var isCurrentAdmin = getCookie("isAdmin");
var doctorID = getCookie("doctorID");

function alert(msg, more) {
	mizhu.alert('', msg, 'alert_red', more);
}

function httpService(http, method, url, request) {
	if (null == currentUserID) {
		alert("请先登录！");
		return;
	}
	request.currentUser = currentUserID;
	return http({
		method: method,
		url: url,
		params: request
	});
};

function ajaxService(method, url, request, rspFunction) {
	if (null == currentUserID) {
		alert("请先登录！");
		return;
	}
	request.currentUser = currentUserID;
	if (method == 'POST') {
		$.post(url, request, rspFunction);
	} else if (method == 'GET') {
		$.get(url, request, rspFunction);
	}
}

function setCookie(cname,value,expiredays) {
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays)
	document.cookie=cname+ "=" +escape(value)+
					((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i<ca.length; i++) 
	{
		var c = ca[i].trim();
		if (c.indexOf(name)==0) return c.substring(name.length,c.length);
	}
	return "";
}

function clearCookie(cname) {    
    setCookie(cname, "", -1);    
}



var booleanEnum = new Map();

booleanEnum.set(true, "是");
booleanEnum.set(false, "否");