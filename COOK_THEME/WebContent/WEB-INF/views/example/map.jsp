<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/include/common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
#example-chartTooltip {
    font-size: 12px;
    color: rgb(15%, 15%, 15%);
    padding:2px;
    background-color: rgba(95%, 95%, 95%, 0.8);
    border: 1px solid #CCCCCC;
    white-space: nowrap;    
    padding: 2px;
}
</style>
</head>
<body>
	<table>
		<tr>
			<td><table id="grid"></table></td>
			<td><div id="world-map" style="width: 800px; height: 450px"></div></td>
		</tr>
	</table>
	<div id="example-chart" style="margin-top:20px; margin-left:20px; width:1200px; height:400px;"></div>
	<div id="example-chartTooltip" style="position: absolute; z-index: 99; display: none;"></div>
</body>
</html>