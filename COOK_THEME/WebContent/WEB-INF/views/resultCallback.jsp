<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/common.jsp"%>
<%
// Cache 제거
response.setHeader("Cache-Control","no-store"); 	//HTTP 1.1
response.setHeader("Pragma","no-cache");        	//HTTP 1.0
response.setDateHeader("Expires",0);            		//Proxy server cache 방지

String acceptCharset  = StringUtil.nullToBlank(request.getAttribute("acceptCharset"),"UTF-8");		//받는 페이지의 문자 셋이 다를경우 acceptCharset 에 charset을 넣어서 ....
String resultMessage  = StringUtil.nullToBlank(request.getAttribute("resultMessage"));
String nextLocation   = StringUtil.nullToBlank(request.getAttribute("nextLocation"));
String useHref        = StringUtil.nullToBlank(request.getAttribute("useHref"), "false").toLowerCase();
String callbackFunc   = StringUtil.nullToBlank(request.getAttribute("callbackFunc"));
if(!useHref.equals("true")) useHref = "false";
%>

<html>
<head>
</head>
<script type="text/javascript">
  function init() {
  	var nextLocation   = "${ctx}<%=nextLocation%>";
  	var resultMessage  = "<%=resultMessage%>";
  	if (resultMessage != "") alert(resultMessage);
  	<%=callbackFunc%>
  	if (nextLocation != "") {
  		if(nextLocation != "#"){
	  		if(<%=useHref%>) location.href = nextLocation;
	  		else location.replace(nextLocation);
	  	}
  	}
  }
</script>
<body onLoad="init();"></body>
</html>


