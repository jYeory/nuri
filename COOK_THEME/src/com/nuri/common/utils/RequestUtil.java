package com.nuri.common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * =============================================================================
 *            프로젝트명 :   np_VerseOfDay
 *            화  일  명 :   RequestUtil.java
 *            기      능 :   request parameter들을 조작하는 Utility Method 들
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
public class RequestUtil {
	
	protected static Log log = LogFactory.getLog(RequestUtil.class);
	
	/** 선택한 parameter들만 포함한다. */
	public static final int PARAM_INCLUDE = 1;

	/** 선택한 parameter들을 제외한다. */
	public static final int PARAM_EXCLUDE = 2;

	/**
	 * request를 query string 형식으로 반환 한다.
	 * 
	 * @param request
	 * @return
	 */
	public static String requestToQueryString(HttpServletRequest request) {
		return requestToQueryString(request, Configuration.getInstance().getString("default.url.encoding.charset"));
	}

	/**
	 * request를 query string 형식으로 반환 한다.
	 */
	public static String requestToQueryString(HttpServletRequest request, String enc) {
		StringBuffer qrystrBuf = new StringBuffer();

		Enumeration<?> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();
			String[] values = request.getParameterValues(name);
			for (int i=0; i < values.length; i++) {
				qrystrBuf.append(name + "=");
				// for jdk 1.4 version
				try {
					qrystrBuf.append( URLEncoder.encode( StringUtil.nullToBlank(values[i]), enc) );
				} catch(UnsupportedEncodingException e) {}
				if (i != (values.length -1)) qrystrBuf.append("&");
			}
			if (names.hasMoreElements()) qrystrBuf.append("&");
		}
		return qrystrBuf.toString();
	}
	
	/**
	 * request를 query string 형식으로 반환 한다.
	 * 
	 * @param request request
	 * @param names prameter들의 name
	 * @param type parameter들을 포함 또는 제외 여부(PARAM_INCLUDE, PARAM_EXCLUDE)
	 */
	public static String requestToQueryString(HttpServletRequest request, String[] paramNames, int type) {
		return requestToQueryString(request, paramNames, type, Configuration.getInstance().getString("default.url.encoding.charset"));
	}

	/**
	 * request를 query string 형식으로 반환 한다.
	 * 
	 * @param request request
	 * @param names prameter들의 name
	 * @param type parameter들을 포함 또는 제외 여부(PARAM_INCLUDE, PARAM_EXCLUDE)
	 * @param encoding char-set
	 */
	public static String requestToQueryString(HttpServletRequest request, String[] paramNames, int type, String encoding) {
		StringBuffer qrystrBuf = new StringBuffer();

		Enumeration<?> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();
			String[] values = request.getParameterValues(name);
			boolean hasParam = false; 
			boolean isIncluded = true;
			
			if(paramNames!=null) {
				for(int i=0; i<paramNames.length; i++) {
					if( name.equalsIgnoreCase(paramNames[i]) ) {
						hasParam = true;
					}
				}
			}
			
			if(type == PARAM_INCLUDE) {
				if(hasParam) isIncluded = true;
				else isIncluded = false;
			}
			else if(type == PARAM_EXCLUDE) {
				if(hasParam) isIncluded = false;
				else isIncluded = true;
			}
			
			if(isIncluded) {
				for (int i=0; i < values.length; i++) {
					qrystrBuf.append(name + "=");
					// for jdk 1.4 version
					try {
						qrystrBuf.append( URLEncoder.encode( StringUtil.nullToBlank(values[i]), encoding ) );
					} catch(UnsupportedEncodingException e){}
					if (i != (values.length -1)) qrystrBuf.append("&");
				}
				if (names.hasMoreElements()) qrystrBuf.append("&");
			}
		}
		return qrystrBuf.toString();
	}

	/**
	 * request를 input tag 형식으로 반환한다.
	 */
	public static String requestToHiddenField(javax.servlet.http.HttpServletRequest request) {
		StringBuffer qrystrBuf = new StringBuffer();

		Enumeration<?> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();
			String[] values = request.getParameterValues(name);
			for (int i=0; i < values.length; i++) {
				qrystrBuf.append("<input type=hidden name=\""+name+"\" value=\""+StringUtil.nullToBlank(values[i])+"\">\n");
			}
		}
		return qrystrBuf.toString();
	}

	/**
	 * request를 input tag 형식으로 반환한다.
	 * @param request request
	 * @param names prameter들의 name
	 * @param type parameter들을 포함 또는 제외 여부(PARAM_INCLUDE, PARAM_EXCLUDE)
	 */
	public static String requestToHiddenField(javax.servlet.http.HttpServletRequest request, String[] paramNames, int type) {
		StringBuffer qrystrBuf = new StringBuffer();

		Enumeration<?> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();
			String[] values = request.getParameterValues(name);
			boolean hasParam = false; 
			boolean isIncluded = true;
			
			if(paramNames!=null) {
				for(int i=0; i<paramNames.length; i++) {
					if( name.equalsIgnoreCase(paramNames[i]) ) {
						hasParam = true;
					}
				}
			}
			
			if(type == PARAM_INCLUDE) {
				if(hasParam) isIncluded = true;
				else isIncluded = false;
			}
			else if(type == PARAM_EXCLUDE) {
				if(hasParam) isIncluded = false;
				else isIncluded = true;
			}
			
			if(isIncluded) {
				for (int i=0; i < values.length; i++) {
					qrystrBuf.append("<input type=hidden name=\""+name+"\" value=\""+StringUtil.nullToBlank(values[i])+"\">\n");
				}
			}
		}
		return qrystrBuf.toString();
	}

    /**
     * <pre>
     * 파라미터명을 Bean class의 속성명 형식으로 변환해준다.
     * 만약, 'p_' 로 시작한다면 p_는 제거한다.
     * ex) p_user_nm ==> userNm
     *     user_nm   ==> userNm
     * </pre>
     * 
     * @param name
     * @return
     */
    public static String convertAttributeName(String name) {
        StringBuffer result = new StringBuffer();
        String data = name;
        if(name.startsWith("p_")) {
            data = name.substring(2);
        }
        int len = data.length();
        boolean needToUpper = false;
        for(int i=0; i<len; i++) {
            char c = data.charAt(i);
            if(c == '_') {
                needToUpper = true;
    
            } else {
                if(needToUpper) {
                    result.append(Character.toUpperCase(c));
                    needToUpper = false;
    
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    /**
     * Request의 값들을 지정한 Bean Class 를 생성하여 속성에 setting 해주어서 반환한다.
     * 'p_' 로 시작하는 파라미터들만 bean에 setting한다.
     * 만약 오류가 발생한다면 null을 반환한다.
     * 
     * @param request
     * @param clazz
     * @return
     */
    public static Object populate(ServletRequest request, Class<?> clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            
            return populate(request, obj);
    
        } catch(InstantiationException ignored){
    
        } catch(IllegalAccessException ignored){
        
        }
        return obj;
    }

    /**
     * Request의 값들을 넘겨준 Object class의 속성에 setting 해준다.
     * 'p_' 로 시작하는 파라미터들만 bean에 setting한다. 
     * 
     * @param request
     * @param obj
     * @return
     */
    public static Object populate(ServletRequest request, Object obj) {
		if(obj==null) {
			log.error("RequestUtil.populate() : TargetObject is NULL");
			return null;
		}
    	Enumeration<?> names = request.getParameterNames();
    	while (names.hasMoreElements()) {
    		String name     = (String)names.nextElement();
    		if(name!=null && name.indexOf("p_")==0) {
    			String[] values = request.getParameterValues(name);
    
    		    String attributeName  = convertAttributeName(name);
    		    Object attributeValue = null;
    		    if(values!=null && values.length==1) {
    		        attributeValue = values[0];
    		        
    		    } else {
    		        attributeValue = values;
    		    }
    		    try {
    			    BeanUtils.setProperty(obj, attributeName, attributeValue);
    
				} catch(IllegalAccessException ignored) {
					log.error(name+"["+attributeName+"] is not allowed attribute for "+obj.getClass().getName());
				} catch(InvocationTargetException ignored) {
					log.error(name+"["+attributeName+"] is not a appropriate attribute for "+obj.getClass().getName());
				}

    		}
    	}        
        return obj;
    }

    /**
     * Client IP Address를 반환
     * 
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
    	if(request==null) return null;
		String remoteIP = request.getHeader("X-FORWARDED-FOR");
		if(remoteIP==null || remoteIP.length()==0)
			remoteIP = request.getRemoteAddr();
		return remoteIP;
    }
}
