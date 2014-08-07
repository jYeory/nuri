<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/include/common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	#placeholder2 .button {
		position: absolute;
		cursor: pointer;
	}

	#placeholder2 div.button {
		font-size: smaller;
		color: #999;
		background-color: #eee;
		padding: 2px;
	}
	.message {
		padding-left: 50px;
		font-size: smaller;
	}
	
	#content {
		width: 800px;
		margin: 0 0 0 0;
	}
	
	.demo-container {
		box-sizing: border-box;
		width: 800px;
		height: 450px;
		padding: 20px 15px 15px 15px;
		margin: 15px auto 30px auto;
		border: 1px solid #ddd;
		background: #fff;
		background: linear-gradient(#f6f6f6 0, #fff 50px);
		background: -o-linear-gradient(#f6f6f6 0, #fff 50px);
		background: -ms-linear-gradient(#f6f6f6 0, #fff 50px);
		background: -moz-linear-gradient(#f6f6f6 0, #fff 50px);
		background: -webkit-linear-gradient(#f6f6f6 0, #fff 50px);
		box-shadow: 0 3px 10px rgba(0,0,0,0.15);
		-o-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
		-ms-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
		-moz-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
		-webkit-box-shadow: 0 3px 10px rgba(0,0,0,0.1);
	}
	
	.demo-placeholder {
		width: 100%;
		height: 100%;
		font-size: 14px;
		line-height: 1.2em;
	}
</style>
</head>
<body>
	<table>
		<tr>
			<td><table id="grid2"></table></td>
			<td><div id="world-map2" style="width: 800px; height: 450px"></div></td>
		</tr>
	</table>
	<div id="content">
		<div class="demo-container">
			<div id="placeholder2" class="demo-placeholder"></div>
		</div>
	</div>
</body>
</html>