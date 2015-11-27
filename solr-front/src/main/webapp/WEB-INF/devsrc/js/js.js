// 展现警告内容
function showAlert(alertMessage) {
	bootbox.alert(alertMessage);
}

function addSearchItem(items) {
	for (var i = 0; i < items.length; i++) {
		var newDisplayItemHtml = "" + 
		"<div class='item widget-container fluid-height social-entry isotope-item' style='position: absolute; left: 0px; top: 0px; opacity: 1; transform: translate3d(740px, 0px, 0px) scale3d(1, 1, 1);'>" +
			"<div class='widget-content padded'>" +
				"<div class='profile-info clearfix'>" +
					"<img width='50' height='50' class='social-avatar pull-left' src='/public/images/avatar-female2.png'>" +
					"<div class='profile-details'>" +
						"<a class='user-name' href='http://www.zi-han.net/theme/se7en/social.html#'>"+items[i].title+"</a>" +
						"<p>" +
							"<em>"+items[i].contentDate+"</em>" +
						"</p>" +
					"</div>" +
				"</div>" +
				
				"<p class='content'>"+items[i].content+"</p>" +
				"<div class='btn btn-sm btn-default-outline'>" +
					"<a href='"+items[i].sourceLink+"' target='_blank'><i class='icon-hand-up'></i></a>" +
				"</div>" +
				"<div class='rightbtn btn btn-sm btn-default-outline'>" +
					"<i class='icon-thumbs-up-alt'></i>" +
				"</div>" +
				"<div class='rightbtn btn btn-sm btn-default-outline'>" +
					"<i class='icon-thumbs-down-alt'></i>" +
				"</div>" +
			"</div>" +
		"</div>";
		
		tmp = $().add(newDisplayItemHtml);
		$("#social-container").isotope('insert', tmp);	
	}
}

$(function() {
	// 搜索检验
	var searchForm = $("#searchForm");
	searchForm.validate({
		focusInvalid : true, // 自动聚焦第一个错误输入
		rules : {
			queryContent : {
				required : true
			}
		},
		messages : {
			queryContent : {
				required : "搜索内容不能为空"
			}
		},
		submitHandler : function() {
			$.ajax({
				type : "POST",
				url : "/dosearch",
				data : searchForm.serialize(),
				success : function(msg) {
					if (msg.status) {
						addSearchItem(msg.data);
					} else {
						showAlert(msg.message);
					}
				},
				error : function() {
					showAlert("网路错误！");
				}
			});
			return false;
		}
	});
});
