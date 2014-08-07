<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/include/common.jsp" %>
<html>
<head>
<link rel="stylesheet" href="${ctx}/js/bootstrap-assets/css/chosen.css" />
<link rel="stylesheet" href="${ctx}/js/jquery/plugin/jstree/themes/default/style.css" />
<link rel="stylesheet" href="${ctx}/js/dtree/dtree.css" />

<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/bootstrap-wysiwyg.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/bootbox.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/jquery.hotkeys.min.js"></script>

<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/fuelux/data/fuelux.tree-sampledata.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/fuelux/fuelux.tree.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/chosen.jquery.min.js"></script>

<script type="text/javascript" src="${ctx}/dwr/interface/RoleDwr.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.form-group{
	margin-right: 0px!important;
	margin-left: 0px!important;
}
div.modal-body{
	padding: 10px 20px 0px 20px!important;
}
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
						권한관리
						<small>
							<i class="icon-double-angle-right"></i>
							with selectable items(single &amp; multiple) and custom icons
						</small>
					</h4>
					<div class="hr hr-18 hr-double dotted"></div>
					<div class="col-sm-12">
						<div class="widget-header">
							<h4></h4>
							<div class="widget-toolbar"></div>
						</div>
						<div class="widget-body">
							<table id="roleGrid"></table>
							<div id="roleGrid-pager"></div>
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
	
	<!-- 등록/편집 모달 -->
	<div id="modal-form" class="modal" tabindex="-1">
		<div class="modal-dialog" style="width:400px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="blue bigger" id="modalTitle"></h4>
				</div>
	
				<div class="modal-body overflow-visible">
					<div class="row">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"> 메뉴 ID </label>
								<div class="col-sm-9">
									<input type="hidden" id="status"/>
									<input type="hidden" id="menuSeq"/>
									<input class="col-xs-12" type="text" id="menuId" value="자동입력" readonly="readonly"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">메뉴명</label>
								<div class="col-sm-9">
									<input class="col-xs-12" type="text" id="menuName" value="" onkeyup="javascript:fnCheckStrLength(this, 45)"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">상위 메뉴명</label>
								<div class="col-sm-9">
									<input class="col-xs-12" type="text" id="prtMenuName" name="menuCls" value="" maxlength="45" readonly="readonly"/>
									<input type="hidden" name="prtMenuId" id="prtMenuId" value=""/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">CSS 클래스</label>
								<div class="col-sm-9">
									<input class="col-xs-12" type="text" id="menuCls" name="menuCls" value="" onkeyup="javascript:fnCheckStrLength(this, 45)"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">URL</label>
								<div class="col-sm-9">
									<input class="col-xs-12" type="text" id="menuUrl" name="menuUrl" value="" onkeyup="javascript:fnCheckStrLength(this, 100)"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">설명</label>
								<div class="col-sm-9">
									<input class="col-xs-12" type="text" id="description" name="description" value="" onkeyup="javascript:fnCheckStrLength(this, 200)"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">권한</label>
								<div class="col-sm-9">
									<select multiple="" class="width-98 chosen-select" id="roles" data-placeholder="권한을 선택하세요.">
										<option value="AL">Alabama</option>
										<option value="AK">Alaska</option>
										<option value="AZ">Arizona</option>
										<option value="AR">Arkansas</option>
									</select>
								</div>
							</div> 
							
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right">정렬 순서</label>
								<div class="col-sm-9">
									<input class="col-xs-12" type="text" id="ords" name="ords" value="" maxlength="45"/>
								</div>
							</div>
							
						</div>
					</div>
				</div>
	
				<div class="modal-footer">
					<button class="btn btn-sm btn-primary" id="btnSave"><i class="icon-ok"></i>저장</button>
					<button class="btn btn-sm" data-dismiss="modal"><i class="icon-remove"></i>취소</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>