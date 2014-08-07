<%@page import="com.nuri.common.utils.JSONUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>가온누리 정보 공개 페이지</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<style type="text/css">
</style>
<!-- inline styles related to this page -->
<%@ include file="/include/common.jsp"%>
<%@ include file="/include/styles.jsp"%>
<%@ include file="/include/scripts.jsp"%>
<%
ArrayList<HashMap<String, Object>> menus = (ArrayList<HashMap<String, Object>>) request.getAttribute("menus");
String jsonStr = JSONUtil.toJSON(menus, null);
%>
<script type="text/javascript">
	var ctx = "<%= request.getContextPath()%>";
	var jsonStr = $.parseJSON( '<%= jsonStr %>' );
	var prevMenuId = '';
	var MENU = {};		// 전체 메뉴
	var treeMenu = {};	// 계층화된 메뉴
	var obj = null;
	var children = {};
	
	function createSubMenu(menu){
		var ul = $('<ul>').addClass('submenu');
		var obj = children[menu.menu_id];
		var li = $('<li>');
		$.each(obj, function(idx, m){
			if(m.menu_id in children){
				li.append( createLi(m) );
				li.append( createSubMenu(m) );
				ul.append(li);
			}
			else{
				ul.append( $('<li>').append( createLi(m) ) );
			}
		});
		return ul;;
	}

	function createLi(menu){
		var a = null;
		if(menu.menu_id in children){
			a = $('<a>').attr({href : '#'}).addClass('dropdown-toggle');
			a.append( $('<i>').addClass(menu.menu_cls) );
			a.append( $('<span>').addClass('menu-text').text( menu.menu_name ) );
			a.append( $('<b>').addClass('arrow icon-angle-down') );
		}
		else{
			a = $('<a>').attr({href : ctx + menu.menu_url, id : menu.menu_func, title : menu.menu_name});
			a.append( $('<i>').addClass(menu.menu_cls) );
			a.append( $('<span>').addClass('menu-text').text( menu.menu_name ) );
		}
		return a;
	}
	
	jQuery(document).ready(function(){
		movePage('dashBoardPage', ctx+"/gaon/dashboard.do");
		1
		// 전 메뉴 id를 key로 하는 object로 변환
		$.each(jsonStr, function(idx, m){
			m.children = [];
			MENU[m.menu_id] = m;
		});
		
		$.each(MENU, function(key, m){
			if(m.prt_menu_id == '#'){
				treeMenu[m.menu_id] = m;
				delete MENU[m.menu_id];
			}
		});
		
		$.each(MENU, function(key, m){
			if(children[m.prt_menu_id] == undefined ){
				children[m.prt_menu_id] = [];
			}
			obj = children[m.prt_menu_id];
			obj.push(m);;
		});
		
		var i = 0;
		var leftMenu = $('ul#leftMenu');
		$.each(treeMenu, function(k, m){
			var li = $('<li>');
			if(i == 0) li.addClass('active');
			
			if(m.menu_id in children){
				li.append( createLi(m) );
				li.append( createSubMenu(m) );
			}
			else{
				li.append( createLi(m) );
			}
			leftMenu.append(li);
			i++;
		});
	});
	
</script>
</head>
<body>
	<div class="navbar navbar-default" id="navbar">
		<script type="text/javascript">
			try{ace.settings.check('navbar' , 'fixed')}catch(e){}
		</script>

		<div class="navbar-container" id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="${ctx}/gaon/main.do" class="navbar-brand">
					<small>
						<i class="icon-leaf"></i>
						가온누리 정보공개 사이트
					</small>
				</a><!-- /.brand -->
			</div><!-- /.navbar-header -->

			<div class="navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">
					<sec:authorize access="isAnonymous()">
						<li class="green">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#" id="btnLogin">로그인</a>
						</li>
					</sec:authorize>
					
					<sec:authorize access="isAuthenticated()">
						<%-- 로그인 시 보여줄 유저 정보 --%>
						<li class="light-blue">
							
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="${ctx}/js/bootstrap-assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small>Welcome,</small>
									Jason
								</span>
	
								<i class="icon-caret-down"></i>
							</a>
	
							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="#">
										<i class="icon-cog"></i>
										Settings
									</a>
								</li>
	
								<li>
									<a href="#">
										<i class="icon-user"></i>
										Profile
									</a>
								</li>
	
								<li class="divider"></li>
	
								<li>
									<a href="${ctx}/home/logout.do">
										<i class="icon-off"></i>
										Logout
									</a>
								</li>
							</ul>
						</li>
					</sec:authorize>
				</ul><!-- /.ace-nav -->
			</div><!-- /.navbar-header -->
		</div><!-- /.container -->
	</div>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#">
				<span class="menu-text"></span>
			</a>

			<div class="sidebar" id="sidebar">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="icon-signal"></i>
						</button>

						<button class="btn btn-info">
							<i class="icon-pencil"></i>
						</button>

						<button class="btn btn-warning">
							<i class="icon-group"></i>
						</button>

						<button class="btn btn-danger">
							<i class="icon-cogs"></i>
						</button>
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div><!-- #sidebar-shortcuts -->
				
				<%-- 메뉴 시작 --%>
				<ul class="nav nav-list" id="leftMenu">
				
					<%--
					<li>
						<a href="#" class="dropdown-toggle">
							<i class="icon-list"></i>
							<span class="menu-text"> Tables </span>

							<b class="arrow icon-angle-down"></b>
						</a>

						<ul class="submenu">
							<li>
								<a href="tables.html">
									<i class="icon-double-angle-right"></i>
									Simple &amp; Dynamic
								</a>
							</li>

							<li>
								<a href="jqgrid.html">
									<i class="icon-double-angle-right"></i>
									jqGrid plugin
								</a>
							</li>
						</ul>
					</li>
					 --%>

				</ul><!-- /.nav-list -->

				<div class="sidebar-collapse" id="sidebar-collapse">
					<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
				</div>

				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>
			
			<%-- 컨텐츠 페이지 상단 메뉴 타이틀 바 --%>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>
					
					<ul class="breadcrumb">
						<li>
							<i class="icon-home home-icon"></i>
							<a href="${ctx}/gaon/main.do">Home</a>
						</li>
						<li class="active">Dashboard</li>
					</ul><!-- .breadcrumb -->

					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon">
								<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
								<i class="icon-search nav-search-icon"></i>
							</span>
						</form>
					</div><!-- #nav-search -->
				</div>
				
				<%-- 컨텐츠 페이지 --%>
				<div id="runtimeContentsArea" class="page-content">
					
				</div><!-- /.page-content -->
				
			</div><!-- /.main-content -->
			
		</div><!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div><!-- /.main-container -->
</body>
</html>