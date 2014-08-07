package com.nuri.common.service;


/**
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   GenericManager.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2013. 07. 19.      이재열<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author JaeYeol Lee
 *
 */
public interface GenericManager {
	
	/**
	 * Try to resolve the message. Return default message if no message was found.
	 * 
	 * @param code the code to lookup up, such as 'calculator.noRateSet'. Users of
	 *        this class are encouraged to base message names on the relevant fully
	 *        qualified class name, thus avoiding conflict and ensuring maximum clarity.
	 * @param defaultMessage String to return if the lookup fails
	 * 
	 * @return the resolved message if the lookup was successful;
	 *         otherwise the default message passed as a parameter
	 * 
	 * @see java.text.MessageFormat
	 */
	public String getMessage(String code, String defaultMessage);
	
	/**
	 * Try to resolve the message. 
	 * 
	 * @param code the code to lookup up, such as 'calculator.noRateSet'. Users of
	 *        this class are encouraged to base message names on the relevant fully
	 *        qualified class name, thus avoiding conflict and ensuring maximum clarity.
	 * 
	 * @return the resolved message if the lookup was successful
	 * 
	 * @see java.text.MessageFormat
	 */
	public String getMessage(String code);
	
	/**
	 * Try to resolve the message. 
	 * 
	 * @param code the code to lookup up, such as 'calculator.noRateSet'. Users of
	 *        this class are encouraged to base message names on the relevant fully
	 *        qualified class name, thus avoiding conflict and ensuring maximum clarity.
	 * @param args array of arguments that will be filled in for params within
	 *        the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
	 *        or <code>null</code> if none.
	 * 
	 * @return the resolved message if the lookup was successful
	 * 
	 * @see java.text.MessageFormat
	 */
	public String getMessage(String code, Object[] args);
	
	/**
	 * Try to resolve the message. Return default message if no message was found.
	 * 
	 * @param code the code to lookup up, such as 'calculator.noRateSet'. Users of
	 *        this class are encouraged to base message names on the relevant fully
	 *        qualified class name, thus avoiding conflict and ensuring maximum clarity.
	 * @param args array of arguments that will be filled in for params within
	 *        the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
	 *        or <code>null</code> if none.
	 * @param defaultMessage String to return if the lookup fails
	 * 
	 * @return the resolved message if the lookup was successful;
	 *         otherwise the default message passed as a parameter
	 * 
	 * @see java.text.MessageFormat
	 */
	public String getMessage(String code, Object[] args, String defaultMesasge);



}

