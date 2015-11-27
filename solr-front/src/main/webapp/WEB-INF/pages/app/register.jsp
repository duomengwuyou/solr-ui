<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html
	class="csstransforms csstransforms3d csstransitions js flexbox flexboxlegacy canvas canvastext postmessage websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>随机新闻</title>
<%@include file="../common/include.jsp"%>
<meta charset="utf-8">
<meta name="keywords" content="张老板">
<meta name="description" content="张老板的网站">

<script type='text/javascript' src='/public/lib/bootbox.js'></script>
<script type='text/javascript' src='/public/js/register.js'></script>
</head>
<body class="login2">
	<!-- Signup Screen -->
	<div class="login-wrapper">
		<a href="/index">
			<img width="80" height="40" src="/public/images/logo-login@2x.png">
		</a>
		
		<form id="register_form" method="post">
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon"><i class=" icon-user"></i></span>
					<input class="form-control" type="text" name="userName" placeholder="用户名">
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon"><i class="icon-envelope"></i></span><input
						class="form-control" type="text" name="userEmail" placeholder="邮箱地址">
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon"><i class="icon-lock"></i></span><input
						class="form-control" type="password" name="userPass" placeholder="请输入密码">
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon"><i class="icon-ok"></i></span><input
						class="form-control" type="password" name="passAgain" placeholder="确认密码">
				</div>
			</div>
			<input class="btn btn-lg btn-primary btn-block" type="submit" value="注册">
			
			<p>已经有账户了？</p>
			<a class="btn btn-default-outline btn-block" href="/index">立即登录</a>
		</form>
		
	</div>
	<!-- End Signup Screen -->

</body>
</html>