var $path_base = ctx;//this will be used in gritter alerts containing images

function showErrorAlert (reason, detail) {
	var msg='';
	if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
	else {
//		console.log("error uploading file", reason, detail);
	}
	$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
}

//switch element when editing inline
function aceSwitch( cellvalue, options, cell ) {
	setTimeout(function(){
		$(cell) .find('input[type=checkbox]').wrap('<label class="inline" />').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
	}, 0);
}
//enable datepicker
function pickDate( cellvalue, options, cell ) {
	setTimeout(function(){
		$(cell).find('input[type=text]').datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
	}, 0);
}

function style_edit_form(form) {
	//enable datepicker on "sdate" field and switches for "stock" field
	form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
		.end().find('input[name=stock]')
			  .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

	//update buttons classes
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
	buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
	buttons.eq(1).prepend('<i class="icon-remove"></i>');
	
	buttons = form.next().find('.navButton a');
	buttons.find('.ui-icon').remove();
	buttons.eq(0).append('<i class="icon-chevron-left"></i>');
	buttons.eq(1).append('<i class="icon-chevron-right"></i>');		
}

function style_delete_form(form) {
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
	buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
	buttons.eq(1).prepend('<i class="icon-remove"></i>');
}

function style_search_filters(form) {
	form.find('.delete-rule').val('X');
	form.find('.add-rule').addClass('btn btn-xs btn-primary');
	form.find('.add-group').addClass('btn btn-xs btn-success');
	form.find('.delete-group').addClass('btn btn-xs btn-danger');
}
function style_search_form(form) {
	var dialog = form.closest('.ui-jqdialog');
	var buttons = dialog.find('.EditTable');
	buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
	buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
	buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
}

function beforeDeleteCallback(e) {
	var form = $(e[0]);
	if(form.data('styled')) return false;
	
	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
	style_delete_form(form);
	
	form.data('styled', true);
}

function beforeEditCallback(e) {
	var form = $(e[0]);
	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
	style_edit_form(form);
}



//it causes some flicker when reloading or navigating grid
//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
//or go back to default browser checkbox styles for the grid
function styleCheckbox(table) {
/**
	$(table).find('input:checkbox').addClass('ace')
	.wrap('<label />')
	.after('<span class="lbl align-top" />')


	$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
	.find('input.cbox[type=checkbox]').addClass('ace')
	.wrap('<label />').after('<span class="lbl align-top" />');
*/
}


//unlike navButtons icons, action icons in rows seem to be hard-coded
//you can change them like this in here if you want
function updateActionIcons(table) {
	/**
	var replacement = 
	{
		'ui-icon-pencil' : 'icon-pencil blue',
		'ui-icon-trash' : 'icon-trash red',
		'ui-icon-disk' : 'icon-ok green',
		'ui-icon-cancel' : 'icon-remove red'
	};
	$(table).find('.ui-pg-div span.ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
	*/
}

//replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
		'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
		'ui-icon-seek-next' : 'icon-angle-right bigger-140',
		'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	});
}

function enableTooltips(table) {
	$('.navtable .ui-pg-button').tooltip({container:'body'});
	$(table).find('.ui-pg-div').tooltip({container:'body'});
}

function dateFormatter(cellValue, options){
	if(cellValue) {
        return $.fmatter.util.DateFormat(
            '', 
            new Date(+cellValue), 
            'Y-m-d H:i:s',
            $.extend({}, $.jgrid.formatter.date, options)
        );
    } else {
        return '';
    }
}

var menuHandler = {
	dashBoardPage : function(){
		var _timer = null;
		$(window).resize(function(){
			if( _timer != null ){
				clearTimeout(_timer);
				_timer = null;
			}
			_timer = setTimeout(function(){
	          	var gridId = 'grid-table';
	          	var gridParentWidth = $('#gbox_' + gridId).parent().width();
	          	$('#' + gridId).jqGrid('setGridWidth', gridParentWidth);
			}, 300);
		});
	
		var grid_data = 
			[ 
				{id:"1",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
				{id:"2",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
				{id:"3",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
				{id:"4",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"},
				{id:"5",name:"Laser Printer",note:"note2",stock:"Yes",ship:"FedEx",sdate:"2007-12-03"},
				{id:"6",name:"Play Station",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
				{id:"7",name:"Mobile Telephone",note:"note",stock:"Yes",ship:"ARAMEX",sdate:"2007-12-03"},
				{id:"8",name:"Server",note:"note2",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
				{id:"9",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
				{id:"10",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
				{id:"11",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
				{id:"12",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
				{id:"13",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"},
				{id:"14",name:"Laser Printer",note:"note2",stock:"Yes",ship:"FedEx",sdate:"2007-12-03"},
				{id:"15",name:"Play Station",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
				{id:"16",name:"Mobile Telephone",note:"note",stock:"Yes",ship:"ARAMEX",sdate:"2007-12-03"},
				{id:"17",name:"Server",note:"note2",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
				{id:"18",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
				{id:"19",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
				{id:"20",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
				{id:"21",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
				{id:"22",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
				{id:"23",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"}
			];	
			
		var grid_selector = "#grid-table";
		var pager_selector = "#grid-pager";
		jQuery(grid_selector).jqGrid({
			//direction: "rtl",
			
			data: grid_data,
			datatype: "local",
			height: 200,
			colNames:[' ', 'NO','성명','생년월일','성별','위임일','주대표'],
			colModel:[
				{name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,
					formatter:'actions', 
					formatoptions:{ 
						keys:true,
						
						delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
						//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
					}
				},
				{name:'id',index:'id', width:60, sorttype:"int", editable: true},
				{name:'name',index:'name', width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
				{name:'sdate',index:'sdate',width:90, editable:true, sorttype:"date",unformat: pickDate},
				{name:'ship',index:'ship', width:90, editable: true,edittype:"select",editoptions:{value:"FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX"}},
				{name:'note',index:'note', width:150, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}}, 
				{name:'stock',index:'stock', width:70, editable: true,edittype:"checkbox",editoptions: {value:"Yes:No"},unformat: aceSwitch}
			], 
	
			viewrecords : true,
			rowNum:10,
			rowList:[10,20,30],
			pager : pager_selector,
			altRows: true,
			//toppager: true,
			
			multiselect: true,
			//multikey: "ctrlKey",
	        multiboxonly: true,
	
			loadComplete : function() {
				var table = this;
				setTimeout(function(){
					styleCheckbox(table);
					
					updateActionIcons(table);
					updatePagerIcons(table);
					enableTooltips(table);
				}, 0);
			},
	
			editurl: $path_base+"/dummy.html",//nothing is saved
			//caption: "대표자현황",
	
	
			autowidth: true
	
		});
		
		//enable search/filter toolbar
		//jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
	
		//navButtons
		jQuery(grid_selector).jqGrid('navGrid', pager_selector,
			{ 	//navbar options
				edit: true,
				editicon : 'icon-pencil blue',
				add: true,
				addicon : 'icon-plus-sign purple',
				del: false,
				delicon : 'icon-trash red',
				search: true,
				searchicon : 'icon-search orange',
				refresh: true,
				refreshicon : 'icon-refresh green',
				view: false,
				viewicon : 'icon-zoom-in grey',
			},
			{
				//edit record form
				//closeAfterEdit: true,
				recreateForm: true,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
					style_edit_form(form);
				}
			},
			{
				//new record form
				closeAfterAdd: true,
				recreateForm: true,
				viewPagerButtons: false,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
					style_edit_form(form);
				}
			},
			{
				//delete record form
				recreateForm: true,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					if(form.data('styled')) return false;
					
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
					style_delete_form(form);
					
					form.data('styled', true);
				},
				onClick : function(e) {
					alert(1);
				}
			},
			{
				//search form
				recreateForm: true,
				afterShowSearch: function(e){
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />');
					style_search_form(form);
				},
				afterRedraw: function(){
					style_search_filters($(this));
				}
				,
				multipleSearch: true,
				/**
				multipleGroup:true,
				showQuery: true
				*/
			},
			{
				//view record form
				recreateForm: true,
				beforeShowForm: function(e){
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />');
				}
			}
		);
	
		//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
		
		$('#btnLogin').click(function(){
			window.location.href = ctx+"/login/loginPage.do";
		});
	},
	
	treeview : function(){
		$('#menuTree').jstree({ 'core' : {
	    		"multiple" : false,
	    		'data' : {
	    			'url' : function (node) {
	    		    	return ctx+'/admin/menu/getRootMenus.do';
	    		    },
	    		    'data' : function (node) {
	    		    	return { 'id' : node.id };
	    		    }
	    		}
			},
			"types" : {
			    "#" : {
			      "max_children" : 100, 
			      "max_depth" : 4, 
			      "valid_children" : ["root"]
			    }
			  }
		})
		.on('changed.jstree', function (e, data) {
			if(data.action != 'deselect_all'){
				var obj = data.instance.get_node(data.selected[0]);
				MenuDwr.getMenuInfo({menuId : obj.id}, function(menuInfo){
					$('#menuId').val(menuInfo.menuId);
					$('#menuSeq').val(menuInfo.menuSeq);
					$('#menuFunc').val(menuInfo.menuFunc);
					$('#menuName').val(menuInfo.menuName);
					$('#prtMenuName').val(menuInfo.prtMenuName);
					$('#prtMenuId').val(menuInfo.prtMenuId);
					$('#menuCls').val(menuInfo.menuCls);
					$('#menuUrl').val(menuInfo.menuUrl);
					$('#description').val(menuInfo.description);
					$('#roles').val(menuInfo.roles);
					$('#ords').val(menuInfo.ords);
				});
			}
		});
		
		$('.modal-footer button#btnSave').bind({
			click : function(){
				var menu = {};
				menu.menuSeq = $('.modal-body input#menuSeq').val();
				menu.menuFunc = $('.modal-body input#menuFunc').val();
				menu.menuName = $('.modal-body input#menuName').val();
				menu.menuCls = $('.modal-body input#menuCls').val();
				menu.menuUrl = $('.modal-body input#menuUrl').val();
				menu.description = $('.modal-body input#description').val();
				if($(".modal-body select#roles").val() != null){
					menu.roles = $(".modal-body select#roles").val().join();
				}
				menu.ords = $('.modal-body input#ords').val();
				
				// 유효성
				if(menu.menuName == null || menu.menuName == ''){
					alert('메뉴명을 입력해주세요.');
					return;
				}
				if(menu.menuFunc == null || menu.menuFunc == ''){
					alert('실행 함수명을 입력해주세요.');
					return;
				}
				if(menu.menuUrl == null || menu.menuUrl == ''){
					alert('URL을 입력해주세요.');
					return;
				}
				
				var status = $('.modal-body #status').val();
				if(status == 'edit'){
					menu.menuId = $('.modal-body input#menuId').val();
					MenuDwr.updateMenu(menu, function(result){
						if(result){
							alert('수정 되었습니다.');
							$('#modal-form').modal('hide');
							$('#menuTree').jstree('refresh');
						}
					});
				}
				else if(status == 'add'){
					menu.prtMenuId = $('.modal-body input#prtMenuId').val();
					MenuDwr.addMenu(menu, function(result){
						if(result){
							alert('추가 되었습니다.');
							$('#modal-form').modal('hide');
							$('#menuTree').jstree('refresh');
						}
					});
				}
			}
		});
		
		// 편집
		$('#btnEdit, #btnAdd, #btnAddRoot').bind({
			click : function(){
				var btnId = $(this).attr('id');
				var status = (btnId == 'btnEdit') ? 'edit' : 'add' ;
				
				if(btnId != 'btnAddRoot' && $('#menuTree').jstree('get_selected').length == 0){
					alert('메뉴를 선택하세요.');
					return;
				}
				
				// 권한 가져와서 option으로 추가하기..
				var select = $('.modal-body select#roles');
				RoleDwr.getRoles({}, function(result){
					var option = null;
					var datas = result.dataList;
					select.html('');
					$.each(datas, function(idx, role){
						option = $('<option>').attr( {value : role.roleSeq} ).text( role.roleNameKor );
						select.append( option );
					});
					select.chosen().change();
					// 기본 게스트 선택?  select.val('ROLE_GUEST');
					select.trigger("chosen:updated");
				});
				
				$('#modal-form').modal('show');
				$('.modal-body #status').val(status);
				
				var title = '';
				var prtMenuName = null;
				var prtMenuId = null;
				
				if(status == 'edit'){
					var menuId = $('#menuTree').jstree('get_selected')[0];
					
					MenuDwr.getMenuInfo({menuId : menuId}, function(menuInfo){
						$('.modal-body #menuId').val(menuInfo.menuId);
						$('.modal-body #menuSeq').val(menuInfo.menuSeq);
						$('.modal-body #menuFunc').val(menuInfo.menuFunc);
						$('.modal-body #menuName').val(menuInfo.menuName);
						$('.modal-body #prtMenuName').val(menuInfo.prtMenuName);
						$('.modal-body #prtMenuId').val(menuInfo.prtMenuId);
						$('.modal-body #menuCls').val(menuInfo.menuCls);
						$('.modal-body #menuUrl').val(menuInfo.menuUrl);
						$('.modal-body #description').val(menuInfo.description);
						$('.modal-body #ords').val(menuInfo.ords);
						
						if(menuInfo.roleSeqs != undefined){
							var roles = menuInfo.roleSeqs.split(',');
							select.val(roles);
							select.trigger("chosen:updated");
						}
						
						title = '메뉴 편집 - '+menuInfo.menuName;
						$('.modal-header h4#modalTitle').text(title);
					});
					return;
				}
				else if(status == 'add'){
					if(btnId == 'btnAddRoot'){
						prtMenuName = 'ROOT';
						prtMenuId = '#';
						title = '1단계 메뉴 추가';
					}
					else{
						prtMenuName = $('#menuName').val();
						prtMenuId = $('#menuId').val();
						title = '하위 메뉴 추가';
					}
					$('.modal-header h4#modalTitle').text(title);
					
					$('.modal-body #menuId').val('자동입력');
					$('.modal-body #menuSeq').val();
					$('.modal-body #menuName').val('');
					$('.modal-body #menuFunc').val('');
					
					$('.modal-body #prtMenuName').val(prtMenuName);
					$('.modal-body #prtMenuId').val(prtMenuId);
					
					$('.modal-body #menuCls').val('');
					$('.modal-body #menuUrl').val('');
					$('.modal-body #description').val('');
					$('.modal-body #ords').val('');
				}
			}
		});
		
		// 삭제 다이얼로그
		$("#btnDelete").bind({
			click : function(){
				if(confirm("삭제 하시겠습니까?")){
					var menuId = $('#menuId').val();
					MenuDwr.deleteMenu(String(menuId), function(result){
						if(result){
							alert('삭제 되었습니다.');
							$('#menuTree').jstree('refresh');
						}
					});
				}
			}
		});
		
		$('#modal-form').on('shown.bs.modal', function () {
			$(".chosen-select").chosen(); 
			$('#chosen-multiple-style').on('click', function(e){
				var target = $(e.target).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('select#roles').addClass('tag-input-style');
				else $('select#roles').removeClass('tag-input-style');
			});
			
		});
	},
	
	roleMngtPage : function(){
		dwr.engine.beginBatch();
		var roleGrid = jQuery("#roleGrid").jqGrid({
			url : ctx+'/admin/role/roleList.do',
		    data : {},
		    datatype : 'json',
		    colNames: ["순번", "권한 약어", "한글명", "설명", "생성일", "사용여부"],
		    colModel: [
		               { name: "roleSeq", hidden:true },
		               { name: "roleCode", width: 100, editable:true, editoptions:{size:"30", maxlength:"30"}},
		               { name: "roleNameKor", width: 100, editable:true, editoptions:{size:"30", maxlength:"30"}},
		               { name: "description", width: 200, editable:true, edittype:"textarea", editoptions:{rows:"2", cols:"29"}},
		               { name: 'createDate', index:'createDate', width : 125, editable:false, formatter: dateFormatter},
		               { name: 'disabled', index:'disabled', width : 125, editable:true, edittype:"checkbox", editoptions: {value:"Yes:No"}, unformat: aceSwitch}
//				       { name: "createDate", width: 80, align: "right", formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0, prefix: "$ "} }
		    ],
		    jsonReader: { id: 'roleSeq', repeatitems: false},
		    autowidth: true,
		    height: 400,
		    rowNum: 20,
		    rownumbers: true,
		    sortname: "roleSeq",
		    sortorder: "desc",
		    scrollrows : true,
		    viewrecords: true,
		    gridview: true,
		    autoencode: true,
		    pager: '#roleGrid-pager',
		    altRows: true,
		    loadComplete : function() {
				var table = this;
				setTimeout(function(){
					updatePagerIcons(table);
//					enableTooltips(table);
					jQuery.fn.gridResize('roleGrid');
				}, 0);
			},
			dataProxy: function(ajaxOptions){
				var bean = jQuery.extend(true, {}, ajaxOptions.data);
				_dataProxy(ajaxOptions, this, bean, {
					save : RoleDwr.addRole,
					edit : RoleDwr.updateRole,
					remove : RoleDwr.removeRole
				});
			}
		});
		
		var addOptions = {
				addCaption:'권한 추가', 
				top: 50, 
				left: "100", 
				closeOnEscape: true, 
				closeAfterAdd: true,
				reloadAfterSubmit:true, 
				recreateForm: true,
				viewPagerButtons: false,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
					style_edit_form(form);
				}, 
				beforeSubmit : function(post, formId){
					post.disabled = (post.disabled == 'No')?null:new Date();
					return [true, "", ""];
				},
				afterComplete : function(response, postdata){
//					console.log('add');
					//jQuery("form[name='searchForm'] select[name='showroomSeq']").append('<option value="'+response.showroomSeq+'" title="'+response.showroomName+'">'+response.showroomName+'</option>');
				}, 
				afterSubmit : function(response, postdata){
//					console.log('afterSubmit');
					return [true, '', ''];
				}
			};
		
		//navButtons
		jQuery(roleGrid).jqGrid('navGrid', "#roleGrid-pager",
			{ 	//navbar options
				edit: true,
				editicon : 'icon-pencil blue',
				add: true,
				addicon : 'icon-plus-sign purple',
				del: true,
				delicon : 'icon-trash red',
				search: false,
				searchicon : 'icon-search orange',
				refresh: true,
				refreshicon : 'icon-refresh green',
				view: false,
				viewicon : 'icon-zoom-in grey',
			},
			{
				//edit record form
				//closeAfterEdit: true,
				recreateForm: true,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
					style_edit_form(form);
				}
			},
			addOptions,
			{
				//delete record form
				recreateForm: true,
				beforeShowForm : function(e) {
					var form = $(e[0]);
					if(form.data('styled')) return false;
					
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
					style_delete_form(form);
					
					form.data('styled', true);
				},
				onClick : function(e) {
					alert(1);
				}
			}
		);
		dwr.engine.endBatch({timeout: 60000});
	}
};

var _dataProxy = function(ajaxOptions, that, bean, dwrConfigObject){
	var dwrCallbackHandler = {
		callback : function(data){
			ajaxOptions.complete.call(that, data, 'success');
		},
		warningHandler : function(msg){
			ajaxOptions.error.call(that, null, 'warning', msg);
		},
		exceptionHandler : function(msg){
			ajaxOptions.error.call(that, null, 'exception', msg);
		},
		errorHandler : function(msg){
			ajaxOptions.error.call(that, null, 'error', msg);
		}
	};
	switch (ajaxOptions.data.oper) {
	case 'edit' :
		if ( jQuery.isPlainObject(dwrConfigObject) && jQuery.isFunction(dwrConfigObject.edit) ) {
			if( bean.createDate && Object.isString(bean.createDate) ) bean.createDate = Date.parse(bean.createDate);
			bean.updateBy = S_userSeq;
			if( jQuery.isFunction(dwrConfigObject.editCallback) ) {
				dwrCallbackHandler.callback = function(data){
					ajaxOptions.complete.call(that, data,'success');
					dwrConfigObject.editCallback.call(that, data);
				};
			}
			delete bean.id;
			delete bean.oper;
			dwrConfigObject.edit(bean, dwrCallbackHandler);
		}
		break;
	case 'add' :
		if ( jQuery.isPlainObject(dwrConfigObject) && jQuery.isFunction(dwrConfigObject.save) ) {
			bean.createBy = S_userSeq;
			if( jQuery.isFunction(dwrConfigObject.saveCallback) ) {
				dwrCallbackHandler.callback = function(data){
					ajaxOptions.complete.call(that, data,'success');
					dwrConfigObject.saveCallback.call(that, data);
				};
			}
			delete bean.createDate;
			delete bean.id;
			delete bean.oper;
			dwrConfigObject.save(bean, dwrCallbackHandler);
		}
		break;
	case 'del' : 
		if ( jQuery.isPlainObject(dwrConfigObject) && jQuery.isFunction(dwrConfigObject.remove) ) {
			if ( jQuery.isFunction(dwrConfigObject.removeCallback) ) {
				dwrCallbackHandler.callback = function(data){
					ajaxOptions.complete.call(that, data,'success');
					dwrConfigObject.removeCallback.call(that, data);
				};
			}
			dwrConfigObject.remove(Number(bean.id), dwrCallbackHandler);
		}
		break;
	}	
};