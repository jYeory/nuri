<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		
<div class="page-header">
	<h1>공제계약사현황</h1>
</div><!-- /.page-header -->


<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->

		<div class="widget-box">
			<div class="widget-header">
				<h4>계약사정보</h4>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse">
						<i class="icon-chevron-up"></i>
					</a>

					<!-- a href="#" data-action="close">
						<i class="icon-remove"></i>
					</a -->
				</div>
			</div>

			<div class="widget-body">
				<div class="widget-main form">
					<form class="form-horizontal" role="form">

						<div class="form-group">
							<div class="col-sm-8">
								<label class="col-sm-2 formTitle">계약사</label>
								<div class="col-sm-10">
									<input type="text" class="formValueRead" value="서초어린이집"/>
								</div>
							</div>
							<div class="col-sm-4">
								<label class="col-sm-4 formTitle">가입일자</label>
								<div class="col-sm-8">
									<input type="text" class="formValueRead" value="2013.01.01" />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-8">
								<label class="col-sm-2 formTitle">대표자</label>
								<div class="col-sm-10">
									<input type="text" class="formValueRead" value="홍길동"/>
								</div>
							</div>
							<div class="col-sm-4">
								<label class="col-sm-4 formTitle">업등록번호</label>
								<div class="col-sm-8">
									<input type="text" class="formValueRead" value="123-123-1111" />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-8">
								<label class="col-sm-2 formTitle">사업자등록번호</label>
								<div class="col-sm-4">
									<input type="text" class="formValueRead" value="123-12-12345" />
								</div>
								<label class="col-sm-2 formTitle">법인등록번호</label>
								<div class="col-sm-4">
									<input type="text" class="formValueRead" value="123456-1234567" />
								</div>
							</div>
							<div class="col-sm-4">
								<label class="col-sm-4 formTitle">설립일자</label>
								<div class="col-sm-8">
									<input type="text" class="formValueRead" value="2013.01.01" />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-8">
								<label class="col-sm-2 formTitle">주소</label>
								<div class="col-sm-10">
									<input type="text" class="formValueRead" value="경남 통영시"/>
								</div>
							</div>
							<div class="col-sm-4">
								<label class="col-sm-4 formTitle">전화번호</label>
								<div class="col-sm-8">
									<input type="text" class="formValueRead" value="02-123-1111" />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-8">
								<label class="col-sm-2 formTitle">신용율</label>
								<div class="col-sm-4">
									<input type="text" class="formValueRead" value="80%" />
								</div>
								<label class="col-sm-2 formTitle">총출자금액</label>
								<div class="col-sm-4">
									<input type="text" class="formValueRead" value="" />
								</div>
							</div>
							<div class="col-sm-4">
								<label class="col-sm-4 formTitle">총담보금액</label>
								<div class="col-sm-8">
									<input type="text" class="formValueRead" value="" />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-8">
								<label class="col-sm-2 formTitle">담보금적용비율</label>
								<div class="col-sm-4">
									<input type="text" class="formValueRead" value="" />
								</div>
								<label class="col-sm-2 formTitle">출자담보제공비율</label>
								<div class="col-sm-4">
									<input type="text" class="formValueRead" value="" />
								</div>
							</div>
							<div class="col-sm-4">
								<label class="col-sm-4 formTitle">현금담보제공비율</label>
								<div class="col-sm-8">
									<input type="text" class="formValueRead" value="" />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-8">
								<label class="col-sm-2 formTitle">보증한도액</label>
								<div class="col-sm-4">
									<input type="text" class="formValueRead" value="999,999,999" />
								</div>
								<label class="col-sm-2 formTitle">누적선수금</label>
								<div class="col-sm-4">
									<input type="text" class="formValueRead" value="999,999,999" />
								</div>
							</div>
							<div class="col-sm-4">
								<label class="col-sm-4 formTitle">보증한도잔액</label>
								<div class="col-sm-8">
									<input type="text" class="formValueRead" value="999,999,999(30%)" />
								</div>
							</div>
						</div>
						
					</form>
				</div>
			</div>
		</div>
		
		<div class="space-20"></div>
		
		<div class="widget-box">
			<div class="widget-header">
				<h4>대표자현황</h4>

				<div class="widget-toolbar">
					<a href="#" data-action="collapse">
						<i class="icon-chevron-up"></i>
					</a>

					<!-- a href="#" data-action="close">
						<i class="icon-remove"></i>
					</a -->
				</div>
			</div>

			<div class="widget-body">
				<table id="grid-table"></table>
				<div id="grid-pager"></div>
			</div>
		</div>
		
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->