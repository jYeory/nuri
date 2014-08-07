/*
$('#btnAdd').click(function(){
	$( "#modal-form" ).dialog({
		width: 800,
		height: 530,
		resizable: false,
		modal: true,
		title: "<div class='widget-header'><h4 class='smaller'>공지사항 등록</h4></div>",
		title_html: true,
		open : function(){
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
				$('#noticeEditor').prev().addClass('wysiwyg-style1');
			}
			
		},
		buttons: [
			{
				html: "<i class='icon-trash bigger-110'></i>&nbsp; Delete all items",
				"class" : "btn btn-danger btn-xs",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
			,
			{
				html: "<i class='icon-remove bigger-110'></i>&nbsp; Cancel",
				"class" : "btn btn-xs",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
});
 */