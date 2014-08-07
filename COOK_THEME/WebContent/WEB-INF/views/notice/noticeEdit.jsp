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
<script type="text/javascript" src="${ctx}/dwr/interface/AttachmentDwr.js"></script>

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
	<form name='fileForm' method="post">
		<input type='hidden' name='attachDocType'/>
		<input type='hidden' name='attachDocKey'/>
	</form>
	<div class="page-content">
		<div class="row">
			<div class="col-xs-12">
				<h4 class="lighter">
					<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
					<span id="pageTitle">공지사항 등록</span>
					<small>
						<i class="icon-double-angle-right"></i>
						with selectable items(single &amp; multiple) and custom icons
					</small>
				</h4>
				<div class="hr hr-18 hr-double dotted"></div>
				<div class="message-navbar">
					<form class="form-horizontal" role="form" id="noticeForm" name="noticeForm" method="post">
						<input type="hidden" name="attachDocType" value="" />
						<input type="hidden" name="attachDocKey" value="" />
						<input type="hidden" name="cmd" value="${cmd}" />
						<input type="hidden" name="noticeSeq" value="${noticeSeq}" />
						<div class="form-group">
							<label for="form-field-username">제목</label>
							<div>
								<input class="col-xs-12" type="text" id="title" name="title" placeholder="Title" value="" />
							</div>
						</div>
						<div class="form-group">
							<div>
								<div class="wysiwyg-editor" id="noticeEditor"></div>
								<input type="hidden" name="contents"/>
							</div>
						</div>
						<div class="form-group" id="attachedFile">
							<label for="form-field-username">첨부된 파일</label>
							<div class="alert alert-block alert-success" style="padding: 5px;" id="fileList">
								<%-- 
								<button type="button" class="close">
									<i class="icon-remove"></i>
								</button>
								TEST1
								<div class="hr hr-single dotted"></div>
								<button type="button" class="close">
									<i class="icon-remove"></i>
								</button>
								TEST
								--%>
							</div>
						</div>
						<div class="form-group" id="attachFile">
							<label for="form-field-username">첨부하기</label>
							<div class="widget-main">
								<input type="file" id="id-input-file-2" name="noticeAttachFile" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-1 col-md-9" style="text-align: center;">
								<button class="btn btn-info" id="btnSave"><i class="icon-ok bigger-110"></i>등록</button>
								&nbsp; &nbsp; &nbsp;
								<button class="btn " data-dismiss="modal" id="btnCancel"><i class="icon-remove bigger-110"></i>취소</button>
							</div>
						</div>
						<%--
						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button class="btn btn-info" type="button"><i class="icon-ok bigger-110"></i>Submit</button>
								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="reset"><i class="icon-undo bigger-110"></i>Reset</button>
							</div>
						</div>
						--%>
					</form>
				</div>
			</div>
		</div>
	</div><!-- /.page-content -->
</body>
</html>