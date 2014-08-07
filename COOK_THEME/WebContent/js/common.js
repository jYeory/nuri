/*================== DWR 용 ==================*/
dwr.maxCallCount = 30;

/**
 * default list 메서드를 사용하는 DWR 메서드를 호출한 후
 * 결과 list 를 Cache 맵 에 담아 다음 번 같은 조건의
 * 같은 key 를 가진 DWR 메서드를 호출할 때 Cache 에서 값을 반환.
 * 
 * 사용 예)
 * var params = {xxx : zzz , kkk : sss};
 * cachedDwr('workDetailList', params, RepairDwrMgr.getWorkDetailList, function(list){
 * .....
 * });
 * 
 * @param key Cache 를 관리하는 맵에 등록할 key
 * @param params DWR이 Parameters Object 로 변환하는 조건 값들
 * @param dwrFunc 호출할 DWR 메서드
 * @param callback DWR 메서드가 호출되고 실행할 callback
 * @param dataFromBrowser callback 에 넘길 browser 쪽 데이터
 */
function cachedDwr(key, params, dwrFunc, callback, dataFromBrowser){
	var paramsJSON;
	var scope = 'session';
	if(typeof params == 'object'){
		if(Object.isArray(params)){
			paramsJSON = Object.toJSON({arrayValue: params , _$key: key , scope: scope});
		}else{
			params._$key = key;
			params.scope = scope;
			paramsJSON = Object.toJSON(params);
		}
	}else if(typeof params == 'string'){
		paramsJSON = Object.toJSON({singleValue: params , _$key: key , scope: scope});
	}else if(typeof params == 'function'){
		callback = dwrFunc;
		dwrFunc = params;
		paramsJSON = Object.toJSON({_$key: key , scope: scope});
	}else{
		return;
	}
	var _callback = function(list, dataFromBrowser){
		if(jQuery.jCache){
			jQuery.jCache.setItem(paramsJSON, list);
		}else if(window.localStorage){
			window.localStorage.setItem(paramsJSON, Object.toJSON(list));
		}else{
			jQuery.data(window, paramsJSON, list);
		}
		callback(list, dataFromBrowser);
	}
	if(jQuery.jCache && jQuery.jCache.hasItem(paramsJSON)){
		callback(jQuery.jCache.getItem(paramsJSON), dataFromBrowser);
	}else if(window.localStorage && window.localStorage.getItem(paramsJSON)){
		callback(window.localStorage.getItem(paramsJSON).evalJSON(), dataFromBrowser);
	}else if(jQuery.data(window, paramsJSON)){
		callback(jQuery.data(window, paramsJSON), dataFromBrowser);
	}else if(typeof params == 'object' || typeof params == 'string'){
		dwrFunc(params,{
			arg : dataFromBrowser,
			callback : _callback
		});
	}else{
		dwrFunc({
			arg : dataFromBrowser,
			callback : _callback
		});
	}
}

/**
 * DWR로 불러오는 BEAN 에 담아 가져올 properties
 * @param name
 * @param inclusions
 * @return
 */
dwr.engine.inclusion = function(name, inclusions){
	var headerMap = dwr.engine._headers;
	if(!headerMap){
		dwr.engine._headers = {};
	}

	var biMap = dwr.engine._headers["BEAN_INCLUSION"];
	biMap = biMap ? (typeof biMap == 'string' && biMap.isJSON()) ? biMap.evalJSON() : biMap : {};

	if(typeof name == 'string'){
		if(typeof inclusions == "undefined")
			return biMap[name];
		else if(inclusions != null)
			biMap[name] = inclusions;
		else
			delete biMap[name];
	}else if(typeof name == 'object'){
		biMap = Object.extend(biMap,name);
	}
	dwr.engine._headers["BEAN_INCLUSION"] = Object.toJSON(biMap);
}

/**
 * DWR로 불러오는 BEAN 에 제외할 properties
 * @param name
 * @param exclusions
 * @return
 */
dwr.engine.exclusion = function (name, exclusions){
	var headerMap = dwr.engine._headers;
	if(!headerMap){
		dwr.engine._headers = {};
	}

	var biMap = dwr.engine._headers["BEAN_EXCLUSION"];
	biMap = biMap ? (typeof biMap == 'string' && biMap.isJSON()) ? biMap.evalJSON() : biMap : {};

	if(typeof name == 'string'){
		if(typeof exclusions == "undefined")
			return biMap[name];
		else if(exclusions != null)
			biMap[name] = exclusions;
		else
			delete biMap[name];
	}else if(typeof name == 'object'){
		biMap = Object.extend(biMap,name);
	}
	dwr.engine._headers["BEAN_EXCLUSION"] = Object.toJSON(biMap);
}

/**
 * DWR 호출시 extra header로 넘기는 값 제거 
 * @return
 */
dwr.engine.clearHeaders = function(){dwr.engine._headers = null;}

/**
 * clearHeaders를 자동으로 실행 토글
 */
dwr.engine.autoClearHeaders = function(){
	// 모든 dwr 메서드 호출후 inclusion, exclusion 자동 초기화 토글
	if(dwr.engine.autoClearAdvice){
		if(typeof dwr.engine.autoClearAdvice === 'function')
			dwr.engine.autoClearAdvice.unweave();
		else if(Object.isArray(dwr.engine.autoClearAdvice) && typeof dwr.engine.autoClearAdvice[0] === 'function')
			dwr.engine.autoClearAdvice[0].unweave();

		delete dwr.engine.autoClearAdvice;
	}else{
		dwr.engine.autoClearAdvice = jQuery.aop.after( {target: dwr.engine.batch, method: 'populateHeadersAndParameters'}, dwr.engine.clearHeaders);
	}
}

/**
 *  error handler
 */
function showErrMsg(msg, obj){
	alert("오류가 발생했습니다. 관리자에게 문의해주세요.\n> "+obj.javaClassName);
}
dwr.engine.setErrorHandler(showErrMsg);

/**
 * hook를 임시로 변경, 한번 dwr요청후 기본 hook로 다시 변경
 */
dwr.engine.tempHook = function(preHook, postHook){
	var tmpPreHook = dwr.engine._preHook;
	var tmpPostHook = dwr.engine._postHook;
	dwr.engine.setPreHook(preHook);
	dwr.engine.setPostHook(function(){
		postHook();
		dwr.engine.setPreHook(tmpPreHook);
		dwr.engine.setPostHook(tmpPostHook);
	});
};

function tempHook(a){
	dwr.engine.tempHook(
		function(){ 
			jQuery("#lui_"+a).show();
			jQuery("#load_"+a).show();
		}, 
		function(){
			jQuery("#lui_"+a).hide();
			jQuery("#load_"+a).hide();
		}
	);
}

/*================== DWR 용 ==================*/

/** 숫자 유효성 검사. alert 사용 **/
function checkNumber(value, orgValue){
	value = value.replace(new RegExp(" ", "gi"), "").replace(new RegExp(",", "gi"), "");
	var re = /^[0-9]+$/;
	if(re.test(value)){
		return value;
	}else{
		alert("숫자만 입력 가능합니다.");
		return orgValue?orgValue:'';
	}
}

/** 숫자 유효성 검사. alert 미사용 **/
function checkNum(obj){
	var key = event.keyCode;
	
	if((key >= 48 && key <= 57) ||		// 숫자열 0 ~ 9 : 48 ~ 57
	   (key >= 96 && key <= 105) ||		// 키패드 0 ~ 9 : 96 ~ 105
		key == 8 ||						// BackSpace
		key == 46 ||					// Delete
		key == 37 ||					// 좌 화살표
		key == 39 ||					// 우 화살표
		key == 35 ||					// End 키
		key == 36 ||					// Home 키
		key == 9 ){						// Tab 키
		
		if(key == 48 || key == 96){ // 0을 눌렀을경우
			if(obj.value == '0') // 현재 값이 0일 경우에서 0을 눌렀을경우
				event.returnValue = false;
			else // 다른숫자뒤에오는 0은
				event.returnValue = true; // 입력시킨다.
		}
	}
	else // 숫자가 아니면 넣을수 없다.
		event.returnValue = false;
}

function formatNumber(num, pattern){
	return num ? jQuery.currency(num, pattern?pattern : {s:",", d:".", c:0} ) : '';
}
function formatDate(cal, pattern){
	return cal ? cal.toString(pattern ? pattern : 'yyyy-MM-dd') : '';
}

function numberFormatComma(n) {
	var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
	n += '';                          // 숫자를 문자열로 변환
	while (reg.test(n)){
		n = n.replace(reg, '$1' + ',' + '$2');
	}
	return n;
}

function parseDate(date){
	var temp = new Date(date);
	var dateStr = padStr(temp.getFullYear()) + '-' +
	                  padStr(1 + temp.getMonth()) + '-' +
	                  padStr(temp.getDate());
	    return dateStr;
};

function padStr(i) {
    return (i < 10) ? "0" + i : "" + i;
}

function log(msg){
	if(console)
		console.log(msg);
}

/*
 *  ExtJS Grid 날짜 포맷..
 */
function parseDate(date){
	var temp = new Date(date);
	var dateStr = padStr(temp.getFullYear()) + '-' +
	                  padStr(1 + temp.getMonth()) + '-' +
	                  padStr(temp.getDate());
	    return dateStr;
};

function padStr(i) {
    return (i < 10) ? "0" + i : "" + i;
}

/**
 * JSON Text를 Object로 변환해주는 함수
 * 
 * @param JSON Text
 * @returns object
 */
function parseJSON(data){
	if ( typeof data !== "string" || !data ) {
		return null;
	}

	// Make sure leading/trailing whitespace is removed (IE can't handle it)
	data = data.trim();
	
	// Make sure the incoming data is actual JSON
	// Logic borrowed from http://json.org/json2.js
	if ( rvalidchars.test(data.replace(rvalidescape, "@")
		.replace(rvalidtokens, "]")
		.replace(rvalidbraces, "")) ) {

		// Try to use the native JSON parser first
		return window.JSON && window.JSON.parse ?
			window.JSON.parse( data ) :
			(new Function("return " + data))();

	} else {
		alert( "Invalid JSON: " + data );
	}
}

function handlePagedList(a){
	a.records=a.totalCount?a.totalCount:a.dataList.length;
	a.total=((a.records-1)/a.pageSize).floor()+1;
	a.page=a.currentPage;
	a.pagesize=a.pageSize;
	a.userdata=a.allValues;
	a.rows=a.dataList;
	return a;
}

// 글자수 제한
function fnCheckStrLength(obj, cnt) 
{ 
   var now_str = obj.value;                     //이벤트가 발생한 컨트롤의 value값 
   var now_len = obj.value.length;              //현재 value값의 글자 수 
   var max_len = cnt;                           //제한할 최대 글자 수 
   var i = 0;                                   //for문에서 사용할 변수 
   var cnt_byte = 0;                            //한글일 경우 2 그외에는 1바이트 수 저장 
   var sub_cnt = 0;                             //substring 할때 사용할 제한 길이를 저장 
   var chk_letter = "";                         //현재 한/영 체크할 letter를 저장 
   var lmt_str = "";                            //제한된 글자 수만큼만 저장 
    
   for (i=0; i<now_len; i++) 
   { 
       chk_letter = now_str.charAt(i); 
       if (escape(chk_letter).length > 4) 
       { 
           cnt_byte += 2; 
       }else{ 
           cnt_byte++; 
       } 
       if (cnt_byte <= max_len) 
       { 
           sub_cnt = i + 1; 
       } 
   } 
   if (cnt_byte > max_len) 
   { 
       alert("최대" + max_len + "글자 이상 쓸수 없습니다!"); 
       lmt_str = now_str.substring(0, sub_cnt); 
       obj.value = lmt_str; 
   } 
   obj.focus(); 
}

Array.prototype.removeByValue = function() {
    if(!Array.prototype.indexOf) {
        Array.prototype.indexOf = function(what, i) {
            i = i || 0;
            var L = this.length;
            while (i < L) {
                if(this[i] === what) return i;
                ++i;
            }
            return -1;
        };
    }
    var what, a = arguments, L = a.length, ax;
    while (L && this.length) {
        what = a[--L];
        while ((ax = this.indexOf(what)) !== -1) {
            this.splice(ax, 1);
        }
    }
    return this;
};

Array.prototype.removeByIdx = function(idx) {
    return (idx<0 || idx>this.length) ? this : this.slice(0, idx).concat(this.slice(idx+1, this.length));
};