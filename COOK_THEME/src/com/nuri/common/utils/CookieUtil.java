package com.nuri.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	/**
	 * 쿠키를 추가하기
	 * 
	 * @param response
	 * @param key
	 * @param value
	 */
	public static void addCookie(HttpServletResponse response, String key, String value) {
		addCookie(response, key, value, Configuration.getInstance().getString("cookie.domain"), -1);
	}

	/**
	 * 쿠키를 추가하기
	 * 
	 * @param response
	 * @param key
	 * @param value
	 * @param domain
	 * @param maxage
	 */
	public static void addCookie(HttpServletResponse response, String key, String value, String domain, int maxage) {
		addCookie(response, key, value, domain, maxage, Configuration.getInstance().getString("default.charset"));
	}

	/**
	 * 쿠키를 추가하기
	 *
	 * @param response 
	 * @param key
	 * @param value
	 * @param domain
	 * @param maxage
	 * @param encoding
	 */
	public static void addCookie(HttpServletResponse response, String key, String value, String domain, int maxage, String encoding) {
		Cookie cookie   = null;
		String encValue = value;

		// for jdk 1.4 version
		try {
			encValue = URLEncoder.encode(value, encoding);
		} catch(UnsupportedEncodingException e){}
		
		cookie = new Cookie(key, encValue);

		if (domain!=null && !domain.equals("")) {
			cookie.setDomain(domain);
		}

		//cookie.setPath("/");
		cookie.setMaxAge(maxage);
		response.addCookie(cookie);
	}
	
	/**
	 * 특정 쿠키 가져오기
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals(name)) {
					return cookies[i];
				}
			}
		}
		return null;
	}
	
	/**
	 * 특정 쿠키값 가져오기
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		return getCookieValue(request, name, Configuration.getInstance().getString("default.charset"));
	}
	
	/**
	 * 특정 쿠키값 가져오기
	 * 
	 * @param request
	 * @param name
	 * @param encoding
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String name, String encoding) {
		Cookie cookie = getCookie(request, name);
		if(cookie==null) return "";
		String encValue = cookie.getValue();
		try {
			encValue = URLDecoder.decode(encValue, encoding);
		} catch(UnsupportedEncodingException e){}
		return encValue; 
	}
}
