<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/common.jsp" %>
<%
String jvm = System.getProperty("java.runtime.name");
String jreVersion = System.getProperty("java.runtime.version");
String osName = System.getProperty("os.name"); 
%>
<c:set var="jvm" value="<%=jvm%>" />
<c:set var="jreVersion" value="<%=jreVersion%>" />
<c:set var="osName" value="<%=osName%>" />
<div>
	<%--
	<ul>
		<li><a href="#expiredStatus">만기일 현황</a></li>
	</ul>
	<div id="expiredStatus">
		<form name='expiredStatusForm'>
			<input type='hidden' name='criterionDay1' value=''/>
			<input type='hidden' name='criterionDay2' value=''/>
			<input type='hidden' name='couponNameSeq' value=''/>
		</form>
		<table id='tblExpiredStatus' class="ui-widget ui-widget-content ui-corner-all"></table>
		<br/>
		<button type='button' name='doDownExpiredStatus'>엑셀</button>
		<table id='tblExpiredStatusGrid' border='1'></table>
		<div id="pgExpiredStatusGrid"></div>
	</div>
	 --%>
	 <p> 본 사이트는 아래 라이브러리로 구성되어 있습니다. </p>
	 <br/>
	 
	 jQuery 1.9.1<br/>
	 jQuery UI 1.10.3<br/>
	 jqPlot 1.0.8<br/>
	 flotChart 0.8.1<br/>
	 jVectorMap 1.2.2<br/>
	 
	 <p>※ IE9, Chrome 등의 브라우저 사용을 권장합니다.</p>
	 <br/>
	 <br/>
	 <p> last update.. 2013. 12. 11 </p>
</div>