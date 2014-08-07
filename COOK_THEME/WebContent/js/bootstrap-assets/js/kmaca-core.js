var viewsPath = "";
function movePage(url){
	//alert("DSF:"+url);
	$("#runtimeContentsArea").load( viewsPath + url );
}