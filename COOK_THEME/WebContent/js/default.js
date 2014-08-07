var myLayout;
var $mainTab = null;
(function($) {
	function loadCenter(e){
		e.preventDefault();
		
		var tabId = 'tabs-'+e.target.id;
		if($(tabId).html() != null ) {
			$mainTab.tabs('select', tabId);		// old
		} else {
			$mainTab.add(e, tabId);
			$.ajax({
				url: e.target.href,
				success : function (data, textStatus, XMLHttpRequest) {
					if(data.indexOf("관리자 로그인페이지") > -1){
						location.reload();
					}else{
						var panel = $('div#'+tabId).html(data);
						$('button', panel).button();
						// 해당 ID로 정의된 function 실행
						if(menuHandler[e.target.id] != undefined){
							menuHandler[e.target.id].call(panel, $mainTab);
						}
					}
				},
				error : function (XMLHttpRequest, textStatus, errorThrown) {
					$(tabId, myLayout.panes.center).html(XMLHttpRequest.responseText);
				}
			});
		}
		return false;
	}

	$(document).ready(function () {
		//$.datepicker.regional['ko'].yearSuffix = '';	// 년도 콤보에서 '년' 제거
		$.datepicker.setDefaults($.datepicker.regional['ko']);

		// 레이아웃
		var layoutOpt = {
			resizerClass: 'ui-state-default',
			north : {
					spacing_open:			1			// cosmetic spacing
				,	spacing_closed :		0
				,	togglerLength_open:		0			// HIDE the toggler button
				,	togglerLength_closed:	-1			// "100%" OR -1 = full width of pane
				,	resizable: 				false
				,	slidable:				false
			},
			west : {size: 150, slideTrigger_open: "mouseover"},
			east : {spacing_closed : 0, initClosed : true},
			south : {spacing_closed : 0, initClosed : true}
		};
		myLayout = $('body').layout(layoutOpt);

		// top 메뉴
		$('ul.jd_menu', myLayout.panes.north).jdMenu();

		var cfg = ($.hoverintent = {
			sensitivity: 7,
			interval: 100
		});
				
		$.event.special.hoverintent = {
			setup: function() {
				$(this).bind("mouseover", jQuery.event.special.hoverintent.handler);
			},
			teardown: function() {
				$(this).unbind("mouseover", jQuery.event.special.hoverintent.handler);
			},
			handler: function(event) {
				event.type = "hoverintent";
				var self = this,
					args = arguments,
					target = $(event.target),
					cX, cY, pX, pY;
					
				function track(event) {
					cX = event.pageX;
					cY = event.pageY;
				};
				pX = event.pageX;
				pY = event.pageY;
				function clear() {
					target.unbind("mousemove", track).unbind("mouseout", arguments.callee);
					clearTimeout(timeout);
				}
				function handler() {
					if ( ( Math.abs(pX-cX) + Math.abs(pY-cY) ) < cfg.sensitivity ) {
						clear();
						jQuery.event.handle.apply(self, args);
					} else {
						pX = cX; pY = cY;
						timeout = setTimeout(handler, cfg.interval);
					}
				}
				var timeout = setTimeout(handler, cfg.interval);
				target.mousemove(track).mouseout(clear);
				return true;
			}
		};

		// left 메뉴
		$("#accordion").accordion({
			event: "click hoverintent",
			autoHeight: false,
			navigation: true
		});

		// Pin 버튼
		$("#btnPin").button({
			icons: {
				primary: 'ui-icon-pin-s'
			},
			text: false
		}).click(function() {
			var options;
			if ($(this).button('option').label == 'Pin') {
				options = {
					label: 'Un-Pin',
					icons: {
						primary: 'ui-icon-pin-w'
					}
				};
			} else {
				options = {
					label: 'Pin',
					icons: {
						primary: 'ui-icon-pin-s'
					}
				};
			}
			$(this).button('option', options);
		});
		myLayout.addPinBtn("#btnPin", "west");
		myLayout.allowOverflow("north");
		
		// 아코디언 소메뉴 클릭
		$('a:not(.accessible)', 'ul.jd_menu, div#accordion').bind('click', loadCenter);
		
		// 메인탭
		$mainTab = jQuery('#tabs').tabs();
		$mainTab.add = function( e, tabId) {
			$mainTab.find('.ui-tabs-nav').append(
				jQuery('<li>').append(
					$('<a>').attr({href:'#'+tabId}).text(e.target.title)
				).append(
					$('<span>').addClass('ui-icon ui-icon-circle-close ui-closable-tab')
					.bind({
						click : function(){
//							var tabContainerDiv = $(this).closest(".ui-tabs").attr("id");
					        var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
					        $( "#" + panelId ).remove();
					        $mainTab.tabs("refresh");
//					        var tabCount = $("#"+tabContainerDiv).find(".ui-closable-tab").length;
//					        if (tabCount<1) {
//					            $("#"+tabContainerDiv).hide();
//					        }
						}
					})
				)
			);
			$mainTab.tabs('refresh');
			$mainTab.tabs({ active: -1 });
			$('<div>').attr({'id':tabId}).appendTo( $( "#tabs" ).tabs() );
			$mainTab.tabs('refresh');
		};

		jQuery('button', myLayout.panes.west).button();
		
		jQuery('#btnLogout').button().bind('click', function(){
			if(confirm("로그아웃 하시겠습니까?")) window.document.location.href = ctx+'/home/logout.do'; 
		});
		
		$('#btnLogin').button().bind({
			click : function(){
				window.open('/cook/login/login.jsp', 'loginPopup', '320px');
			}		
		});
		
		// 테마 스위처
//		$('#switcher').themeswitcher({width:130});

		// 자동로그아웃시...
		if(dwr.engine && dwr.engine.setTextHtmlHandler)
			dwr.engine.setTextHtmlHandler(function(){
				location.reload();
			});
	});
})(jQuery);