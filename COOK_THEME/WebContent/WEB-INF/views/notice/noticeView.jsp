<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/include/common.jsp" %>
<html>
<head>
<link rel="stylesheet" href="${ctx}/js/bootstrap-assets/css/chosen.css" />

<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/bootstrap-wysiwyg.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/bootbox.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/jquery.hotkeys.min.js"></script>
<script type="text/javascript" src="${ctx}/dwr/interface/NoticeDwr.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.widget-main{
	padding:0px !important;
}
h4, h5, h6 {
	margin-top: 10px;
	margin-bottom: -5px;
}
</style>
</head>

<body>
	<iframe name="hiddenFrame" id="hiddenFrame" style="display: none"></iframe>
	<div class="page-content">
		<div class="row">
			<div class="col-xs-12">
				<div style="border:1px solid #d6e1ea;" class="message-navbar align-center clearfix">
					<div class="pull-left">
						<i class="icon-inbox blue bigger-110 middle"></i>
						<b class="bigger-110 middle ng-binding"> 8. 클라우드 가상서버 마이그레이션 작업 안내입니다.</b>
						<!-- ngIf: sessUser.adminFlag == 'Y' -->
						<!-- ngIf: sessUser.adminFlag == 'Y' -->
					</div>
				</div>
				<div class="message-content">
					<div class="message-header clearfix">
						<div class="pull-left">
							<i class="icon-comment orange2 mark-star"></i>
							<span class="sender ng-binding"> admin </span>&nbsp;
							<i class="icon-time bigger-110 orange middle"></i>
							<span class="time grey ng-binding">2014-06-23</span>
						</div>
						<div class="pull-right">
							<button class="btn btn-minier" ng-click="moveMainNotice()">목록</button>
							<!-- ngIf: sessUser.adminFlag == 'Y' -->
							<!-- ngIf: sessUser.adminFlag == 'Y' -->
						</div>
					</div>
					<div class="hr hr-double"></div>
					<div class="message-body" id="view-content" style="width: 100%; white-space: pre-wrap;"><span class="ng-scope">
						안녕하세요. 퍼니오입니다.</span>
						<br class="ng-scope"><br class="ng-scope">
						<span class="ng-scope">2014-06-23 ~ 2014-06-29 기간동안 가상서버들의 마이그레이션 작업이 진행됩니다.</span>
						<br class="ng-scope"><br class="ng-scope">
						<span class="ng-scope">서버를 좀 더 효율적으로 운영하기 위하여 현재 운영중인 가상서버들을 부득이하게</span>
						<br class="ng-scope"><br class="ng-scope">
						<span class="ng-scope">마이그레이션을 해야 하니 양해 부탁드립니다.</span>
						<br class="ng-scope"><br class="ng-scope">
						<span class="ng-scope">가상서버별로 30분에서 1시간 정도의 시간이 소요되며, 고객분들께서는 특별히 작업하실</span>
						<br class="ng-scope"><br class="ng-scope">
						<span class="ng-scope">내용은 없습니다. </span>
						<br class="ng-scope"><br class="ng-scope">
						<span class="ng-scope">작업 기간동안 가상서버를 잠시 내려야 하니 1시간 이상 접속이 되지 않을 경우는 게시판으로</span>
						<br class="ng-scope"><br class="ng-scope">
						<span class="ng-scope">연락 부탁드립니다.</span>
						<br class="ng-scope"><br class="ng-scope">
						<span class="ng-scope">좀 더 안정적인 서비스를 위한 조치이오니 많은 양해 부탁드립니다.</span>
						<br class="ng-scope"><br class="ng-scope">
						<span class="ng-scope">퍼니오 드림</span></div>
					<!-- PAGE CONTENT BEGINS -->
					<div id="notice-update-form" class="message-navbar hide" style="margin-top: 5px;">
						<form class="form-horizontal ng-pristine ng-valid">
							<div class="form-group">
								<div class="col-xs-12 col-sm-12">
									<div class="clearfix">
										<i class="icon-circle-blank"></i> VIEW
										<input type="checkbox" ng-model="notice.noticeViewFlag" id="noticeViewFlag" name="noticeViewFlag" class="ng-pristine ng-valid">
										&nbsp;&nbsp;&nbsp;&nbsp;
										<i class="icon-circle-blank"></i> IDX-VIEW
										<input type="checkbox" ng-model="notice.indexViewFlag" id="indexViewFlag" name="indexViewFlag" class="ng-pristine ng-valid">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12 col-sm-12">
									<div class="clearfix">
										<i class="icon-edit red"></i> 제목
										<input placeholder=" 문의 제목을 입력하세요!" type="text" maxlength="50" ng-model="notice.title" id="title" name="title" class="form-control ng-pristine ng-valid">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12 col-sm-12">
									<div class="clearfix">
										<i class="icon-edit red"></i> 내용
										<textarea placeholder="문의 내용을 입력하세요!" ng-model="notice.content" id="content" name="content" class="form-control ng-pristine ng-valid" style="height: 240px;"></textarea>
									</div>
								</div>
							</div>
						</form>
						<div class="modal-footer center">
							<button class="width-20 btn btn-sm" data-dismiss="modal" ng-click="cancelUpdateNotice(noticeUid)">
								<i class="icon-refresh"></i>
								초기화
							</button>
							<button class="width-20 btn btn-sm btn-primary" ng-click="updateNotice(noticeUid)">
								수&nbsp;&nbsp;&nbsp;&nbsp;정
								<i class="icon-arrow-right icon-on-right"></i>
							</button>
						</div>
					</div><!-- PAGE CONTENT ENDS -->
				</div>
			</div>
		</div>
	</div><!-- /.page-content -->
</body>
</html>