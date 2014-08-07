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

<script type="text/javascript" src="${ctx}/dwr/interface/MenuDwr.js"></script>
<script type="text/javascript" src="${ctx}/dwr/interface/RoleDwr.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/plugin/jstree/jstree.js"></script>

<%-- <script type="text/javascript" src="${ctx}/js/dtree/dtree.js"></script> --%>
<%-- <link rel="stylesheet" href="${ctx}/js/dtree/dtree.css" /> --%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.form-group{
	margin-right: 0px!important;
	margin-left: 0px!important;
}
div.modal-body{
	padding: 10px 20px 0px 20px!important;
}
</style>
</head>

<body>
	<div class="page-content">
		<div class="page-header">
			<h1>
				메뉴관리
				<small>
					<i class="icon-double-angle-right"></i>
					with selectable items(single &amp; multiple) and custom icons
				</small>
			</h1>
		</div><!-- /.page-header -->
	
		<div class="row">
			<div class="widget-box transparent invoice-box">
				<div class="widget-header widget-header-small">
	
					<div class="widget-toolbar hidden-480">
						<a href="#" id="btnAddRoot" class="btn btn-minier btn-primary" data-toggle="modal">루트에 추가</a>
						<a href="#" id="btnAdd" class="btn btn-minier btn-primary" data-toggle="modal">추가</a>
						<a href="#" id="btnEdit" class="btn btn-minier btn-primary" data-toggle="modal">편집</a>
						<button id="btnDelete" class="btn btn-minier btn-primary">삭제</button>
					</div>
				</div>
			</div>
											
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
	
				<div class="row">
				
					<div class="col-sm-6">
						<div class="widget-box">
							<div class="widget-header">
								<h4>메뉴 계층 구조</h4>
							</div>
				
							<div class="widget-body">
								<div class="widget-main padding-8">
									<div id="menuTree" class="tree"></div>
								</div>
							</div>
						</div>
					</div>
	
					<div class="col-sm-6">
						<div class="widget-box">
							<div class="widget-header">
								<h4 class="lighter smaller">메뉴정보</h4>
							</div>
	
							<div class="widget-body">
								<div class="widget-main no-padding">
									<!-- PAGE CONTENT BEGINS -->
										<form class="form-horizontal" role="form">
											<input type="hidden" name="menuSeq" id="menuSeq" value="" />
											<div class="form-group">
												<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 메뉴 ID </label>
												<div class="col-sm-9">
													<input type="text" id="menuId" name="menuId" class="col-xs-10 col-sm-5" value="" readonly="readonly" />
													<input type="hidden" name="menuSeq" id="menuSeq" value=""/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 메뉴명 </label>
												<div class="col-sm-9">
													<input type="text" id="menuName" name="menuName" class="col-xs-10 col-sm-5" value="" readonly="readonly" />
												</div>
											</div>
		
											<div class="form-group">
												<label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 상위 메뉴명 </label>
												<div class="col-sm-9">
													<input type="text" id="prtMenuName" name="prtMenuName" class="col-xs-10 col-sm-5" readonly="readonly" />
													<input type="hidden" name="prtMenuId" id="prtMenuId" value=""/>
													<!-- 
													<span class="help-inline col-xs-12 col-sm-7"> <span class="middle">Inline help text</span> </span>
													 -->
												</div>
											</div>
		
											<div class="form-group">
												<label class="col-sm-3 control-label no-padding-right" for="form-field-4"> CSS 클래스 </label>
												<div class="col-sm-9">
													<input type="text" id="menuCls" name="menuCls" class="col-xs-10 col-sm-11" value="" readonly="readonly" />
												</div>
											</div>
											
											<div class="form-group">
												<label class="col-sm-3 control-label no-padding-right" for="form-field-5"> URL </label>
												<div class="col-sm-9">
													<input type="text" id="menuUrl" name="menuUrl" class="col-xs-10 col-sm-11" value="" readonly="readonly" />
												</div>
											</div>
		
											<div class="form-group">
												<label class="col-sm-3 control-label no-padding-right" for="form-field-6"> 설명 </label>
												<div class="col-sm-9">
													<input type="text" id="description" name="description" class="col-xs-10 col-sm-11" value="" readonly="readonly" />
												</div>
											</div>
		
											<div class="form-group">
												<label class="col-sm-3 control-label no-padding-right" for="form-field-7"> 권한 </label>
												<div class="col-sm-9">
													<input type="text" id="descpription" name="descpription" class="col-xs-10 col-sm-11" value="" readonly="readonly" />
												</div>
											</div>
		
											<div class="form-group">
												<label class="col-sm-3 control-label no-padding-right" for="form-field-8">정렬 순서</label>
		
												<div class="col-sm-9">
													<div class="clearfix">
														<input type="text" id="ords" name="ords" class="col-xs-1 col-sm-1" value="" readonly="readonly" />
													</div>
												</div>
											</div>
											<%-- 
											<div class="clearfix form-actions">
												<div class="col-md-offset-3 col-md-9">
													<button class="btn btn-info" id="btnSaveMenu" type="button">
														<i class="icon-ok bigger-110"></i>
														저장하기
													</button>
													&nbsp; &nbsp; &nbsp;
													<button class="btn" type="reset">
														<i class="icon-undo bigger-110"></i>
														Reset
													</button>
												</div>
											</div>
											--%>
										</form>
								</div>
							</div>
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
									<select multiple="" class="width-98 chosen-select" id="roles" name="roles" data-placeholder="권한을 선택하세요.">
									<%--
										<option value="AL">Alabama</option>
										<option value="AK">Alaska</option>
										<option value="AZ">Arizona</option>
										<option value="AR">Arkansas</option>
									 --%>
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