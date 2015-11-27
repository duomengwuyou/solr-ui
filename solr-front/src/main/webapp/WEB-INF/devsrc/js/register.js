$(function() {	
	// 登陆校验
	var register_form = $("#register_form");
	register_form.validate({
		focusInvalid : true, // 自动聚焦第一个错误输入
		rules : {
			userName : {
				required : true
			},
			userPass : {
				required : true
			},
			passAgain: {
				required : true
			}
		},
		messages : {
			userName : {
				required : "需要填入用户名"
			},
			userPass : {
				required : "需要填入密码"
			},
			passAgain : {
				required : "需要填入核对密码"
			}
		},
		submitHandler : function() {
			$.ajax({
				type : "POST",
				url : "/register",
				data : register_form.serialize(),
				// timeout: 30000,
				beforeSend : function() {
					$("#register_form form button").attr('disabled', 'disabled');
				},
				success : function(msg) {
					if (msg.status) {
						bootbox.alert("注册成功", function(){
							window.location.href='/index';
						});
					} else {
						bootbox.alert(msg.message);
					}
				},
				error : function() {
					alert("网路错误！");
					$("#register_form form button")
							.removeAttr('disabled', 'disabled');
				}
			});
			return false;
		}
	});

});
