package com.nuri.common.utils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.locale.LocaleBeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * =============================================================================
 *            프로젝트명 :   np_VerseOfDay
 *            화  일  명 :   Parameters.java
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
public class Parameters<K, V> extends HashMap<K, V> implements Serializable {
	
	static final long serialVersionUID = 8750411155467048253L;
	
	static final Log logger = LogFactory.getLog(Parameters.class.getName());
	
	private String defaultCharsetEncoding = "UTF-8";
	
	public void setDefaultCharsetEncoding(String charset) {
		defaultCharsetEncoding = charset;
	}

	public String getDefaultCharsetEncoding() {
		return defaultCharsetEncoding;
	}

	
	/**
	 * Default Constructor
	 */
	public Parameters() {
		super();
	}

	/**
	 * ServletRequest를 사용할 경우 Constructor  
	 * 
	 * @param request
	 */
	public Parameters(ServletRequest request) {
		String enc = request.getCharacterEncoding();
		if(enc!=null) defaultCharsetEncoding = enc;
		initMap(request);
	}

	/**
	 * ServletRequest의 parameter를 모두 populate
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void initMap(ServletRequest request) {
		java.util.Enumeration<?> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();
			String[] values = request.getParameterValues(name);
			try {
				if(values.length==1) {
					put((K)name, (V)request.getParameter(name));
				} else {
					put((K)name, (V)values);
				}
				
			} catch(ClassCastException cce) {
				logger.error("Type Error: ["+name+"=>"+(values.length==1?values[0]:values)+"] ClassCastException ");
			}
		}
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
		return convertAttributeName("p_", name);
	}

	/**
	 * <pre>
	 * 파라미터명을 Bean class의 속성명 형식으로 변환해준다.
	 * prefix 존재하면 prefix는 제거한다.
	 * </pre>
	 * 
	 * @param prefix
	 * @param name
	 * @return
	 */
	public static String convertAttributeName(String prefix, String name) {
		StringBuffer result = new StringBuffer();
		String data = name;
		if(name.startsWith(prefix)) {
			data = name.substring(prefix.length());
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
	 * 현재 Parameters객체의 값들을 Bean에 setting한다
	 * 'p_' 로 시작하는 파라미터들만 bean에 setting한다.
	 * 
	 * @param clazz
	 * @return
	 */
	public Object populate(Class<?> clazz) {
		return populate("p_", clazz);
	}
	
	/**
	 * 현재 Parameters객체의 값들을 Bean에 setting한다
	 * 지정한 prefix로 시작하는 파라미터들만 bean에 setting한다.
	 * 
	 * @param prefix
	 * @param clazz
	 * @return
	 */
	public Object populate(String prefix, Class<?> clazz) {
		Object obj = null;
		try {
			obj = clazz.newInstance();
            
			return populate(prefix, obj);
    
		} catch(InstantiationException ignored){
    
		} catch(IllegalAccessException ignored){
        
		}
		return obj;
	}
	
	/**
	 * 현재 Parameters객체의 값들을 Bean에 setting한다
	 * 'p_' 로 시작하는 파라미터들만 bean에 setting한다.
	 * 
	 * @param targetObject
	 * @return
	 */
	public Object populate(Object targetObject) {
		return populate("p_", targetObject);
	}

	/**
	 * 현재 Parameters객체의 값들을 Bean에 setting한다
	 * 지정한 prefix로 시작하는 파라미터들만 bean에 setting한다.
	 * 
	 * @param prefix
	 * @param targetObject
	 * @return
	 */
	public Object populate(String prefix, Object targetObject) {
		if(targetObject==null) {
			logger.error("Parameters.populate() : TargetObject is NULL");
			return null;
		}
		Iterator<?> iter = this.keySet().iterator();
		while(iter.hasNext()) {
			String name  = String.valueOf(iter.next());
			Object value = get(name);
			if(name!=null && name.indexOf(prefix)==0) {
				String attributeName  = convertAttributeName(prefix, name);
				try {
					// 여기서 유형별로 변환해서 setter를 호출한다
					PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(targetObject, attributeName);
					boolean f = false;
					if(pd!=null && value!=null && String.valueOf(value).trim().length()>0) {
						Class<?> clazz = pd.getPropertyType();
						if(clazz!=null) {
							String clazz_name = clazz.getName();
							// 날짜일 경우 파싱하여 이를 써먹는다
							if(clazz_name!=null && (clazz_name.indexOf("Calendar")>=0 || clazz_name.indexOf("Date")>=0 )) {
								String date  = String.valueOf(value).trim();
								String datePattern = "";
								int len = date.length();

								// 구분자 판단
								char delim = 0;
								if(date.indexOf('-')>0) delim = '-'; 
								else if(date.indexOf('/')>0) delim = '/';
								else if(date.indexOf('.')>0) delim = '.';
								
								if(delim!=0) {
									switch (len) {
										// yyyy-MM
										case 7 :
											datePattern = "yyyy"+delim+"MM";
											break;

										// yyyy-MM-dd
										case 10 :
											datePattern = "yyyy"+delim+"MM"+delim+"dd";
											break;

										// yyyy-MM-dd HH
										case 13 :
											datePattern = "yyyy"+delim+"MM"+delim+"dd HH";
											break;

										// yyyy-MM-dd HH:mm
										case 16 :
											datePattern = "yyyy"+delim+"MM"+delim+"dd HH:mm";
											break;

										// yyyy-MM-dd HH:mm:ss
										case 19 :
											datePattern = "yyyy"+delim+"MM"+delim+"dd HH:mm:ss";
											break;

										// yyyy-MM-dd HH:mm:ss:S
										case 21 :
											datePattern = "yyyy"+delim+"MM"+delim+"dd HH:mm:ss.S";
											break;

										// yyyy-MM-dd HH:mm:ss:SS
										case 22 :
											datePattern = "yyyy"+delim+"MM"+delim+"dd HH:mm:ss.SS";
											break;

										// yyyy-MM-dd HH:mm:ss:SSS
										case 23 :
											datePattern = "yyyy"+delim+"MM"+delim+"dd HH:mm:ss.SSS";
											break;

									}
									
								} else {
									switch(len) {
										// yyyy
										case 4:
											datePattern = "yyyy";
											break;
									
										// yyyyMM
										case 6:
											datePattern = "yyyyMM";
											break;
									
										// yyyyMMdd
										case 8:
											datePattern = "yyyyMMdd";
											break;
									
										// yyyyMMddHH
										case 10:
											datePattern = "yyyyMMddHH";
											break;
									
										// yyyyMMddHHmm
										case 12:
											datePattern = "yyyyMMddHHmm";
											break;
									
										// yyyyMMddHHmmss
										case 14:
											datePattern = "yyyyMMddHHmmss";
											break;

										// yyyyMMddHHmmssS
										case 15:
											datePattern = "yyyyMMddHHmmssS";
											break;

										// yyyyMMddHHmmssSS
										case 16:
											datePattern = "yyyyMMddHHmmssSS";
											break;
									
										// yyyyMMddHHmmssSSS
										case 17:
											datePattern = "yyyyMMddHHmmssSSS";
											break;
									}
									
								}

								if(datePattern.length()>0) {
									if(clazz_name.indexOf("Calendar")>=0) {
										Calendar cal = new GregorianCalendar();
										cal.setTime( DateUtils.parseDate(date, new String[]{datePattern}) );
										LocaleBeanUtils.setProperty(targetObject, attributeName, cal);
									} else {
										BeanUtils.setProperty(targetObject, attributeName, DateUtils.parseDate(date, new String[]{datePattern}));
									}
								}
								f = true;
							}else if(clazz.isPrimitive()){
								BeanUtils.setProperty(targetObject, attributeName, value);
								f = true;
							}
						}
					}
					if(!f) {
						if(value!=null && value.toString().length()==0) value = null;
						PropertyUtils.setProperty(targetObject, attributeName, value);
					}

				} catch(ParseException e) {
					logger.debug(name+"["+attributeName+"] ParseException "+targetObject.getClass().getName());

				} catch(IllegalAccessException ignored) {
					logger.debug(name+"["+attributeName+"] is not allowed attribute for "+targetObject.getClass().getName());

				} catch(InvocationTargetException ignored) {
					logger.debug(name+"["+attributeName+"] is not a appropriate attribute for "+targetObject.getClass().getName());

				} catch(NoSuchMethodException e) {
					logger.debug(name+"["+attributeName+"] is not attribute for "+targetObject.getClass().getName());

				} catch(IllegalArgumentException e) {
					logger.debug(name+"["+attributeName+"] Method invocation failed. argument type mismatch : "+targetObject.getClass().getName());
				}
			}
		}
		return targetObject;
	}

	/**
	 * name의 값(String)을 반환 
	 * 만약 String[] 이면 첫번째 요소를 반환
	 * 
	 * @param name
	 * @return
	 */
	public String getString(String name) {
		return getString(name, "");
	}

	/**
	 * name의 값(String)을 반환 
	 * 만약 String[] 이면 첫번째 요소를 반환
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getString(String name, String defaultValue) {
		if (!this.containsKey(name)) return defaultValue;
		Object tmpObj = this.get(name);
		if (tmpObj==null) return defaultValue;

		if (tmpObj instanceof String) {
		    if( ((String)tmpObj).length()==0 ) return defaultValue;
			return (String)tmpObj;

		} else if (tmpObj instanceof String[]) {
			String[] tmpArr = (String[])tmpObj;
			if (tmpArr.length>0 && tmpArr[0]!=null) return tmpArr[0];
			else return defaultValue;

		} else if (tmpObj instanceof Object[]) {
			Object[] objs = (Object[])tmpObj;
			if( objs.length>0 && objs[0]!=null ) {
			    if(String.valueOf(objs[0]).length()==0) return defaultValue;
				return  String.valueOf(objs[0]);
			} else {
				return defaultValue;
			}

		} else {
		    if(String.valueOf(tmpObj).length()==0) return defaultValue;
			return String.valueOf(tmpObj);
		}
	}

	/**
	 * name의 값(String[])를 반환
	 * 만약 String 이면 요소 1개짜리 String[]를 반환
	 * 
	 * @param name
	 * @return
	 */
	public String[] getStringArray(String name) {
		String[] values = null;
		if (!this.containsKey(name)) return null;
		Object tmpObj = this.get(name);
		if (tmpObj==null) return null;

		if (tmpObj instanceof String[]) {
			values = (String[])tmpObj;

		} else if (tmpObj instanceof String) {
			values = new String[1];
			values[0] = (String)tmpObj;

		} else if (tmpObj instanceof Object[]) {
			Object[] objs = (Object[])tmpObj;
			values = new String[objs.length];
			for(int k=0; k<values.length; k++) {
				values[k] = String.valueOf(objs[k]);
			}
		}
		return values;
	}
	
	/**
	 * name의 값을 int값으로 반환
	 * 
	 * @param name
	 * @return
	 */
	public int getInt(String name) {
		return getInt(name, 0);
	}
	
	/**
	 * name의 값을 int값으로 반환
	 * 
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	public int getInt(String name, int defaultVal) {
		int retVal = defaultVal;
		Object tmpObj = this.get(name);
		if(tmpObj==null) return defaultVal;
		try {
			if(tmpObj instanceof Object[]) {
				retVal = Integer.parseInt( String.valueOf(((Object[])tmpObj)[0]) );
			} else {
				if(String.valueOf(tmpObj).trim().length()<1) {
					retVal = defaultVal;
				} else {
					retVal = Integer.parseInt( String.valueOf(tmpObj).trim() );					
				}
				
			}
			
		} catch(Exception e) {
			logger.error("[Parameters.getInt("+name+", "+defaultVal+")] "+e);
			retVal = defaultVal;
		}
		return retVal;
	}

	/**
	 * name의 값을 int[] 값으로 반환
	 * 
	 * @param name
	 * @return
	 */
	public int[] getIntArray(String name) {
		return getIntArray(name, 0);
	}
	
	/**
	 * name의 값을 int[] 값으로 반환
	 * 
	 * @param name
	 * @param defaultVal
	 * @return null은 return이 되지 않고 요소1개짜리 defaultValue가 return된다.
	 */
	public int[] getIntArray(String name, int defaultVal) {
		int[] retVals = null;
		Object tmpObj = this.get(name);
		if(tmpObj==null) return new int[]{defaultVal};
		
		if(tmpObj instanceof int[]) {
			retVals = (int[])tmpObj;
			
		} else if(tmpObj instanceof Integer[]) {
			Integer[] objs = (Integer[])tmpObj;
			retVals = new int[objs.length];
			for(int k=0; k<objs.length; k++) {
				retVals[k] = objs[k].intValue();
			}
			
		} else if(tmpObj instanceof Object[]) {
			Object[] objs = (Object[])tmpObj;
			retVals = new int[objs.length];
			for(int k=0; k<objs.length; k++) {
				try {
					retVals[k] = Integer.parseInt( String.valueOf(objs[k]) );
				} catch(Exception e) {
					logger.error("[Parameters.getIntValues("+name+") : "+objs[k]+"] "+e.getMessage());
					retVals[k] = defaultVal;
				}
			}

		} else if(tmpObj instanceof Integer) {
			retVals = new int[]{ ((Integer)tmpObj).intValue() };

		} else {
			retVals = new int[1];
			try {
				retVals[0] = Integer.parseInt(String.valueOf(tmpObj));
				
				
			} catch(Exception e) {
				logger.error("[Parameters.getIntValues("+name+", "+defaultVal+")] "+e.getMessage());
				retVals[0] = defaultVal;
			}

		}
		return retVals;
	}

	public long getLong(String name) {
		return getLong(name, 0);
	}
	
	public long getLong(String name, long defaultVal) {
		long retVal = defaultVal;
		Object tmpObj = this.get(name);
		if(tmpObj==null) return defaultVal;
		try {
			if(tmpObj instanceof Object[]) {
				retVal = Integer.parseInt( String.valueOf(((Object[])tmpObj)[0]) );
			} else {
				if(String.valueOf(tmpObj).trim().length()<1) {
					retVal = defaultVal;
				} else {
					retVal = Long.parseLong( String.valueOf(tmpObj).trim() );					
				}
				
			}
			
		} catch(Exception e) {
			logger.error("[Parameters.getLong("+name+", "+defaultVal+")] "+e);
			retVal = defaultVal;
		}
		return retVal;
	}
	
	/**
	 * name의 값을 반환
	 * 
	 * @param name
	 * @return
	 */
	public Object getValue(String name) {
		return this.get(name); 
	}
	
	/**
	 * name의 값을 반환
	 * 
	 * @param name
	 * @return
	 */
	public Object[] getValues(String name) {
		Object tmpObj = this.get(name);
		if(tmpObj==null) return null;
		
		if(tmpObj instanceof Object[]) {
			return (Object[])tmpObj;
		} else {
			return new Object[]{tmpObj};
		}
	}

	/**
	 * 반환할 값의 데이터 갯수를 반환
	 * 
	 * @param name
	 * @return
	 */
	public int getSize(String name) {
		Object tmpObj = this.get(name);
		if(tmpObj instanceof Object[]) {
			return ((Object[])tmpObj).length;
		} else {
			return 1;
		}
	}

	/**
	 * 값을 추가
	 * 
	 * @param name
	 * @param value
	 */
	public void addValue(K name, V value) {
		this.put(name, value);
	}

//	/**
//	 * 값을 추가(객체배열형태를 추가)
//	 * 
//	 * @param name
//	 * @param values
//	 */
//	public void addValue(K name, Object[] values) {
//		this.put(name, values);
//	}

	/**
	 * name의 값을 제거
	 * 
	 * @param name
	 */
	public void removeValue(String name) {
		if (this.containsKey(name)) this.remove(name);
	}

	/** 해당 Key값만을 포함하여 구성하는 모드 */
	public static int KEY_INCLUDE = 1;

	/** 해당 Key값을 제외하고 나머지로 구성하는 모드 */
	public static int KEY_EXCLUDE = 2;
	
	/**
	 * 존재하는 값들을 QueryString으로 변환하여 반환
	 * 
	 * @param keys     추가 또는 제외할 값들의 키
	 * @param mode     추가/제외 모드 : KEY_INCLUDE, KEY_EXCLUDE
	 * @param encoding Character encoding
	 * @return
	 */
	public String toQueryString(String[] keys, int mode, String encoding) {
		
		StringBuffer buff = new StringBuffer();
		Iterator<?> iter = this.keySet().iterator();
		while(iter.hasNext()) {
			String key = String.valueOf(iter.next());
			Object obj = get(key);
			
			boolean hasKey = false;
			for(int i=0; i<keys.length; i++) if( key.equals(keys[i]) ) {hasKey = true; break;} 

			// 포함되는 것들(hasKey==true)만 추가한다.
			if(mode == KEY_INCLUDE) {
				if(hasKey) {
					if(obj instanceof Object[]) {
						Object[] objArr = (Object[]) obj;
						for(int i=0; i<objArr.length; i++) {
							try {
								if(encoding!=null && encoding.length()>0) {
									buff.append(key+"="+URLEncoder.encode(ObjectUtils.defaultIfNull(objArr[i],"").toString(), encoding)+"&");
								} else {
									buff.append(key+"="+URLEncoder.encode(ObjectUtils.defaultIfNull(objArr[i],"").toString(), defaultCharsetEncoding)+"&");
								}
							}catch(Exception ig){}
						}

					} else {
						buff.append(key+"="+getString(key)+"\r\n");
					}
				}
				
			// 포함되지 않는 것들(hasKey==false)만 추가한다.
			} else if(mode == KEY_EXCLUDE) {
				if(!hasKey) {
					if(obj instanceof Object[]) {
						Object[] objArr = (Object[]) obj;
						for(int i=0; i<objArr.length; i++) {
							try {
								if(encoding!=null && encoding.length()>0) {
									buff.append(key+"="+URLEncoder.encode(ObjectUtils.defaultIfNull(objArr[i],"").toString(), encoding)+"&");
								} else {
									buff.append(key+"="+URLEncoder.encode(ObjectUtils.defaultIfNull(objArr[i],"").toString(), defaultCharsetEncoding)+"&");
								}
							}catch(Exception ig){}
						}

					} else {
						buff.append(key+"="+getString(key)+"\r\n");
					}
				}
			}

		}
		return buff.toString();
	}
	
	/**
	 * 존재하는 값들을 QueryString으로 변환하여 반���
	 * 
	 * @param keys
	 * @param mode
	 * @return
	 */
	public String toQueryString(String[] keys, int mode) {
		return toQueryString(keys, mode, defaultCharsetEncoding);
	}
	
	/**
	 * 존재하는 값들을 QueryString으로 변환하여 반환
	 * 
	 * @param encoding
	 * @return
	 */
	public String toQueryString(String encoding) {
		StringBuffer buff = new StringBuffer();
		Iterator<?> iter = this.keySet().iterator();
		while(iter.hasNext()) {
			String key = String.valueOf(iter.next());
			Object obj = get(key);
			
			if(obj instanceof Object[]) {
				Object[] objArr = (Object[]) obj;
				for(int i=0; i<objArr.length; i++) {
					try {
						if(encoding!=null && encoding.length()>0) {
							buff.append(key+"="+URLEncoder.encode(ObjectUtils.defaultIfNull(objArr[i],"").toString(), encoding)+"&");
						} else {
							buff.append(key+"="+URLEncoder.encode(ObjectUtils.defaultIfNull(objArr[i],"").toString(), defaultCharsetEncoding)+"&");
						}
					}catch(Exception ig){}
				}
			} else {
				buff.append(key+"="+getString(key)+"\r\n");
			}				
		}
		return buff.toString();
	}
	
	/**
	 * 존재하는 값들을 QueryString으로 변환하여 반환
	 * 
	 * @return
	 */
	public String toQueryString() {
		return toQueryString(defaultCharsetEncoding);
	}
	
	/**
	 * 존재하는 값들을 &lt;input type=hidden&gt; tag로 변환하여 반환
	 * 
	 * @param keys     추가 또는 제외할 값들의 키
	 * @param mode     추가/제외 모드 : KEY_INCLUDE, KEY_EXCLUDE
	 * @param encoding Character encoding
	 * @return
	 */
	public String toHiddenFields(String[] keys, int mode, String encoding) {
		StringBuffer buff = new StringBuffer();
		Iterator<?> iter = this.keySet().iterator();
		while(iter.hasNext()) {
			String key = String.valueOf(iter.next());
			Object obj = get(key);
			
			boolean hasKey = false;
			for(int i=0; i<keys.length; i++) if( key.equals(keys[i]) ) {hasKey = true; break;} 

			// 포함되는 것들(hasKey==true)만 추가한다.
			if(mode == KEY_INCLUDE) {
				if(hasKey) {
					if(obj instanceof Object[]) {
						Object[] objArr = (Object[]) obj;
						for(int i=0; i<objArr.length; i++) {
							buff.append("<input type=hidden name=\""+key+"\" value=\""+ObjectUtils.defaultIfNull(objArr[i],"")+"\">\n");
						}

					} else {
						buff.append("<input type=hidden name=\""+key+"\" value=\""+getString(key)+"\">\n");
					}
				}
				
			// 포함되지 않는 것들(hasKey==false)만 추가한다.
			} else if(mode == KEY_EXCLUDE) {
				if(!hasKey) {
					if(obj instanceof Object[]) {
						Object[] objArr = (Object[]) obj;
						for(int i=0; i<objArr.length; i++) {
							buff.append("<input type=hidden name=\""+key+"\" value=\""+ObjectUtils.defaultIfNull(objArr[i],"")+"\">\n");
						}

					} else {
						buff.append("<input type=hidden name=\""+key+"\" value=\""+getString(key)+"\">\n");
					}
				}
			}

		}
		return buff.toString();
	}	

	/**
	 * 존재하는 값들을 &lt;input type=hidden&gt; tag로 변환하여 반환
	 * 
	 * @param keys     추가 또는 제외할 값들의 키
	 * @param mode     추가/제외 모드 : KEY_INCLUDE, KEY_EXCLUDE
	 * @return
	 */
	public String toHiddenFields(String[] keys, int mode) {
		return toHiddenFields(keys, mode, defaultCharsetEncoding);
	}

	/**
	 * 존재하는 값들을 &lt;input type=hidden&gt; tag로 변환하여 반환
	 * 
	 * @param encoding Character encoding
	 * @return
	 */
	public String toHiddenFields(String encoding) {
		StringBuffer buff = new StringBuffer();
		Iterator<?> iter = this.keySet().iterator();
		while(iter.hasNext()) {
			String key = String.valueOf(iter.next());
			Object obj = get(key);
			
			if(obj instanceof Object[]) {
				Object[] objArr = (Object[]) obj;
				for(int i=0; i<objArr.length; i++) {
					buff.append("<input type=hidden name=\""+key+"\" value=\""+ObjectUtils.defaultIfNull(objArr[i],"")+"\">\n");
				}

			} else {
				buff.append("<input type=hidden name=\""+key+"\" value=\""+getString(key)+"\">\n");
			}			
		}
		return buff.toString();
	}

	/**
	 * 존재하는 값들을 &lt;input type=hidden&gt; tag로 변환하여 반환
	 * 
	 * @return
	 */
	public String toHiddenFields() {
		return toHiddenFields(defaultCharsetEncoding);
	}


	/**
	 * 현재 모든 값의 Key를 반환한다.
	 * @return
	 */
	public Set<?> getKeys() {
		return this.keySet();
	}

	/**
	 * toString method
	 */
	public String toString() {
		StringBuffer buff = new StringBuffer();
		Iterator<?> iter = this.keySet().iterator();
		buff.append("================ (Parameters) =====================\r\n");
		while(iter.hasNext()) {
			String key = String.valueOf(iter.next());
			Object obj = get(key);
			if(obj instanceof Object[]) {
				Object[] objArr = (Object[]) obj;
				for(int i=0; i<objArr.length; i++) {
					buff.append(key+"="+String.valueOf(objArr[i])+"\r\n");
				}
			} else {
				buff.append(key+"="+getString(key)+"\r\n");
			}
		}
		buff.append("===================================================\r\n");
		return buff.toString();
	}

}
