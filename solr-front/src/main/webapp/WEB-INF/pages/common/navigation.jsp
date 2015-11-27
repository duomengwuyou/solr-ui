<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- Navigation -->
<div class="navbar navbar-fixed-top scroll-hide" style="overflow: visible;">
		
			<!-- 上部导航栏 -->
			<div class="container-fluid top-bar">
				<div class="pull-right">
					<ul class="nav navbar-nav pull-right">
						<li class="dropdown">
							<a href="http://www.baidu.com" target="_blank"> <span aria-hidden="true" class="se7en-star"></span></a>
						</li>
						<!-- 页面主题颜色 -->
						<li class="dropdown settings hidden-xs"><a class="dropdown-toggle" data-toggle="dropdown" href="http://www.zi-han.net/theme/se7en/social.html#">
							<span aria-hidden="true" class="se7en-gear"></span>
							<div class="sr-only">设置</div> </a>
							
							<ul class="dropdown-menu">
								<li><a class="settings-link blue" href="javascript:chooseStyle('none', 30)"><span></span>蓝色</a></li>
								<li><a class="settings-link green" href="javascript:chooseStyle('green-theme', 30)"><span></span>绿色</a></li>
								<li><a class="settings-link orange" href="javascript:chooseStyle('orange-theme', 30)"><span></span>橘黄色</a></li>
								<li><a class="settings-link magenta" href="javascript:chooseStyle('magenta-theme', 30)"><span></span>品红</a></li>
								<li><a class="settings-link gray" href="javascript:chooseStyle('gray-theme', 30)"><span></span>灰色</a></li>
							</ul>
						</li>

					</ul>
				</div>
				
				<a class="logo" href="#">ZhangBoss</a>
				
				<form class="navbar-form form-inline col-lg-2 hidden-xs" id="searchForm" method="post">
					<input class="form-control" placeholder="搜索" type="text" name="queryContent" id="queryContent">
				</form>
			</div>

</div>
<!-- End Navigation -->