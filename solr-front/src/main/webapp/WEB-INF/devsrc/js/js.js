$(function() {	
	// 登陆校验
	var login_form = $("#login_form");
	login_form.validate({
		focusInvalid : true, // 自动聚焦第一个错误输入
		rules : {
			userName : {
				required : true
			},
			userPass : {
				required : true
			}
		},
		messages : {
			userName : {
				required : "username is required"
			},
			userPass : {
				required : "password is required"
			}
		},
		submitHandler : function() {
			$.ajax({
				type : "POST",
				url : "/login",
				data : login_form.serialize(),
				// timeout: 30000,
				beforeSend : function() {
					$("#isLogin form button").attr('disabled', 'disabled');
				},
				success : function(msg) {
					if (msg.status) {
						if(location.href.indexOf("logout") >= 0) {
							location.href = "/index";
						}else {
							location.reload();
						}

					} else {
						alert(msg.message);
						$("#isLogin form button").removeAttr('disabled',
								'disabled');
					}
				},
				error : function() {
					alert("网路错误！");
					$("#isLogin form button")
							.removeAttr('disabled', 'disabled');
				}
			});
			return false;
		}
	});
});
