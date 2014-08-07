package com.nuri.common.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Message {
	static Log logger = LogFactory.getLog(Message.class);
	
	/**
	 * Default Constructor
	 */
	public Message() {}

	private static ResourceBundle bundle = null;

	/**
	 * 원하는 메시지를 가져옴
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		String value = null;
		try {
			String defaultCharset = Configuration.getInstance().getString("default.charset").trim();
			if(defaultCharset.length()==0) {
				defaultCharset = null;
			}

			//value = getResourceBundle().getString(key);
			try {
				if(defaultCharset==null)
					value = new String(getResourceBundle().getString(key).getBytes("8859_1"));
				else
					value = new String(getResourceBundle().getString(key).getBytes("8859_1"), defaultCharset);

			} catch(Exception e) {
				logger.error("Message.getString("+key+") : "+e.getMessage());
				value = getResourceBundle().getString(key);
			}

		} catch(MissingResourceException e) {
			logger.error("java.util.MissingResourceException: Couldn't find value for: " + key);
		}
		if(value == null) {
			value = "Could not find resource: " + key + "  ";
		}
		return value;
	}
	
	/**
	 * 원하는 메시지를 가져옴
	 * 
	 * @param key
	 * @param encoding 반환할 문자셋
	 * @return
	 */
	public static String getString(String key, String charset) {
		String value = null;
		try {
			try {
				value = new String(getResourceBundle().getString(key).getBytes("8859_1"), charset);

			} catch(Exception e) {
				logger.error("Message.getString("+key+", "+charset+") : "+e.getMessage());
				value = getResourceBundle().getString(key);
			}

		} catch(MissingResourceException e) {
			logger.error("java.util.MissingResourceException: Couldn't find value for: " + key);
		}
		if(value == null) {
			value = "Could not find resource: " + key + "  ";
		}
		return value;
	}
	
	/**
	 * ResourceBundle 반환
	 * 
	 * @return
	 */
	private static ResourceBundle getResourceBundle() {
		if(bundle == null) {
			bundle = ResourceBundle.getBundle(Configuration.getInstance().getString("message.property.name"));
		}
		return bundle;
	}
}