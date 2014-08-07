<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/include/common.jsp"%>
<script type="text/javascript">
var ctx = "<%= request.getContextPath()%>";
var S_adminSeq = "${MyAdmins.adminSeq}";
var S_adminId = "${MyAdmins.adminId}";
var serverName = "${serverName}";
var serverPort = "${serverPort}";
</script>
<script type="text/javascript" src="${ctx}/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${ctx}/js/jqueryui/ui/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
<script type='text/javascript' src='${ctx}/dwr/util.js'></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript">
	var ctx = "<%= request.getContextPath()%>";
	jQuery(document).ready(function(){
		document.location.href = ctx+'/gaon/main.do';
	});
</script>
</head>
<body>
</body>
</html>