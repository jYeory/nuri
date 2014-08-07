<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/include/common.jsp"%>
<%@ include file="/include/scripts.jsp"%>
<%@ include file="/include/styles.jsp"%>
<%
String status = (String) request.getParameter("auth");
%>
<c:set var="status" value="<%= status %>" />
<link rel="stylesheet" href="${ctx}/js/bootstrap-assets/css/font-awesome.min.css" />
<%-- <script type="text/javascript" src="${ctx}/dwr/interface/UserDwr.js"></script> --%>
<style type="text/css">
input[type="email"] {
	-webkit-ime-mode:disabled !important;
	-moz-ime-mode:disabled !important;
	-ms-ime-mode:disabled !important;
	ime-mode:disabled !important;
}
span.placeholder
{
	position:absolute;
	top:4px;
	left:20px;
	z-index:2;
}

/* 웹킷 전용 속성 */
::-webkit-input-placeholder
{
	color:blue;
} 
/* 파이어폭스 전용 속성 */
textarea:-moz-placeholder, input:-moz-placeholder
{
	color:blue;
	
} 

</style>
<script type="text/javascript">
	jQuery(document).ready(function(){
		if('${status}' == 'fail'){
			alert('입력하신 정보가 존재하지 않습니다.');
		}
		
		$(function() {
			 $('input').placeholder();
		});
	});
	
	function show_box(id) {
		jQuery('.widget-box.visible').removeClass('visible');
		jQuery('#'+id).addClass('visible');
	}
	
	function doLogin(){
		var form = document.loginForm;
// 		form.action = ctx+"/doLogin";
		form.submit();
	}
	
	function doRegister(){
		var userId = $('#userId').val();
		var userName = $('#userName').val();
		var nickname = $('#nickname').val();
		
		if(userId == '' || userId.length == 0){
			alert('이메일을 입력해주세요.');
			$('#userId').focus();
			return;
		}
		if(userName == '' || userName.length == 0){
			alert('이름을 입력해주세요.');
			$('#userName').focus();
			return;
		}
		if(nickname == '' || nickname.length == 0){
			alert('별명을 입력해주세요.');
			$('#nickname').focus();
			return;
		}
		if($('#passwd').val() == '' || $('#passwd').val().length == 0){
			alert('비밀번호를 입력해주세요.');
			$('#passwd').focus();
			return;
		}
		if($('#passwd').val().length < 7){
			alert('비밀번호는 8자리 이상으로 입력해주세요.');
			$('#passwd').focus();
			return;
		}
		if($('#rptPasswd').val() == '' || $('#rptPasswd').val().length == 0){
			alert('비밀번호를 다시 입력해주세요.');
			$('#rptPasswd').focus();
			return;
		}
		if($('#passwd').val() != $('#rptPasswd').val()){
			alert('비밀번호를 동일하게 입력해주세요.');
			return;
		}
		
		// 유효성 검사 부터...
			
		// email 중복 검사
		$.ajax({
			url: "<c:url value='/user/isDuplication.do'/>",
			data : {
				userId : userId
			},
			success : function (data, textStatus, XMLHttpRequest) {
				if(Boolean(Number(data))){
					var form = document.registerForm;
					form.action = "<c:url value='/user/register.do'/>";
					form.submit();
				}
				else{
					alert('중복된 이메일입니다.');
				}
			},
			error : function (XMLHttpRequest, textStatus, errorThrown) {
				$(tabId, myLayout.panes.center).html(XMLHttpRequest.responseText);
			}
		});
		
	}
</script>
<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i>
									<%--
									<span class="red">Ace</span>
									<span class="white">Application</span>
									 --%>
									 <span class="white">가온누리  음식 봉사단</span>
								</h1>
								<%--
								<h4 class="blue">&copy; 가온누리</h4>
								 --%>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>
												아래 정보를 입력해 주세요.
											</h4>

											<div class="space-6"></div>

											<form id="loginForm" name="loginForm" action="${pageContext.request.contextPath}/doLogin" method="post">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<%-- id : test@yeory.com --%>
															<input type="text" name="j_username" class="form-control" placeholder="Your email address" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<%-- pass : test123 --%>
															<input type="password" name="j_password" class="form-control" placeholder="Password" />
															<i class="icon-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
													<%--
														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl"> Remember Me</span>
														</label>
													 --%>

														<button type="button" class="width-35 pull-right btn btn-sm btn-primary" id="btnLogin" onclick="doLogin()">
															<i class="icon-key"></i>
															Login
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /widget-main -->

										<div class="toolbar clearfix">
											<div>
												<a href="#" onclick="show_box('forgot-box'); return false;" class="forgot-password-link">
													<i class="icon-arrow-left"></i>
													I forgot my password
												</a>
											</div>

											<div>
												<a href="#" onclick="show_box('signup-box'); return false;" class="user-signup-link">
													회원가입
													<i class="icon-arrow-right"></i>
												</a>
											</div>
										</div>
									</div><!-- /widget-body -->
								</div><!-- /login-box -->

								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="icon-key"></i>
												Retrieve Password
											</h4>

											<div class="space-6"></div>
											<p>
												Enter your email and to receive instructions
											</p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i>
														</span>
													</label>

													<div class="clearfix">
														<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="icon-lightbulb"></i>
															Send Me!
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /widget-main -->

										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
												로그인으로 돌아가기
												<i class="icon-arrow-right"></i>
											</a>
										</div>
									</div><!-- /widget-body -->
								</div><!-- /forgot-box -->
								
								<%-- 회원가입 폼 시작 --%>
								<div id="signup-box" class="signup-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="icon-group blue"></i>
												New User Registration
											</h4>

											<div class="space-6"></div>
											<p> Enter your details to begin: </p>

											<form name="registerForm" method="post">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" onkeyup="chk_byte(this, 80);" placeholder="Email" id="userId" name="userId" />
															<i class="icon-envelope"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" onkeyup="chk_byte(this, 40);" placeholder="Username" id="userName" name="userName" />
															<i class="icon-user"></i>
														</span>
													</label>
													
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" onkeyup="chk_byte(this, 40);" placeholder="nickname" id="nickname" name="nickname" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" onkeyup="chk_byte(this, 80);" placeholder="Password" id="passwd" name="passwd"/>
															<i class="icon-lock"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" onkeyup="chk_byte(this, 80);" placeholder="Repeat password" id="rptPasswd"/>
															<i class="icon-retweet"></i>
														</span>
													</label>
													<%-- 
													<label class="block">
														<input type="checkbox" class="ace" />
														<span class="lbl">
															I accept the
															<a href="#">User Agreement</a>
														</span>
													</label>
													--%>
													<div class="space-24"></div>

													<div class="clearfix">
														<button type="reset" onclick="doReset()" class="width-30 pull-left btn btn-sm">
															<i class="icon-refresh"></i>
															초기화
														</button>

														<button type="button" onclick="doRegister()" class="width-65 pull-right btn btn-sm btn-success">
															가입하기
															<i class="icon-arrow-right icon-on-right"></i>
														</button>
													</div>
												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
												<i class="icon-arrow-left"></i>
												로그인으로 돌아가기
											</a>
										</div>
									</div><!-- /widget-body -->
								</div><!-- /signup-box -->
							</div><!-- /position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->
		
	</body>
</html>