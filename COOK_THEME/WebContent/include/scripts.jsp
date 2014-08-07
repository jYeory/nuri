<script type="text/javascript">
// 	var $j = jQuery.noConflict();
	var ctx = "<%= request.getContextPath()%>";
	var serverName = "${serverName}";
	var serverPort = "${serverPort}";
	var S_role = "${S_role}";
	var S_userSeq = "${S_userSeq}";
	var S_userName = "${S_userName}";
	var __http = document.location.protocol;
// 	console.log('S_role > '+S_role);
// 	console.log('S_userName > '+S_userName);
// 	document.write(unescape("%3Cscript src=\""+__http+"//ajax.googleapis.com/ajax/libs/prototype/1.7.1.0/prototype.js\" type=\"text/javascript\"%3E%3C/script%3E"));
</script>
<!-- page specific plugin scripts -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
<script src="${ctx}/js/bootstrap-assets/js/html5shiv.js"></script>
<script src="${ctx}/js/bootstrap-assets/js/respond.min.js"></script>
<![endif]-->

<!-- basic scripts -->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${ctx}/js/bootstrap-assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

<script type="text/javascript">
	if("ontouchend" in document) document.write("<script src="+ctx+"'/bootstrap-assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/typeahead-bs2.min.js"></script>
<!-- ace scripts -->
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/ace-elements.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/ace.min.js"></script>

<!-- ace settings handler -->
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/ace-extra.min.js"></script>

<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/date-time/bootstrap-datepicker.min.js"></script>

<!--[if lte IE 8]>
  <script src="${ctx}/js/bootstrap-assets/js/excanvas.min.js"></script>
<![endif]-->

<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-migrate-1.2.1.min.js"></script>

<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/chosen.jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/jquery.easy-pie-chart.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/jquery.gritter.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/spin.min.js"></script>

<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/jquery.easy-pie-chart.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/jquery.sparkline.min.js"></script>

<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
<script type='text/javascript' src='${ctx}/dwr/util.js'></script>
<%-- <script type="text/javascript" src="${ctx}/js/scripts.js"></script> --%>

<script type="text/javascript" src="${ctx}/js/jqGrid/jquery.jqGrid.src.js" ></script> 
<script type="text/javascript" src="${ctx}/js/bootstrap-assets/js/jqGrid/i18n/grid.locale-en.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.fmatter.js"></script>

<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/handler.js"></script>
<script type="text/javascript" src="${ctx}/js/h_notice.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.placeholder.js"></script>

<script type="text/javascript" src="${ctx}/js/flot/jquery.flot.js"></script>
<script type="text/javascript" src="${ctx}/js/flot/jquery.flot.navigate.js"></script>
<script type="text/javascript" src="${ctx}/js/flot/jquery.flot.trendline.js"></script>
<script type="text/javascript" src="${ctx}/js/flot/jquery.flot.categories.js"></script>
<script type="text/javascript" src="${ctx}/js/flot/jquery.flot.pie.min.js"></script>
<script type="text/javascript" src="${ctx}/js/flot/jquery.flot.resize.min.js"></script>

<%--
<script type="text/javascript" src="${ctx}/js/loadImage/load-image.js"></script>
<script type="text/javascript" src="${ctx}/js/loadImage/load-image-orientation.js"></script>
<script type="text/javascript" src="${ctx}/js/loadImage/load-image-meta.js"></script>
<script type="text/javascript" src="${ctx}/js/loadImage/load-image-exif.js"></script>
<script type="text/javascript" src="${ctx}/js/loadImage/load-image-exif-map.js"></script>
<script type="text/javascript" src="${ctx}/js/loadImage/vendor/jquery.Jcrop.js"></script>
 --%>

<script type="text/javascript">
function movePage(id, url, params){
	if(console) console.log(url);
	if(params == undefined) params = {};
	$.ajax({
		url: url,
		data : params,
		success : function (data, textStatus, XMLHttpRequest) {
			$("#runtimeContentsArea").html( data );
			if(menuHandler[id] != undefined){
				menuHandler[id].call();
			}
		},
		error : function (XMLHttpRequest, textStatus, errorThrown) {
			$(tabId, myLayout.panes.center).html(XMLHttpRequest.responseText);
		}
	});
}

jQuery.fn.gridResize = function(gridId){
	var gridParentWidth = $('#gbox_' + gridId).parent().width();
	$('#' + gridId).jqGrid('setGridWidth', gridParentWidth);
};

function simpleDateFormatter(cellValue, options, c){
	if(cellValue) {
        return $.jgrid.parseDate(
            '', 
            new Date(+cellValue), 
            'ISO8601Short', 
            $.extend({}, $.jgrid.formatter.date, options)
        );
    } else {
        return '';
    }
};
</script>
