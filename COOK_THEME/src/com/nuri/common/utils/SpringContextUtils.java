package com.nuri.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * =============================================================================
 *            프로젝트명 	:   nPine_CRM
 *            화  일  명 	:   SpringContextUtils.java
 *            기      능 	:   
 *            인      수 	:   
 *            특이  사항 	:
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       			변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2011. 6. 30.      		이재열<jyeory@gmail.com>         		최 초 작 성                      
 *==============================================================================
 * 
 * @author JaeYeol Lee
 *
 */
public class SpringContextUtils implements ApplicationContextAware {

	protected final Log logger = LogFactory.getLog(getClass());

	protected Map<String, Object> cachedBeans = new HashMap<String, Object>();
	
	private ApplicationContext ctx;
	
	private static class SingletonHolder {
		static SpringContextUtils instance = new SpringContextUtils();
	}
	
	private SpringContextUtils(){}
	
	public static SpringContextUtils getInstance() {
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
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ctx = applicationContext;
	}
	
	/**
	 * Managed Bean 반환
	 * 
	 * @param sctx
	 * @param beanId
	 * @return
	 */
	public static Object getBean(String beanId) {
		Map<String, Object> beans = getInstance().cachedBeans;
		ApplicationContext ctx = getInstance().ctx;
		Object bean = beans.get(beanId);
		if(bean!=null) return bean;
		if(ctx!=null) {
			bean = ctx.getBean(beanId);
			beans.put(beanId, bean);
		}
		return bean;
	}

}
