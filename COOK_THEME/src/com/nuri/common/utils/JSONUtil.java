package com.nuri.common.utils;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   JSONUtil.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2013. 7. 24.      	jYeory<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author jYeory
 *
 */
public class JSONUtil {

	/**
	 * Object를 JSON String으로 변환하여 반환
	 * 
	 * @param object
	 * @param excludes
	 * @return
	 */
	public static String toJSON(Object object, String[] excludes) {
		JsonConfig jconfig = new JsonConfig();
		jconfig.setAllowNonStringKeys(true);
		jconfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		if(excludes!=null) jconfig.setExcludes(excludes);
		
		JSON json = JSONSerializer.toJSON(object, jconfig);
		return json.toString();
	}
	
	public static String encode(Object obj){
		return net.arnx.jsonic.JSON.encode(obj);
	}
	
	public static Object decode(String jsonStr){
		return net.arnx.jsonic.JSON.decode(jsonStr);
	}
	
	/**
	 * ExtJS Json 생성 
	 * @param list
	 * @return
	 */
	public static String toExtJSON(PagedList<?> list){
		return "{ \"totalCount\":\""+list.getTotalCount()+"\", \"datas\":"+net.arnx.jsonic.JSON.encode(list.getDataList())+"}";
	}
}
