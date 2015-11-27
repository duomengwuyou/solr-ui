<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${sessionScope.isLogin == null || sessionScope.isLogin == 'false'}">
	<script type="text/javascript">
		if (confirm("尚未登录，请登录后再访问！")) {
			window.location.href="/index"; 
		} else {
			window.location.href="/index"; 
		}
	</script>
</c:if>

<c:if test="${sessionScope.isLogin == 'true'}">
	<script type="text/javascript">
		var userId = <%=(Long)request.getSession().getAttribute("userId") %>;
	</script>
</c:if>

<!DOCTYPE html>
<html>
<head>
<title>Chat</title>
<%@include file="../common/include.jsp"%>
<link href="/public/css/chatstyle.css" media="all"
	rel="stylesheet" type="text/css">

<script type='text/javascript' src='/public/lib/bootbox.js'></script>
<script type='text/javascript' src='/dwr/interface/JavaSide.js'></script>
<script type='text/javascript' src='/public/js/chat.js'></script>

</head>
<body>
	<div class="modal-shiftfix">
		<div class="container-fluid main-content">
			<div class="row">
				<form id="send_message_form" class="form-horizontal" role="form" method="post">
					<!-- Conversation -->
					<div class="col-lg-12">
						<div class="widget-container scrollable chat chat-page">
							<div class="contact-list">
								<div class="heading">
									边聊边看<i class="icon-plus pull-right"></i>
								</div>
								<ul>
									<li>
										<a> 
											<img width="30" height="30" src="/public/images/avatar-female.png"><label>新闻标题</label>
											<p>习近平访问英国期间收到了隆重的招待。</p>
										</a>
									</li>

								</ul>
							</div>
							<div class="heading">
								<i class="icon-comments"></i>与<a>随缘人</a>的聊天
							</div>
							
							<div class="widget-content padded" id="conversation_div">
								<ul id="conversation_ul">
									
								</ul>
							</div>

							<div class="post-message">
								<input class="btn buildCon" id="buildCon" type="button"
									onclick="buildConnection()" value="聊天"> <input
									class="btn buildCon" id="closeCon" style="display: none"
									type="button" onclick="closeConnection()" value="断开">
								<input class="form-control" placeholder="输入需要发送的信息…"
									id="sendMessage" name="sendMessage" type="text"> 
								<input type="submit" value="发送">
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
</body>
</html>