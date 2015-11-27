<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="csstransforms csstransforms3d csstransitions js flexbox flexboxlegacy canvas canvastext postmessage websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>随机新闻</title>
<%@include file="../common/include.jsp"%>
<meta charset="utf-8">
<meta name="keywords" content="张老板">
<meta name="description" content="张老板的网站">


<div class="fit-vids-style" id="fit-vids-style" style="display: none;">
	<style>
		.fluid-width-video-wrapper {
			width: 100%;
			position: relative;
			padding: 0;
		}
		
		.fluid-width-video-wrapper iframe, .fluid-width-video-wrapper object,
			.fluid-width-video-wrapper embed {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
		}
	</style>
</div>



<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">

<style type="text/css">
.fancybox-margin {
	margin-right: 0px;
}
</style>

<style type="text/css">
.jqstooltip {
	position: absolute;
	left: 0px;
	top: 0px;
	visibility: hidden;
	background: rgb(0, 0, 0) transparent;
	background-color: rgba(0, 0, 0, 0.6);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000,
		endColorstr=#99000000);
	-ms-filter:
		"progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";
	color: white;
	font: 10px arial, san serif;
	text-align: left;
	white-space: nowrap;
	padding: 5px;
	border: 1px solid white;
	z-index: 10000;
}

.jqsfield {
	color: white;
	font: 10px arial, san serif;
	text-align: left;
}
</style>
</head>

<body youdao="bind">
	<div class="modal-shiftfix">
		<%@include file="../common/navigation.jsp"%>
		<div class="container-fluid main-content">
			<div class="social-wrapper">
				<div id="social-container" class="isotope" style="position: relative; overflow: hidden; height: 2369px; width: 1110px;">
										
					<div class="item widget-container fluid-height profile-widget isotope-item" style="position: absolute; left: 0px; top: 0px; opacity: 1; transform: translate3d(370px, 0px, 0px) scale3d(1, 1, 1);">
						<div class="widget-content padded">
							<div class="profile-info clearfix">
								<img width="70" height="70" class="social-avatar pull-left" src="/public/images/avatar-male.jpg">
								<div class="profile-details">
									<a class="user-name" href="#">子涵</a>
									<p>知名设计师</p>
									<em><i class="icon-map-marker"></i>甘肃兰州</em>
								</div>
							</div>
							
							<div class="profile-stats">
								<div class="col-xs-4">
									<a href="#">
										<h2>245</h2>
									</a>
									<p>文章</p>
								</div>
								<div class="col-xs-4">
									<a href="#">
										<h2>150</h2>
									</a>
									<p>朋友</p>
								</div>
								<div class="col-xs-4">
									<a href="#">
										<h2>12</h2>
									</a>
									<p>视频</p>
								</div>
							</div>
						</div>
					</div>
					
					
					<div class="item widget-container fluid-height social-entry isotope-item" style="position: absolute; left: 0px; top: 0px; opacity: 1; transform: translate3d(740px, 0px, 0px) scale3d(1, 1, 1);">
						<div class="widget-content padded">
							<div class="profile-info clearfix">
								<img width="50" height="50" class="social-avatar pull-left" src="/public/images/avatar-female2.png">
								<div class="profile-details">
									<a class="user-name" href="http://www.zi-han.net/theme/se7en/social.html#">sheepppp</a>
									<p>
										<em>2 天前</em>
									</p>
								</div>
							</div>
							
							<p class="content">生活小智慧：做人低调的21条，句句都是精华！#生活智慧#</p>
							<div class="btn btn-sm btn-default-outline">
								<i class="icon-thumbs-up-alt"></i>
							</div>
							<div class="btn btn-sm btn-default-outline">
								<i class="icon-thumbs-down-alt"></i>
							</div>
						</div>
					</div>
					
					
					<div class="item social-widget weibo isotope-item" style="position: absolute; left: 0px; top: 0px; opacity: 1; transform: translate3d(0px, 200px, 0px) scale3d(1, 1, 1);">
						<i class="icon-weibo"></i>
						<div class="social-data">
							<h1>16,009</h1>
							关注
						</div>
					</div>
					
					
					<div class="item widget-container clearfix social-entry isotope-item" style="position: absolute; left: 0px; top: 0px; opacity: 1; transform: translate3d(370px, 200px, 0px) scale3d(1, 1, 1);">
						<div class="widget-content">
							<div class="profile-info clearfix padded">
								<img width="50" height="50" class="social-avatar pull-left" src="/public/images/avatar-male2.png">
								<div class="profile-details">
									<a class="user-name" href="http://www.zi-han.net/theme/se7en/social.html#">木马1989</a>
									<p>
										<em>12 天前</em>
									</p>
								</div>
							</div>
							
							<img width="350" height="262" class="social-content-media" src="/public/images/social-image.jpg">
							
							<div class="padded">
								<p class="content">老中医健康减肥养生堂【中医教你七个“按时”巧养生法】</p>
								<div class="btn btn-sm btn-default-outline">
									<i class="icon-thumbs-up-alt"></i>
								</div>
								<div class="btn btn-sm btn-default-outline">
									<i class="icon-thumbs-down-alt"></i>
								</div>
							</div>
						</div>	
					</div>			
		
					
					
					<div class="item widget-container clearfix social-entry isotope-item" style="position: absolute; left: 0px; top: 0px; opacity: 1; transform: translate3d(0px, 1042px, 0px) scale3d(1, 1, 1);">
						<div class="widget-content">
							<div class="profile-info clearfix padded">
								<img width="50" height="50" class="social-avatar pull-left"
									src="/public/images/avatar-male2.png">
								<div class="profile-details">
									<a class="user-name"
										href="http://www.zi-han.net/theme/se7en/social.html#">盒子的故事</a>
									<p>
										<em>12 天前</em>
									</p>
								</div>
							</div>
							<img width="350" height="262" class="social-content-media"
								src="/public/images/manofscience.jpg">
							<div class="padded">
								<div class="btn btn-sm btn-default-outline">
									<i class="icon-thumbs-up-alt"></i>
								</div>
								<div class="btn btn-sm btn-default-outline">
									<i class="icon-thumbs-down-alt"></i>
								</div>
							</div>
						</div>
					</div>
					
					
					<div class="item widget-container fluid-height clearfix social-entry isotope-item" style="position: absolute; left: 0px; top: 0px; opacity: 1; transform: translate3d(740px, 1803px, 0px) scale3d(1, 1, 1);">
						<div class="widget-content">
							<div class="profile-info clearfix padded">
								<img width="50" height="50" class="social-avatar pull-left"
									src="/public/images/avatar-female2.png">
								<div class="profile-details">
									<a class="user-name" href="#">Stan Humanbot</a>
									<p>
										<em>2 天前</em>
									</p>
								</div>
							</div>
							<iframe height="300" width="100%" src="/public/images/YoukuPlayer.html" frameborder="0" allowfullscreen=""></iframe>
							<div class="padded">
								<p class="content">谷歌眼镜升级版现身 与近视镜结合 131107</p>
								<div class="btn btn-sm btn-default-outline">
									<i class="icon-thumbs-up-alt"></i>
								</div>
								<div class="btn btn-sm btn-default-outline">
									<i class="icon-mail-forward"></i>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-lg-12">
						<div class="btn btn-lg btn-block btn-default" id="load-more">加载更多</div>
					</div>
				</div>
				
				<div id="hidden-items">
					
				</div>
			</div>
		</div>
	</div>

</body>
</html>