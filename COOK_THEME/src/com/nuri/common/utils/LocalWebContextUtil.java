package com.nuri.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * =============================================================================
 *            프로젝트명 :   np_VerseOfDay
 *            화  일  명 :   LocalWebContextUtil.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2010. 11. 17.      이재열<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author JaeYeol Lee
 *
 */
public class LocalWebContextUtil {
	
	private static LocalWebContextUtil instance = new LocalWebContextUtil();
	
	private ThreadLocal<HttpServletRequest> request   = new ThreadLocal<HttpServletRequest>();
//	private ThreadLocal<Admin> admin = new ThreadLocal<Admin>();
	
	private LocalWebContextUtil(){}

	public static LocalWebContextUtil getInstance() {
		return instance;
	}
	
	public HttpServletRequest getRequest(){
		return request.get();
	}
	public void setRequest(HttpServletRequest request) {
		this.request.set(request);
	}

//	public void setAdmin(Admin admin) {
//		this.admin.set(admin);
//	}
//
//	public Admin getAdmin() {
//		return admin.get();
//	}
}
