// 记录当前是否会话中
var connectedOrNot = false;

// 展现警告内容
function showAlert(alertMessage) {
	bootbox.alert(alertMessage);
}

//获取当前的am pm时间
function getAmPmTime() {
	var Digital = new Date()
	var hours = Digital.getHours()
	var minutes = Digital.getMinutes()
	var seconds = Digital.getSeconds()
	var dn = "AM"
	if (hours > 12) {
		dn = "PM"
		hours = hours - 12
	}
	if (hours == 0)
		hours = 12
	if (minutes <= 9)
		minutes = "0" + minutes
	if (seconds <= 9)
		seconds = "0" + seconds
	var ctime = hours + ":" + minutes + ":" + seconds + " " + dn
	return ctime;
}

// 增加发送信息框
function sendFrame(sendMessage, fromUser) {
	var result = "<li class=\"current-user\">"
			+ "<img width=\"30\" height=\"30\" src=\"/public/images/avatar-female.jpg\">"
			+ "<div class=\"bubble\"><a class=\"user-name\" >" + fromUser
			+ "</a>" + "<p class=\"message\">" + sendMessage + "</p>"
			+ "<p class=\"time\"><strong>" + getAmPmTime()
			+ "</strong></p></div></li>";

	$("#conversation_ul").append(result);
	scrollToBottom();
}

// 增加接收消息
function receivedFrame(receiveMessage, fromUser) {
	var result = "<li>"
			+ "<img width=\"30\" height=\"30\" src=\"/public/images/avatar-female.jpg\">"
			+ "<div class=\"bubble\"><a class=\"user-name\" >" + fromUser
			+ "</a>" + "<p class=\"message\">" + receiveMessage + "</p>"
			+ "<p class=\"time\"><strong>" + getAmPmTime()
			+ "</strong></p></div></li>";

	$("#conversation_ul").append(result);
	scrollToBottom();
}

// 页面显示滚动条最底部
function scrollToBottom() {
	$('#conversation_div').scrollTop($('#conversation_div')[0].scrollHeight);
}

// 显示连接按钮
function showConButton() {
	$("#buildCon").css("display", "block");
	$("#closeCon").css("display", "none");
	connectedOrNot = false;
}

// 显示关闭连接按钮
function showCloseConButton() {
	$("#buildCon").css("display", "none");
	$("#closeCon").css("display", "block");
	connectedOrNot = true;
}

// 建立连接
function buildConnection() {
	$("#conversation_ul").empty();
	sendFrame("......向服务器发送建立连接请求......","我");
	JavaSide.buildConnection({
		callback : function(str) {
			if (str == true) {
				$("#buildCon").css("display", "none");
				$("#closeCon").css("display", "block");
			}
		},
		timeout : 5000,
		errorHandler : function(message) {
			showAlert("Oops: " + message);
		}
	});
}

// 关闭连接
function closeConnection() {
	sendFrame("......向服务器发送关闭连接请求......","我");
	JavaSide.closeConnection({
		callback : function(str) {
			if (str == true) {
				showConButton();
			}
		},
		timeout : 5000,
		errorHandler : function(message) {
			showAlert("Oops: " + message);
		}
	});
}

// 清空发送栏消息
function emptyMessage() {
	$("#sendMessage").val("");
}

// 关闭当前窗口
function customClose(message) {
	bootbox.alert(message, function() {
		closeWindow();
	});
}

function closeWindow() {
	var userAgent = navigator.userAgent;
	if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") != -1) {
		window.location.href = "about:blank";
	} else {
		window.opener = null;
		window.open("", "_self");
		window.close();
	}
}


$(function() {
	// 设置java翻转控制ajax
	dwr.engine.setActiveReverseAjax(true);
	// 页面unload的时候是否通知服务端
	dwr.engine.setNotifyServerOnPageUnload(true);

	JavaSide.pageLoad(userId, {
		callback : function() {
		}
	});
	// 发送信息
	$("#send_message_form").validate({
		focusInvalid : true, // 自动聚焦第一个错误输入
		rules : {
			sendMessage : {
				required : true
			}
		},
		messages : {
			sendMessage : {
				required : "发送信息不能为空"
			}
		},
		submitHandler : function() {
			if (connectedOrNot == false) {
				showAlert("不在会话中，不能发送消息，请先建立连接。");
				emptyMessage();
				return;
			}
			var sendMessage = $("#sendMessage").val();
			// 绘制发送框
			sendFrame(sendMessage, "我");
			emptyMessage();

			JavaSide.sendMessage(sendMessage, {
				timeout : 5000,
				errorHandler : function(message) {
					showAlert("Oops: " + message);
				}
			});
		}
	});

});