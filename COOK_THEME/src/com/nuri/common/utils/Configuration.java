package com.nuri.common.utils;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * =============================================================================
 *            프로젝트명 :   np_VerseOfDay
 *            화  일  명 :   Configuration.java
 *            기      능 :   기본 환경설정 Load Class
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
public class Configuration {

	private static Properties props   = null;
	private static String configFile  = "/config.ini";
	private Log log = LogFactory.getLog(Configuration.class);
	
	
	private static class SingletonHolder {
		private static Configuration instance = new Configuration();
	}

	private Configuration() {
		init();
	}

	public static Configuration getInstance() {
		return SingletonHolder.instance;
	}
	
	public void destory() {
		SingletonHolder.instance = null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$ "+getClass().getSimpleName()+"["+hashCode()+"].finalize() called $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		super.finalize();
	}

	/**
	 *
	 *
	 * @param name
	 * @return
	 */
	public static Configuration getInstance(String name) {
		if(name!=null && !name.equals(configFile)) {
			synchronized (configFile) {
				configFile = "/"+name;
				if(SingletonHolder.instance!=null) SingletonHolder.instance.init();
				else SingletonHolder.instance = new Configuration();
			}
			return SingletonHolder.instance;

		} else {
			return SingletonHolder.instance;
		}
	}

	/**
	 * refresh method
	 */
	public synchronized void reset() {
		init();
	}

	/**
	 * 초기화
	 */
	private void init() {
		props = new Properties();
		try {
			// 만약 System Property로 config.path 가 설정이 되었다면 그걸 쓴다.
			String tmpVal = System.getProperty("config.path");
			if (tmpVal != null && !tmpVal.equals("") && tmpVal.indexOf('/') != -1) {
				File tmpF = new File(tmpVal + configFile);
				if(tmpF.exists()) {
					configFile = tmpVal + configFile;
				} else {
					configFile = tmpVal;
				}

				log.info("[Configuration] configFile : " + configFile);
				props.load(new FileInputStream(new File(configFile)));

			// if 'config.path' doesn't include '/', get property file from classpath
			} else if(tmpVal != null && !tmpVal.equals("") && tmpVal.indexOf('/') == -1)	{
				configFile = "/"+tmpVal;
				java.net.URL propsURL = getClass().getResource(configFile);
				if (propsURL == null) {
					SingletonHolder.instance = null;
					log.error("[Configuration] Can't find '" + configFile + "' File in your classpath.");

				} else {
					log.info("[Configuration] configFile : " + propsURL);
					props.load(propsURL.openStream());
				}

			// if 'config.path' System proerty is null, get property file from classpath
			} else {
				java.net.URL propsURL = getClass().getResource(configFile);
				if (propsURL == null) {
					SingletonHolder.instance = null;
					log.error("[Configuration] Can't find '" + configFile + "' File in your classpath.");

				} else {
					log.info("[Configuration] configFile : " + propsURL);
					props.load(propsURL.openStream());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			SingletonHolder.instance = null;
			log.error(
				"[Configuration] Exception occured!! Can't load '" + configFile + "' File..." + e.getMessage());
		}
	}

	/**
	 * 지정된 키 값으로 존재하는 모든 프로퍼티를 가져온다.
	 * suffix가 .0 , .1 , .2 , .3 , ... 과 같은 형식으로 된 모든 프로퍼티를 가져와 String[] 로 반환 한다.
	 *
	 * @param name
	 * @return
	 */
	public String[] getStringArray(String name) {
		ArrayList<String> values = new ArrayList<String>();
		if(getString(name).trim().length()>0) {
			values.add(getString(name).trim());
		}
		String line = "";
		int num = 0;
		while( (line = getString(name+"."+num)).trim().length()>0 ) {
			num++;
			values.add(line);
		}
		return (String[]) values.toArray(new String[values.size()]);
	}

	/**
	 * 프로퍼티 값을 가져옴
	 *
	 * @param name
	 * @return
	 */
	public String getString(String name) {
		return convertCharset(props.getProperty(name));
	}

	/**
	 * 프로퍼티 값을 가져옴. 값이 없으면 defaultValue를 반환
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getString(String name, String defaultValue) {
		String retVal = getString(name);
		if (retVal.length()==0) return defaultValue;
		return retVal;
	}

	/**
	 * 프로퍼티 값을 int로 가져옴
	 *
	 * @param name
	 * @return
	 */
	public int getInt(String name) {
		return getInt(name, 0);
	}

	/**
	 * 프로퍼티 값을 int로 가져옴. 값이 없으면 defaultValue를 반환
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String name, int defaultValue) {
		int retVal = defaultValue;
		try {
			retVal = Integer.parseInt(props.getProperty(name));
		} catch (Exception e) {
			log.error("[Configuration.getInt(" + name + "," + defaultValue + ")] " + e.getMessage());
			retVal = defaultValue;
		}
		return retVal;
	}

	/**
	 * 프로퍼티 값을 long로 가져옴
	 *
	 * @param name
	 * @return
	 */
	public long getLong(String name) {
		return getLong(name, 0);
	}

	/**
	 * 프로퍼티 값을 long으로 가져옴. 값이 없으면 defaultValue를 반환
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public long getLong(String name, long defaultValue) {
		long retVal = defaultValue;
		try {
			retVal = Long.parseLong(props.getProperty(name));
		} catch (Exception e) {
			log.error("[Configuration.getLong(" + name + "," + defaultValue + ")] " + e.getMessage());
			retVal = defaultValue;
		}
		return retVal;
	}

	/**
	 * 프로퍼티 값을 double로 가져옴
	 *
	 * @param name
	 * @return
	 */
	public double getDouble(String name) {
		return getLong(name, 0);
	}

	/**
	 * 프로퍼티 값을 double으로 가져옴. 값이 없으면 defaultValue를 반환
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public double getDouble(String name, double defaultValue) {
	    double retVal = defaultValue;
		try {
			retVal = Double.parseDouble(props.getProperty(name));
		} catch (Exception e) {
			log.error("[Configuration.getDouble(" + name + "," + defaultValue + ")] " + e.getMessage());
			retVal = defaultValue;
		}
		return retVal;
	}

	/**
	 * 프로퍼티 값을 boolean으로 가져옴.
	 * value값이 true, yes, y, 1 라면 true
	 * 그 외에는 false
	 *
	 * @param name
	 * @return
	 */
	public boolean getBoolean(String name, boolean defaultValue) {
		boolean flag = defaultValue;
		String val = props.getProperty(name);
		if (val != null) {
			val = val.trim().toLowerCase();
			if (val.equals("true") || val.equals("y") || val.equals("yes") || val.equals("1")) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	/**
	 * 프로퍼티에서 가져올때 강제로 8859_1로 가져와서
	 * System Default Charset으로 변환하는 메서드
	 *
	 * @param str
	 * @return
	 */
	private String convertCharset(String str) {
		String charset = props.getProperty("default.charset");
		if(charset==null || charset.length()==0) charset=null;
		
		String retVal = "";
		if (str == null)
			return "";
		try {
			if(charset==null)
				retVal = new String(str.getBytes("8859_1"));
			else
				retVal = new String(str.getBytes("8859_1"), charset);
		} catch (Exception e) {
			log.error("[Configuration.convertCharset(" + str + ")] " + e.getMessage());
			retVal = "";
		}
		return retVal;
	}

	public String toString() {
		return props!=null?props.toString():null;
	}
	
	/**
	 * 커스텀 태그(함수)에서 사용하기 위해서 임시로 만든 메서드
	 * 나중에 Configuration class를 변경할때, 커스텀 태그(함수)도 고려해야함 
	 * 
	 * @param name
	 * @return
	 */
	public static String get(String name) {
		return getInstance().getString(name);
	}

	/**
	 * 커스텀 태그(함수)에서 사용하기 위해서 임시로 만든 메서드
	 * 나중에 Configuration class를 변경할때, 커스텀 태그(함수)도 고려해야함 
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String get(String name, String defaultValue) {
		return getInstance().getString(name, defaultValue);
	}
	
	/**
	 * @return
	 */
	public static String getConfigFile() {
		return configFile;
	}

}
