<%@ page language="java" pageEncoding="UTF-8"%>
<html class="csstransforms csstransforms3d csstransitions js flexbox flexboxlegacy canvas canvastext postmessage websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>随机新闻</title>
<%@include file="../common/include.jsp"%>
<meta charset="utf-8">
<meta name="keywords" content="张老板">
<meta name="description" content="张老板的网站">

</head>
<body youdao="bind">
	<div class="modal-shiftfix">
		<%@include file="../common/navigation.jsp"%>
		<div class="container-fluid main-content">
			<!-- Gallery with captions -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height">
						<div class="heading">
							<i class="icon-picture"></i>带标题的相册
						</div>
						<div class="widget-content padded">
							<ul class="gallery-grid clearfix">
								<li>
									<figure>
										<img src="/public/images/nature1.jpg">
										<div class="caption">
											<h3>图片标题</h3>
											<span>这里可以添加图片描述……</span>
										</div>
									</figure>
								</li>
								<li>
									<figure>
										<img src="/public/images/nature3.jpg">
										<div class="caption">
											<h3>图片标题</h3>
											<span>这里可以添加图片描述……</span>
										</div>
									</figure>
								</li>
								<li>
									<figure>
										<img src="/public/images/nature2.jpg">
										<div class="caption">
											<h3>图片标题</h3>
											<span>这里可以添加图片描述……</span>
										</div>
									</figure>
								</li>
								<li>
									<figure>
										<img src="/public/images/nature4.jpg">
										<div class="caption">
											<h3>图片标题</h3>
											<span>这里可以添加图片描述……</span>
										</div>
									</figure>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!-- end Gallery with captions -->
			
			<div class="row">
				<!-- Gallery with filtering and actions -->
				<div class="col-lg-12">
					<div class="widget-container fluid-height">
						<div class="heading">
							<i class="icon-th-large"></i>过滤和动作
							<div class="gallery-filters list-inline btn-group pull-right">
								<a class="btn btn-sm btn-primary-outline  selected" data-filter="*" href="http://www.zi-han.net/theme/se7en/gallery.html#">全部</a>
								<a class="btn btn-sm btn-primary-outline" data-filter=".filter1" href="http://www.zi-han.net/theme/se7en/gallery.html#">过滤条件1</a>
							 	<a class="btn btn-sm btn-primary-outline" data-filter=".filter2" href="http://www.zi-han.net/theme/se7en/gallery.html#">过滤条件2</a>
							</div>
						</div>
						
						<div class="widget-content padded">
							<div class="gallery-container isotope" style="position: relative; overflow: hidden; height: 510px; width: 440px;">
								<a class="gallery-item filter1 fancybox isotope-item isotope-hidden" href="/public/images/image-iso5.png"
									rel="gallery1" title="这里显示图片的标题或描述" style="position: absolute; left: 0px; top: 0px; transform: translate3d(0px, 0px, 0px) scale3d(0.001, 0.001, 1); opacity: 0;">
									<img src="/public/images/image-iso5.png">
									<div class="actions">
										<i class="icon-trash"></i>
										<i class="icon-zoom-in"></i>
										<i class="icon-pencil"></i>
									</div> 
								</a>
								
								<a class="gallery-item filter2 fancybox isotope-item" href="/public/images/image-iso3.png"
									rel="gallery1" title="这里显示图片的标题或描述"
									style="position: absolute; left: 0px; top: 0px; transform: translate3d(0px, 0px, 0px) scale3d(1, 1, 1); opacity: 1;"><img
									src="/public/images/image-iso3.png">
									<div class="actions">
										<i class="icon-trash"></i><i class="icon-zoom-in"></i><i
											class="icon-pencil"></i>
									</div> 
								</a>
							
							</div>
						</div>
					</div>
				</div>
				<!-- End Gallery with filtering and actions -->
			</div>
		</div>
	</div>

</body>
</html>