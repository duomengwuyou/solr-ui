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
						 <li id="isLogin">
				            <c:if test="${sessionScope.isLogin == null || sessionScope.isLogin == 'false'}">
				               <form id="login_form" class="navbar-form form-inline col-lg-2 hidden-xs" >
				                  <div class="form-group">
				      				 <input class="form-control" placeholder="用户名" type="text" name="userName">
				      				 <input class="form-control" placeholder="密码" type="text" name="userPass">				                     
				                     <button type="submit" >登录</button>
				                     <button type="button" onclick="window.location.href='/toRegister'">注册</button>
				                  </div>
				               </form>
				            </c:if>
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
						
						<!-- 管理员设置 -->
						<c:if test="${sessionScope.isLogin == 'true'}">
						<li class="dropdown user hidden-xs" id="">
							<a data-toggle="dropdown" class="dropdown-toggle" href="http://www.zi-han.net/theme/se7en/social.html#"> 
								<img width="34" height="34" src="/public/images/avatar-male.jpg">管理员<b class="caret"></b>
							</a>
							
							<ul class="dropdown-menu">
								<li>
									<a href="##"> <i class="icon-gear"></i>账户设置</a>
								</li>
								
								<li>
									<a href="/logout"> <i class="icon-signout"></i>退出</a>
								</li>
							</ul>
						</li>
						</c:if>
					</ul>
				</div>
				
				<a class="logo" href="#">ZhangBoss</a>
				
				<form class="navbar-form form-inline col-lg-2 hidden-xs">
					<input class="form-control" placeholder="搜索" type="text">
				</form>
			</div>
			
			<!-- 图标化导航栏 -->
			<div class="container-fluid main-nav clearfix">
				<div class="nav-collapse">
					<ul class="nav">
						<li>
							<c:if test="${sessionScope.curPage == 'index' }">
								<a class="current" href="/index"><span aria-hidden="true" class="se7en-home"></span>首页</a>
							</c:if>
							<c:if test="${sessionScope.curPage != 'index' }">
								<a href="/index"><span aria-hidden="true" class="se7en-home"></span>首页</a>
							</c:if>
						</li>
						
						<li>
							<c:if test="${sessionScope.curPage == 'newsinfo' }">
								<a class="current" href="/newsinfo"><span aria-hidden="true" class="se7en-feed"></span>随机新闻</a>
							</c:if>
							<c:if test="${sessionScope.curPage != 'newsinfo' }">
								<a href="/newsinfo"><span aria-hidden="true" class="se7en-feed"></span>随机新闻</a>
							</c:if>
						</li>
						
						<li class="dropdown">
							<c:if test="${sessionScope.curPage == 'chat' }">
								<a class="current" href="/chat" target="_blank"> <span aria-hidden="true" class="se7en-star"></span>随机聊天</a>
							</c:if>
							<c:if test="${sessionScope.curPage != 'chat' }">
								<a href="/chat" target="_blank"> <span aria-hidden="true" class="se7en-star"></span>随机聊天</a>
							</c:if>
						</li>
						
						<li>
							<c:if test="${sessionScope.curPage == 'photo' }">
								<a class="current" href="/photo"><span aria-hidden="true" class="se7en-gallery"></span>随机图片</a>
							</c:if>
							<c:if test="${sessionScope.curPage != 'photo' }">
								<a href="/photo"><span aria-hidden="true" class="se7en-gallery"></span>随机图片</a>
							</c:if>
						</li>
						
						<li>
							<a href="#" target="_blank"> <span aria-hidden="true" class="se7en-doc"></span>帮助文档</a>
						</li>
					</ul>
				</div>
			</div>
</div>
<!-- End Navigation -->