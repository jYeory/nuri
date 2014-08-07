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
.widget-toolbar {
	line-height: 0px !important;
}
h4, h5, h6 {
	margin-top: 10px;
	margin-bottom: -5px;
}
</style>
</head>

<body>
	<div class="page-content">
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<div class="row">
					<h4 class="lighter">
						<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
						공지사항
						<small>
							<i class="icon-double-angle-right"></i>
							with selectable items(single &amp; multiple) and custom icons
						</small>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
						<div class="widget-toolbar hidden-480">
							<a href="#" id="btnAdd" class="btn btn-minier btn-primary" data-toggle="modal">추가</a>
							<a href="#" id="btnEdit" class="btn btn-minier btn-primary" data-toggle="modal">편집</a>
							<button id="btnDelete" class="btn btn-minier btn-primary">삭제</button>
						</div>
						</sec:authorize>
					</h4>
					<div class="hr hr-18 hr-double dotted"></div>
					<div class="col-sm-12">
						<div class="widget-header">
							<h4></h4>
							<div class="widget-toolbar"></div>
						</div>
						<div class="widget-body">
							<table id="noticeGrid"></table>
							<div id="noticeGrid-pager"></div>
						</div>
					</div>
				</div>
	
				<script type="text/javascript">
					var $assets = "assets";//this will be used in fuelux.tree-sampledata.js
				</script>
	
				<!-- PAGE CONTENT ENDS -->
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div><!-- /.page-content -->
	
</body>
</html>