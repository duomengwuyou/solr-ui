<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>System</title>
<%@include file="../common/include.jsp"%>
<style type="text/css">
h4 {
	padding-left: 10px;
}
</style>
</head>
<body>
	<ol class="breadcrumb">
		<li><a href="/index.jsp">Home</a></li>
		<li class="active">System</li>
	</ol>
	<h4>User Management</h4>
	<div class="panel panel-default">
		<div class="panel-heading collapsed" data-toggle="collapse"
			href="#one">
			<h3 class="panel-title">
				<span class="caret"></span>Users List
			</h3>
		</div>
		<div id="one" class="panel-collapse collapse">
			<div class="panel-body userList">nobody!</div>
		</div>

		<div class="panel-heading collapsed" data-toggle="collapse"
			href="#two">
			<h3 class="panel-title">
				<span class="caret"></span>Add User
			</h3>
		</div>
		<div id="two" class="panel-collapse collapse">
			<div class="panel-body">
				<form id="addUser_form" class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-sm-2 control-label">username</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userName"
								placeholder="Please enter the prefix of baidu email address">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">password</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" name="userPass"
								placeholder="Please enter the password">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">token</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" name="token"
								placeholder="Please enter the token">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">default product</label>
						<div class="col-sm-10">
							<select class="form-control" name="appName">
								<option value="booster">Booster</option>
								<option value="saver">Battery</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-default">Add</button>
						</div>
					</div>
				</form>
			</div>
		</div>

		<div class="panel-heading collapsed" data-toggle="collapse"
			href="#three">
			<h3 class="panel-title">
				<span class="caret"></span>Delete User
			</h3>
		</div>
		<div id="three" class="panel-collapse collapse">
			<div class="panel-body">
				<form id="DelUser_form"
					class="form-inline internal_user_report_search" role="form">
					<div class="form-group">
						<label class="control-label">User:</label> <select
							class="form-control userList_select" name="userName">
							<option>none</option>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label">token:</label> <input type="password"
							class="form-control" name="token"
							placeholder="Please enter the token">
					</div>
					<button type="submit" class="btn btn-default">Delete</button>
				</form>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	$(function() {
		//页面加载时获取数据
		//获取用户列表
		$.ajax({
			type : "post",
			url : "/getAllUsers",
			timeout : 10000,
			success : function(msg) {
				var userList = "";
				var userList_select = "";
				if (msg.status) {
					for (var i = 0; i < msg.data.length; i++) {
						userList_select += "<option>" + msg.data[i]
								+ "</option>";
						if (i == 0) {
							userList = userList + msg.data[i];
						} else {
							userList = userList + " | " + msg.data[i];
						}
					}
					;
					$(".userList_select").html(userList_select);
					if (userList) {
						$(".userList").html(userList);
					}
				}
			},
			error : function() {
				$(".userList").html("data request fail!");
			}
		});

		//页面加载时获取数据 end

		//设置booster版本信息
		$("#booster_language_form").validate({
			focusInvalid : true, //自动聚焦第一个错误输入
			rules : {
				realVersion : {
					required : true
				},
				token : {
					required : true
				}
			},
			messages : {
				realVersion : {
					required : "Version is required"
				},
				token : {
					required : "Token is required"
				}
			},
			submitHandler : function() {
				$.ajax({
					type : "POST",
					url : "/setVersion",
					data : $("#booster_language_form").serialize(),
					timeout : 5000,
					beforeSend : function() {
					},
					success : function(msg) {
						if (msg.status) {
							alert("Set Booster Version success!");
							location.reload();
						} else {
							alert(msg.message);
						}
					},
					error : function() {
						alert("Network error!");
					}
				});
				return false;
			}
		});

		//添加用户
		$("#addUser_form").validate({
			focusInvalid : true, //自动聚焦第一个错误输入
			rules : {
				userName : {
					required : true
				},
				userPass : {
					required : true
				},
				token : {
					required : true
				}
			},
			messages : {
				username : {
					required : "username is required"
				},
				userPass : {
					required : "password is required"
				},
				token : {
					required : "password is required"
				}
			},
			submitHandler : function() {
				$.ajax({
					type : "POST",
					url : "/register",
					data : $("#addUser_form").serialize(),
					timeout : 5000,
					beforeSend : function() {
					},
					success : function(msg) {
						if (msg.status) {
							alert("Add user success!");
							location.reload();
						} else {
							alert(msg.message);
						}
					},
					error : function() {
						alert("Network error!");
					}
				});
				return false;
			}
		});

		//删除用户
		$("#DelUser_form").validate({
			focusInvalid : true, //自动聚焦第一个错误输入
			rules : {
				token : {
					required : true
				}
			},
			messages : {
				token : {
					required : "password is required"
				}
			},
			submitHandler : function() {
				$.ajax({
					type : "POST",
					url : "/delUser",
					data : $("#DelUser_form").serialize(),
					timeout : 5000,
					beforeSend : function() {
					},
					success : function(msg) {
						if (msg.status) {
							alert("Delete user success!");
							location.reload();
						} else {
							alert(msg.message);
						}
					},
					error : function() {
						alert("Network error!");
					}
				});
				return false;
			}
		});

	});
</script>
</html>