/**
 * this는 각각의 탭, argument로 mainTab의 객체가 넘어온다.
 */
var initEditor = false;
function activeEditor(){
	if(!initEditor){
		initEditor = !initEditor;
		$('#noticeEditor').ace_wysiwyg({
			toolbar:
				[
				 'font',
				 null,
				 'fontSize',
				 null,
				 {name:'bold', className:'btn-info'},
				 {name:'italic', className:'btn-info'},
				 {name:'strikethrough', className:'btn-info'},
				 {name:'underline', className:'btn-info'},
				 null,
				 {name:'justifyleft', className:'btn-primary'},
				 {name:'justifycenter', className:'btn-primary'},
				 {name:'justifyright', className:'btn-primary'},
				 {name:'justifyfull', className:'btn-inverse'},
				 null,
				 {name:'insertImage', className:'btn-success'}
				 ],
				 speech_button : false,
				 'wysiwyg': {
					 fileUploadError: showErrorAlert
				 }
		});
		$('#noticeEditor').prev().addClass('wysiwyg-style2');
	}
}

function activeFileAttach(){
	// file attach element
	$('#id-input-file-1 , #id-input-file-2').ace_file_input({
		no_file:'No File ...',
		btn_choose:'Choose',
		btn_change:'Change',
		droppable:false,
		onchange:null,
		thumbnail:false //| true | large
		//whitelist:'gif|png|jpg|jpeg'
		//blacklist:'exe|php'
		//onchange:''
		//
	});
}

var noticeHandler = {
		
	// 공지사항 등록 & 수정
	editNotice : function(){
		var cmd = $('input[name=cmd]').val();
		var noticeSeq = $('input[name=noticeSeq]').val();
		initEditor = false;
		activeEditor();
		activeFileAttach();
		
		$('#pageTitle').html( (cmd == 'edit')?'공지사항 편집':'공지사항 등록' );
		if(cmd == 'edit' && noticeSeq != ''){
			NoticeDwr.getNotice(noticeSeq, function(notice){
				$('#noticeEditor').html(notice.contents);
				$('#title').val(notice.title);
				// 첨부파일 없을 경우 '첨부된 파일' 감추고 '첨부하기' 표시
				if(Boolean(notice.hasAttach)){
					$('#attachedFile').show();
					$('#attachFile').hide();
					
					// 첨부파일 가져와 div에 넣자. 공통으로 할 수 있을거 같은데... 
					var params = { attachDocType : 'notice', attachDocKey : notice.noticeSeq };
					AttachmentDwr.attachList(params, function(attachList){
						if(attachList != null){
							$.each(attachList, function(key, attach){
								var button = $('<button>').attr({type:'button'}).addClass('close');
								button.append( $('<i>').addClass('icon-remove') );
								$('#fileList').append(button);
								var url = ctx+'/common/file/getFile.do?attachDocType=notice&attachDocKey='+notice.noticeSeq;
								var a = $('<a>').attr({href:url, target:'_blank' });
								a.append( $('<i>').addClass('icon-file').css({'padding-right' : '5px'}) );
								a.append( attach.filename + ' ('+Math.round(Number(attach.fileSize)/(1024*1024))+' MB)' );
								$('#fileList').append(a);
								var hr = $('<div>').addClass('hr hr-single dotted').css({'padding': '5 0 5 0'});
								$('#fileList').append(hr);
							});
							$('#fileList').find('div.hr-single').last().remove();
						}
					});
				}
				else{
					$('#attachedFile').hide();
					$('#attachFile').show();
					// 첨부파일 input box에 보이게..
//					$('#id-input-file-2').ace_file_input('show_file_list', ['file1.txt']);
				}
			});
		}
		
		$('#btnSave').click(function(){
			var html = $('#noticeEditor').html();
			var title = $('#title').val();
			if(title == ''){
				alert('제목을 입력하세요.');
				return false;
			}
			if(html == ''){
				alert('내용을 입력하세요.');
				return false;
			}
			
	        var notice = {};
			notice.title = title;
			notice.contents = html;
			notice.noticeType = 0;
			if(cmd == 'edit'){
				notice.noticeSeq = noticeSeq;
				NoticeDwr.updateNotice(notice, function(reesult){
					if(result.noticeSeq != null){
						fileupload(result);
					}
				});
			}
			else if(cmd == 'add'){
				NoticeDwr.addNotice(notice, function(result){
					if(result.noticeSeq != null){
						fileupload(result);
					}
				});
			}
			return false;
		});
		
		function fileupload(result){
			$('input[name=attachDocType]').val('notice');
			$('input[name=attachDocKey]').val(result.noticeSeq);
	        var data = new FormData(document.getElementById('noticeForm'));
			// 파일 업로드
			$.ajax({
				url: ctx+'/common/file/upload.do',
				type: "post",
				dataType: "text",
				data: data,
				// cache: false,
				processData: false,
				contentType: false,
				success: function(data, textStatus, jqXHR) {
					alert('저장 되었습니다.');
					movePage('noticePage', ctx+"/notice/noticePage.do");
				},
				error: function(jqXHR, textStatus, errorThrown) {}
			});
		}
		
		$('#btnCancel').bind({
			click : function(){
				movePage('noticePage', ctx+"/notice/noticeList.do");
				return false;
			}
		});
	},
	
	// 공지사항
	noticePage : function(){
		dwr.engine.beginBatch();
		var grid = jQuery("#noticeGrid").jqGrid({
			url : ctx+'/notice/getList.do',
		    data : {},
		    datatype: "json",
		    colNames: ["Seq", "구분", "제목", "작성자", "작성일", '조회수'],
		    colModel: [
		               { name: "noticeSeq", hidden:true },
		               { name: "noticeType", width: 30, align:'center', formatter : function(cellValue){ return cellValue=='0'?'일반':'단체'; } },
		               { name: "title", width: 100},
		               { name: "createBy", width: 50, align:'center' },
		               { name: 'createDate', width: 50, align:'center', index:'createDate', editable:true, formatter: dateFormatter},
		               { name: "readCnt", width: 20, align:'center' }
		    ],
		    jsonReader : {
	          	root: "pageSize",
	          	page: "currentPage",
	          	total: "totalCount",
	          	records: "dataList",
	          	repeatitems: false,
	          	cell: "cell",
	          	id: "noticeSeq"
	      	},
		    autowidth: true,
		    height: 400,
		    rowNum: 20,
		    rownumbers: true,
		    sortname: "noticeSeq",
		    sortorder: "asc",
		    scrollrows : true,
		    viewrecords: true,
		    gridview: true,
		    autoencode: true,
		    pager: '#noticeGrid-pager',
		    altRows: true,
		    loadComplete : function() {
				var table = this;
				setTimeout(function(){
					updatePagerIcons(table);
//					enableTooltips(table);
					jQuery.fn.gridResize('noticeGrid');
				}, 0);
			},
			onSelectRow: function(id){
	      		return;
	        }
		});
		dwr.engine.endBatch({timeout: 60000});
		
		jQuery("#noticeGrid").jqGrid('navGrid', '#noticeGrid-pager',
			{ 	//navbar options
				edit: false,
				editicon : 'icon-pencil blue',
				add: false,
				addicon : 'icon-plus-sign purple',
				del: false,
				delicon : 'icon-trash red',
				search: false,
				searchicon : 'icon-search orange',
				refresh: true,
				refreshicon : 'icon-refresh green',
				view: false,
				viewicon : 'icon-zoom-in grey',
			}
		);
		
		$('#btnAdd').bind({
			click : function(){
				movePage('editNotice', ctx+"/notice/noticeEdit.do", {cmd : 'add'});
			}
		});
		$('#btnEdit').bind({
			click : function(){
				var rowId = $("#noticeGrid").jqGrid('getGridParam', 'selrow');
				movePage('editNotice', ctx+"/notice/noticeEdit.do", {cmd : 'edit', noticeSeq : rowId});
			}
		});
		
		/*
		// 편집
		$('#btnEdit').bind({
			click : function(){
				var btnId = $(this).attr('id');
				var status = (btnId == 'btnEdit') ? 'edit' : 'add' ;
				
				$('#modal-form').modal('show');
				$('.modal-body #status').val(status);
				
				var title = '';
				var prtMenuName = null;
				var prtMenuId = null;
				
				if(status == 'edit'){
					title = '공지사항 편집 - ';
					$('.modal-header h4#modalTitle').text(title);
				}
				else if(status == 'add'){
					title = '공지사항 추가';
					$('.modal-header h4#modalTitle').text(title);
				}
			}
		});
		*/
		/*
		$('#modal-form').on('shown.bs.modal', function () {
			$(this).find('.chosen-container').each(function(){
				$(this).find('a:first-child').css('width' , '210px');
				$(this).find('.chosen-drop').css('width' , '210px');
				$(this).find('.chosen-search input').css('width' , '200px');
			});
		});
		*/
		$('#btnSave').click(function(){
			var html = $('#noticeEditor').html();
			var title = $('#noticeTitle').val();
			if(title == ''){
				alert('제목을 입력하세요.');
				return;
			}
			if(html == ''){
				alert('내용을 입력하세요.');
				return;
			}
			
			/*
			var notice = {};
			notice.title = title;
			notice.contents = html;
			notice.noticeType = 0;
			NoticeDwr.addNotice(notice, function(result){
				if(result){
					alert('추가 되었습니다.');
					$('#modal-form').modal('hide');
					jQuery("#noticeGrid").trigger('reloadGrid');
				}
			});
			*/
		});

		
		//Add Image Resize Functionality to Chrome and Safari
		//webkit browsers don't have image resize functionality when content is editable
		//so let's add something using jQuery UI resizable
		//another option would be opening a dialog for user to enter dimensions.
		if ( typeof jQuery.ui !== 'undefined' && /applewebkit/.test(navigator.userAgent.toLowerCase()) ) {
			
			var lastResizableImg = null;
			function destroyResizable() {
				if(lastResizableImg == null) return;
				lastResizableImg.resizable( "destroy" );
				lastResizableImg.removeData('resizable');
				lastResizableImg = null;
			}

			var enableImageResize = function() {
				$('.wysiwyg-editor')
				.on('mousedown', function(e) {
					var target = $(e.target);
					if( e.target instanceof HTMLImageElement ) {
						if( !target.data('resizable') ) {
							target.resizable({
								aspectRatio: e.target.width / e.target.height,
							});
							target.data('resizable', true);
							
							if( lastResizableImg != null ) {//disable previous resizable image
								lastResizableImg.resizable( "destroy" );
								lastResizableImg.removeData('resizable');
							}
							lastResizableImg = target;
						}
					}
				})
				.on('click', function(e) {
					if( lastResizableImg != null && !(e.target instanceof HTMLImageElement) ) {
						destroyResizable();
					}
				})
				.on('keydown', function() {
					destroyResizable();
				});
		    };
			
			enableImageResize();
		}
	}
};

menuHandler = $.extend(menuHandler, noticeHandler);