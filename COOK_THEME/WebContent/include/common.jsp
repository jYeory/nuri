<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.apache.commons.collections.IteratorUtils"%>
<%@page import="org.springframework.security.authentication.AnonymousAuthenticationToken"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@page import="com.nuri.domain.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@ page import="java.util.*,
				   java.net.*,
                   java.text.*,
                   com.nuri.common.utils.StringUtil,
                   com.nuri.common.utils.Configuration,
                   com.nuri.common.utils.Parameters"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<jsp:useBean id="loginedUser" class="com.nuri.domain.User" scope="session"/>
<%
	String S_role = null;
	String S_userSeq = null;
	String S_userId = null;
	String S_userName = null;
	String S_nickName = null;
	
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
// 	System.out.println("auth > "+auth);
	// not logined
	if (auth != null && auth instanceof AnonymousAuthenticationToken) {
		List<SimpleGrantedAuthority> authorities =  IteratorUtils.toList( auth.getAuthorities().iterator() );
		S_role = authorities.get(0).getAuthority();
		S_userName = auth.getName();
	}
	// logined
	else {
		S_userSeq = StringUtil.nullToBlank(loginedUser.getUserSeq());
		S_userId = StringUtil.nullToBlank(loginedUser.getUserId());
		S_userName = StringUtil.nullToBlank(loginedUser.getUserName());
		S_nickName = StringUtil.nullToBlank(loginedUser.getNickname());
	}
	
	Log logger = LogFactory.getLog(this.getClass());
	String homeUrl = "";
	 if(Configuration.getInstance().getBoolean("use.default.url")){
		homeUrl = Configuration.getInstance().getString("default.url");
	}else{
		homeUrl = request.getContextPath();
	} 
	 
	String ctx = request.getContextPath();
	String serverName = request.getServerName();
	int serverPort = request.getServerPort();
	
// 	System.out.println("S_role > "+S_role);
// 	System.out.println("S_userName > "+S_userName);
	
%>
<c:set var="homeUrl" value="<%= homeUrl %>"/>
<c:set var="ctx" value="<%= ctx %>"/>
<c:set var="S_userId" value="<%= S_userId %>"/>
<c:set var="S_userSeq" value="<%= S_userSeq %>"/>
<c:set var="S_userName" value="<%= S_userName %>"/>
<c:set var="S_nickName" value="<%= S_nickName %>"/>
<c:set var="S_role" value="<%= S_role %>"/>
<c:set var="serverName" value="<%= serverName %>"/>
<c:set var="serverPort" value="<%= serverPort %>"/>
<%-- <c:set var="S_role" value="<%= S_role %>"/> --%>