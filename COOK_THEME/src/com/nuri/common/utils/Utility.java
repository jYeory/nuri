/*
 *
 * @(#) Utility.java
 * @(#) Created on : 2004. 8. 24.
 *
 * Copyright 2002-2004 Autobrain Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Autobrain Inc.
 * Use is subject to license terms.
 *
 * For more information on the Autobrain Inc., please see
 * <http://www.autobrain.net>.
 *
 * @author Dongsu Lee <crazygt@crazygt.com>
 */
package com.nuri.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 다양한 유틸리티 메서드들의 모음 클래스
 *
 * @author Dongsu Lee <crazygt@crazygt.com>
 *
 * Utility
 */
public class Utility {

	/**
	 * Console로 부터 1라인을 읽는다.
	 *
	 * @param defaultVal
	 * @return
	 * @throws IOException
	 */
	public static String readFromConsole(String defaultVal) throws IOException {
		String line = null;
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
		line = br.readLine();
		if(line ==null || line.trim().length()==0) {
			line = defaultVal;
		}
		return line;
	}

	/**
	 * 해당 객체의 세부 내용을 조회할 경우 사용하는 메서드
	 *
	 * @param obj
	 * @return
	 */
	public static String describe(Object obj) {
		if(obj==null) return null;
		String descrb = null;
		try {
			descrb = BeanUtils.describe(obj).toString();
		} catch(Exception e) {
			return obj.toString();
		}
		return descrb;
	}

	public static String toDate(String pattern){
		return DateFormatUtils.format(new Date(), pattern);
	}

	public static Date stringToDate(String dateString) throws ParseException {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(dateString);
	}

	public static String getActiveTime(String str){
		String result = "";
		if(str != null){
			result = str.substring(0,2)+":"+str.substring(2,4);
		}
		return result;
	}
	// 입력예) addDate("2000-10-12", 2, -2, -1);
	// 기준날짜에서 2년더하고, 2월 빼고, 1일뺀다.

	// 입력예) addDate("2000-05-31", -1, -1, -1); => 1999-04-30 년월일연산후 평가하기때문에.
	// 예외상황
	// 월과 일을 같이 연산할때는 월호출후, 일후출할것(두번호출).
	// addDate( addDate("200-05-31",-1,-1,0),0,0,-1 ) => 1999-04-29 일변화없이 월이변할때는 말이체크하기때문에.
	public static  String addDate ( String curr_dt, int y, int m, int d){
	   int[] array = { 31,28,31,30,31,30,31,31,30,31,30,31 };

	   try {
		  java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  String yy = curr_dt.substring(0,4);
		  String mm = curr_dt.substring(5,7);
		  String dd = curr_dt.substring(8);
		  int y2 = Integer.parseInt(yy) + y;
		  int m2 = Integer.parseInt(mm) + m;
		  int d2 = Integer.parseInt(dd) + d;

		  array[1] = febdays(Integer.parseInt(yy));
		  /*
			 일을 제외한 달의 연산시 현재가 말일이면, 연산된 달의 말일로 설정한다.
		  */
		  if ( d == 0 && array[ Integer.parseInt(mm)-1] == Integer.parseInt(dd) ){
//			if ( array[ Integer.parseInt(mm)-1] == Integer.parseInt(dd) ){
			 d2 = array[ ( Math.abs(m2) -1) % 12];
		  }
		  return fmt.format( fmt.parse( y2+"-"+m2+"-"+d2 ) );
	   } catch ( Exception ex ){
		  System.out.println(ex);
		  return "";
	   }
	}

	public static int febdays(int y) {
	   if(y%400==0 || ( y%100!=0 && y%4==0) )
		  return 29;
	   else
		  return 28;
	}

	/**
	 * 한주가 월요일부터 일요일로 정의할경우
	 * 입력한 날짜(yyyy-MM-dd)가 속한 주의 월요일(첫째날)을 구한다.
	 *
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfWeek(String date){
		String firstday= "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date dt = formatter.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			calendar.setFirstDayOfWeek(Calendar.MONDAY-1);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			firstday = formatter.format(calendar.getTime());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return firstday;
	}
	/**
	 * 한주를 월요일부터 일요일로 정의할경우
	 * 입력한 날짜(yyyy-MM-dd)가 속한 주의 일요일(마지막날)을 구한다.
	 *
	 * @param date
	 * @return
	 */
	public static String getLastDayOfWeek(String date){
		String lastday = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date dt = formatter.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			calendar.add(Calendar.DATE,6);
			lastday = formatter.format(calendar.getTime());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lastday;
	}

	/**
	 * 입력한 날짜의 처음 시작하는 일자의 요일을 구한다.
	 *
	 * @param day
	 * @return
	 */
	public static int getDayOfFirstWeek(String day){
		int firstWeek = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date dt = formatter.parse(day);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			calendar.set(Calendar.DATE, 1);
			firstWeek = calendar.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return firstWeek;
	}

	/**
	 * 입력한 날짜의 마지막 일자의 요일을 구한다.
	 *
	 * @param day
	 * @return
	 */
	public static int getDayOfLastWeek(String day){
		int lastWeek = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date dt = formatter.parse(day);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			lastWeek = calendar.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastWeek;
	}

	/**
	 * 입력한 날짜의 마지막 일자를 구한다.
	 *
	 * @param day
	 * @return
	 */
	public static int getLastDate(String day){
		int lastDate = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date dt = formatter.parse(day);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			calendar.set(Calendar.DATE, 1);
			lastDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastDate;
	}
	/**
	 * 현재 시간을 Calendar로 리턴한다.
	 *
	 * @param day
	 * @return
	 */
	public static Calendar getCalendar(){
		Calendar calendar = Calendar.getInstance(Locale.KOREA);
		return calendar;
	}

	/**
	 * File을 Link한다.
	 *
	 * @param day
	 * @return
	 */
	public static String getFileLink(HttpServletRequest req, String file, String originalFileName) {
		StringBuffer buff = new StringBuffer();
		if(!file.equals("")){
			file = replaceFileGu(file);
			String fileName = "";
//			String strClient = req.getHeader("User-Agent");
			fileName += file;
			buff.append("<a href=\"javascript:download('"+fileName+"', '"+originalFileName+"')\">");
			buff.append( originalFileName );
			buff.append("</a>");
		}
		return buff.toString();
	}

	/**
	 * File을 이미지로 Link한다.
	 *
	 * @param day
	 * @return
	 */
	public static String getFileLinkImg(HttpServletRequest req, String file, String originalFileName) {
		StringBuffer buff = new StringBuffer();
		if(!file.equals("")){
			file = replaceFileGu(file);
			String fileName = "";
//			String strClient = req.getHeader("User-Agent");

			String Img = "";
			if(file.substring(file.lastIndexOf(".")+1).equals("xls")){
				Img = "<img src=\"/sfa/images/excel.gif\" width=\"16\" height=\"16\" border=\"0\">";
			}else if(file.substring(file.lastIndexOf(".")+1).equals("doc")){
				Img = "<img src=\"/sfa/images/doc.gif\" width=\"16\" height=\"16\" border=\"0\">";
			}else if(file.substring(file.lastIndexOf(".")+1).equals("zip")){
				Img = "<img src=\"/sfa/images/zip.gif\" width=\"16\" height=\"16\" border=\"0\">";
			}else{
				Img = "<img src=\"/sfa/images/txt.gif\" width=\"16\" height=\"16\" border=\"0\">";
			}

			fileName += file;
			buff.append("<a href=\"javascript:download('"+fileName+"', '"+originalFileName+"')\">");
			buff.append( Img );
			buff.append("</a>");
		}
		return buff.toString();
	}

	public static String replaceFileGu(String filename){
		StringBuffer buff = new StringBuffer();
		if(!filename.equals("")){
			String[] arr = StringUtil.explode("\\", filename);
			for (int i = 0; i < arr.length; i++) {
				buff.append(arr[i]);
				if(arr.length-1 != i){
					buff.append("/");
				}
			}
		}
		return buff.toString();
	}

	public static String getFileUploadPath(String path){
		StringBuffer buff = new StringBuffer();
		buff.append(path);
		buff.append(toDate("yyyy"));
		buff.append(java.io.File.separator);
		buff.append(toDate("MM"));
		buff.append(java.io.File.separator);
		return buff.toString();
	}

	public static String getReplyForm(String pos){
	   String rtn = "";
	   if ( pos != null && !pos.equals("0") ){
		  int depth = Integer.parseInt(pos);

		  for ( int i=0; i< depth; i++){
			 rtn += "&nbsp;&nbsp;";
		  }
		  rtn += "<img src='/sfa/images/icon_re1.gif' width='12' height='12' align='absmiddle'>&nbsp;";
	   }
	   return rtn;
	}

	public static  String contentForReply(String content, String name) {
	   if ( content.equals("") ) {
		  return "";
	   }
	   content = name +" 님의 글입니다.\n" + content;
	   String temp = content.trim();
	   int pos = 0;
	   while((pos = temp.indexOf("\n", pos)) != -1) {
		  String left = temp.substring(0, pos + 1);
		  String right = temp.substring(pos + 1, temp.length());
		  temp = left + " > " + right;
		  pos += 1;
	   }
	   return temp;
	}

	public static  String contentWithEditorForReply(String content, String name) {
	   if ( content.equals("") ) {
		  return "";
	   }
	   content = "<br />==================== " + name +" 님의 글입니다. ====================<br />" + content;
	   return content;
	}

	public static String setNewImg(Calendar createDt, Calendar updateDt){
		String result = "";
		String pattern = "yyyyMMdd";
		String screateDt = getDate(createDt, pattern);
		String supdateDt = getDate(updateDt, pattern);
		String today = toDate("yyyyMMdd");
		if (screateDt.equals(today) || supdateDt.equals(today) ){
			result = "<img src='/sfa/images/new.gif' align='absmiddle'>&nbsp;";
		}
		return result;
	}

	public static String setNewImg(Calendar createDt, Calendar updateDt, int term) throws ParseException{
		String result = "";

		Date createdt = null;
		Date updatedt = null;
		Date enddt = null;
		Date enddt2 = null;
		String datePattern = "yyyy-MM-dd";

		//기준일
		Calendar todateDt = Calendar.getInstance();
		Date todatedt = StringUtil.toDate(DateFormatUtils.format(todateDt.getTime(),datePattern),datePattern);

		// 입력일
		if(createDt != null){
			createdt = StringUtil.toDate(DateFormatUtils.format(createDt.getTime(),datePattern),datePattern);
			enddt = StringUtil.toDate(Utility.addDate(DateFormatUtils.format(createDt.getTime(),datePattern),0,0,term),datePattern);
//			System.out.println("오늘     ==== >" +todatedt);
//			System.out.println("입력일   ==== >" + createdt);
//			System.out.println("끝나는날 ==== >" + enddt);
			if((todatedt.before(enddt) || todatedt.equals(enddt)) && (todatedt.after(createdt) || todatedt.equals(createdt))){
				result = "<img src='/sfa/images/new.gif' align='absmiddle'>&nbsp;";
			}
		}

		// 수정일
		if(updateDt != null){
			updatedt = StringUtil.toDate(DateFormatUtils.format(updateDt.getTime(),datePattern),datePattern);
			enddt2 = StringUtil.toDate(Utility.addDate(DateFormatUtils.format(updateDt.getTime(),datePattern),0,0,term),datePattern);
			if((todatedt.before(enddt2) || todatedt.equals(enddt2)) && (todatedt.after(updatedt) || todatedt.equals(updatedt))){
				result = "<img src='/sfa/images/new.gif' align='absmiddle'>&nbsp;";
			}
		}

//		if(updateDt != null){
//			if(status2 == true ){
//				result = "<img src='/sfa/images/new.gif'>&nbsp;";
//			}
//		}else{
//			if(status == true ){
//				result = "<img src='/sfa/images/new.gif'>&nbsp;";
//			}
//		}
		return result;
	}

	/**
	 * 오늘 날짜를 입력받은 패턴으로 리턴한다.
	 * @param createDt
	 * @param pattern
	 * @return
	 */
	public static String getDate(Calendar createDt, String pattern){
		String result = "";
		if (createDt != null) {
			result = DateFormatUtils.format(createDt.getTime(),pattern);
		}
		return result;
	}

	/**
	 * Date타입을 String로 바꾼다
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		return DateFormatUtils.format(date,"yyyy-MM-dd");
	}

	/**
	 *
	 * @param cal
	 * @return
	 */
	public static String calendarToString(Calendar cal) throws ParseException{
		return dateToString(cal.getTime());
	}

	public static Calendar stringToCalendar(String dateString) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(Utility.stringToDate(dateString));
		return cal;
	}

	/**
	 * 해당 년도 해당 월의 주의 기간을 리턴. (1주는 월요일~일요일)
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Vector<Calendar[]> getWeeks(int year, int month){
		Vector<Calendar[]> v = new Vector<Calendar[]>();

		Calendar firstDay = new GregorianCalendar(year, month-1, 1);
		Calendar lastDay = new GregorianCalendar(year, month-1, firstDay.getActualMaximum(Calendar.DAY_OF_MONTH));

		Calendar tmpCal = (Calendar)firstDay.clone();
		int last = firstDay.getActualMaximum(Calendar.DAY_OF_MONTH);
		Calendar[] cals = new Calendar[2];
		for(int i=1; i<=last; i++){
			if(cals[0]!=null && cals[1]!=null){
				v.add(cals);
				cals = new Calendar[2];
			}
			if(i==1){
				cals[0] = firstDay;
			}else if(i==last){
				cals[1] = lastDay;
				v.add(cals);
			}

			if(tmpCal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
				cals[0] = (Calendar)tmpCal.clone();
			}else if(tmpCal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
				cals[1] = (Calendar)tmpCal.clone();
			}

			tmpCal.add(Calendar.DATE,1);
		}

		return v;
	}
}
