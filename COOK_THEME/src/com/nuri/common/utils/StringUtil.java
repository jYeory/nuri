package com.nuri.common.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * =============================================================================
 *            프로젝트명 :   np_VerseOfDay
 *            화  일  명 :   StringUtil.java
 *            기      능 :   문자열 처리를 위한 Utility Method 들
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
public class StringUtil {

	// Static format objects
	private static SimpleDateFormat dateFormat = new SimpleDateFormat();
	private static DecimalFormat numberFormat  = new DecimalFormat();

	/**
	 * 문자열의 null 여부를 check하여 null일 경우 ""를 리턴한다.
	 * null이 아닐 경우에는 문자열의 trim()을 호출한 후 리턴한다.
	 */
	public static String nullToBlank(Object comment) {
		if (comment == null) return "";
		return String.valueOf(comment).trim();
	}

	/**
	 * 문자열의 null 여부를 check하여 null일 경우 ""를 리턴한다.
	 * null이 아닐 경우에는 문자열의 trim()을 호출한 후 리턴한다.
	 */
	public static String nullToBlank(Object comment, String def) {
		if (comment == null || String.valueOf(comment).trim().equals("")) return def;
		return String.valueOf(comment).trim();
	}
	
	/**
	 * Collection, Map, Object[] 등 과 같은 객체들의 값을 
	 * 펼쳐서 String으로 반환한다.
	 * 
	 * @param obj
	 * @param delim
	 * @return
	 */
	public static String expandArray(Object obj, String delim) {
		StringBuffer buff = new StringBuffer();
		if(obj instanceof Map<?,?>) {
			obj = ((Map<?,?>)obj).values(); 
		}
		if(obj instanceof Collection<?>) {
			Iterator<?> iter = ((Collection<?>) obj).iterator();
			while(iter.hasNext()) {
				if(buff.length()>0) buff.append(delim);
				buff.append( String.valueOf(iter.next()) );
			}

		} else if(obj instanceof Object[]) {
			Object[] arr = (Object[]) obj;
			for(int i=0; i<arr.length; i++) {
				if(buff.length()>0) buff.append(delim);
				buff.append( String.valueOf(arr[i]) );				
			}
		}
		return buff.toString();
	}

	/**
	 * String[] 에 특정 값이 존재하는지 check
	 */
	public static boolean contains(String[] array, String value) {
		if (array == null || value == null) return false;
		boolean containFlag = false;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && value.trim().equals(array[i].trim())) {
				containFlag = true;
			}
		}
		return containFlag;
	}

	/**
	 * 부호비트에 값이 있는 경우는 2바이트 문자로 간주하여 처리한다.
	 * 
	 * @param in_Str 잘려져야 할 스트링
	 * @param in_CutPos 잘려져야 할 인덱스.
	 * @return 잘려진 스트링.
	 */
	public String cutByteLength(String in_Str, int in_CutPos) {
		byte[] byteStr = in_Str.getBytes();
	
		// 부호비트의 값이 0 이 아닌 경우는 2바이트 문자로 간주.
		if ((byteStr[in_CutPos] & 0x80) == 0) {
			return new String(byteStr, 0, in_CutPos);

		} else {
			return new String(byteStr, 0, in_CutPos + 1);
		}
	}

	/**
	 * 문자열의 개행문자를 <BR>로 변환한다. 만약 문자열 안에 <HTML>이라는 문자가
	 * 존재한다면 변환하지 않는다.
	 * 또한 공백문자 ' '를 &nbsp;로 변환한다.('<','>' 문자 안에 없을 경우)
	 */
	public static String convertBr(String comment) {
		String temp = comment;
		int pos;
		
		pos = 0;
		while((pos = temp.indexOf("\n")) != -1) {

	  String left = temp.substring(0, pos);
		   String right = temp.substring(pos + 1, temp.length());
		   temp = left + "<br>" + right;

		}

		pos = 0;
		while((pos = temp.indexOf("  ", pos)) != -1) {
		   String left = temp.substring(0, pos);
		   String right = temp.substring(pos + 2, temp.length());
		   temp = left + "&nbsp;&nbsp;" + right;
		   pos += 4;
		}
		return temp;

	}

	/**
	 * 특정문자열 사이를 삭제한다.
	 * example) deleteInString("<font color='red'>TEST123</font>", "<", ">");  -->  result는 TEST123
	 * @param s 원본 문자열
	 * @param from 삭제할 문자열의 시작 패턴
	 * @param to 삭제할 문자열의 끝 패턴
	 */
	public static String deleteInString(String s, String from, String to) {

		boolean inToken = false;
		StringBuffer returnBuf = new StringBuffer();
		int lastFromPosition = 0;
		
		int s_len    = s.length();
		int from_len = from.length();
		int to_len   = to.length();

		for (int i = 0; i < s_len; i++) {
			if (inToken) {
				if ((s_len >= i + to_len) && s.substring(i, i + to_len).equalsIgnoreCase(to)) {
					inToken = false;
					i += (to_len - 1);
				}
			} else {
				if ((s_len >= i + from_len) && s.substring(i, i + from_len).equalsIgnoreCase(from)) {
					lastFromPosition = i;
					i += (from_len - 1);
					inToken = true;
				} else {
					returnBuf.append(s.charAt(i));
				}
			}
		}

		if (inToken) {
			returnBuf.append(s.substring(lastFromPosition));
		}

		return returnBuf.toString();
	}
	
	/**
	 * Escape Character 를 변환 한다.
	 * 
	 * @param comment
	 * @return
	 */
	public static String escape(Object comment) {
		if (comment == null) return "";
		String result = String.valueOf(comment).trim();
		result = replaceInString(result, "\'", "＇");
		result = replaceInString(result, "\"", "“");
		result = replaceInString(result, "\n", "\\n");
		result = result.replace('\r', ' ');
		return result;
	}

	/**
	 * 문자열을 특정 delimiter를 이용하여 분리한다.
	 *
	 * @param delim delimiter
	 * @param buf 분리할 문자열
	 * @return 분리된 문자열의 String[]
	 */
	public static String[] explode(String delim, String buf) {
		StringTokenizer token = new StringTokenizer(buf, delim);
		ArrayList<String> arrList = new ArrayList<String>();
		for (; token.hasMoreTokens();) {
			arrList.add(token.nextToken());
		}
		String retval[] = new String[arrList.size()];
		for (int i = 0; i < retval.length; i++) {
			retval[i] = (String) arrList.get(i);
		}
		return retval;
	}

	/**
	 * 문자열을 특정 delimiter를 이용하여 분리한다.
	 *
	 * @param delim delimiter
	 * @param buf 분리할 문자열
	 * @return 분리된 문자열의 ArrayList
	 */
	public static ArrayList<String> explodeToArrayList(String delim, String buf) {
		StringTokenizer token = new StringTokenizer(buf, delim);
		ArrayList<String> arrList = new ArrayList<String>();
		while (token.hasMoreTokens()) {
			arrList.add(token.nextToken());
		}
		return arrList;
	}


	/**
	 * 입력된 값을 지정된 글자수만큼 0을 채워서 반환
	 * 
	 * @param data 
	 * @param size
	 * @return
	 */
	public static String fillZeroString(String data, int size) {
		if(data==null) data = "";
		StringBuffer buff = new StringBuffer();
		String str = data.trim();
		for(int i=0; i<size-str.length(); i++) {
			buff.append('0');
		}
		buff.append(str);
		return buff.toString();
	}

	/**
	 * 일정길이까지 문자를 자른다
	 * 
	 * @param data
	 * @param len
	 * @return
	 */
	public static String limitLength(String data, int len) {
		return limitLength(data, len, "...");
	}
	
	/**
	 * 시작인덱스와 끝 인덱스 만큼 자른다.
	 * 
	 * @param data
	 * @param startIdx
 	 * @param endIdx
	 * @return
	 */
	public static String cutString(String data, int startIdx, int endIdx) {
		String result = "";
		if(data != null){
			result = data.substring(startIdx,endIdx);
		}
		return result; 
	}

	/**
	 * 일정 길이 까지 문자를 자른다.
	 * 
	 * @param data
	 * @param len
	 * @param suffix
	 * @return
	 */
	public static String limitLength(String data, int len, String suffix) {
		if (data == null) return "";
		if (data.length() >= len) {
			data = data.substring(0, len - 3);
			data += suffix;
		}
		return data;
	}

	/**
	 * 특정 문자열을 원하는 pattern으로 바꾸어준다.
	 * @param str 원본 문자열
	 * @param oldStr 대체될 문자열 패턴
	 * @param newStr 대체할 문자열 패턴
	 * @return 결과 문자열
	 */
	public static String replaceInString(String str, String oldStr, String newStr) {
		if (str == null || oldStr == null || newStr == null) {
			return str;
		}

		int stdIdx = 0;
		int endIdx = str.indexOf(oldStr, stdIdx);
		if (endIdx < 0)
			return str; // 대체하고자 하는 문자열이 없을 경우
		StringBuffer strbuf = new StringBuffer();
		while (endIdx >= 0) {
			strbuf.append(str.substring(stdIdx, endIdx));
			strbuf.append(newStr);
			stdIdx = endIdx + oldStr.length();
			endIdx = str.indexOf(oldStr, stdIdx);
		}
		strbuf.append(str.substring(stdIdx));
		return strbuf.toString();
	}
	
	/**
	 * 특정 문자열 배열을 원하는 문자열 배열로 바꾸어준다.
	 * 
	 * @param strbuff 원문 StringBuffer
	 * @param oldStr  원본 문자열 배열들
	 * @param newStr  바꾸는 문자열 배열들
	 */
	public static void replaceInString(StringBuffer strbuff, String[] oldStr, String[] newStr) {
		if (strbuff == null || oldStr == null || newStr == null) {
			return;
		}
		for(int i=0; i<oldStr.length; i++) {
			int k = (newStr.length <= i) ? newStr.length-1 : i; 
			int sIdx = 0;
			int eIdx = strbuff.indexOf(oldStr[i], sIdx);
			while(eIdx>=0) {
				strbuff.replace(eIdx, eIdx+oldStr[i].length(), newStr[k]);
				sIdx = eIdx+oldStr[i].length();
				eIdx = strbuff.indexOf(oldStr[i], sIdx);
			}
		}
	}

	/**
	 * Kilo Byte형태로 보여준다. 사용된 pattern은 #,##0.## 임(소수점 1째자리까지 나타나고 3자리마다 ,를 붙인다.) 
	 */
	public static String showKb(Object obj) {
		if (obj == null)
			return "0";
		String str = String.valueOf(obj);
		if (str.equals("") || str.equals("0"))
			return "0";
		try {
			return toNumberString(String.valueOf(((Double.valueOf(str)).doubleValue()) / (1024)), "#,##0.##");
		} catch (NumberFormatException ex) {
			System.err.println("StrUtil.showKb(" + obj + ") " + ex.getMessage());
			return "0";
		}
	}

	/**
	 * Mega Byte형태로 보여준다. 사용된 pattern은 #,##0.## 임(소수점 2째자리까지 나타나고 3자리마다 ,를 붙인다.) 
	 */
	public static String showMb(Object obj) {
		if (obj == null)
			return "0";
		String str = String.valueOf(obj);
		if (str.equals("") || str.equals("0"))
			return "0";
		try {
			return toNumberString(String.valueOf(((Double.valueOf(str)).doubleValue()) / (1024 * 1024)), "#,##0.##");
		} catch (NumberFormatException ex) {
			System.err.println("StrUtil.showMb(" + obj + ") " + ex.getMessage());
			return "0";
		}
	}

	/**
	 * 숫자를 세자리마다 ,를 붙여서 보여준다.
	 */
	public static String showNum(Object numO) {
		if (numO == null)
			return "0";
		String num = String.valueOf(numO);
		if (num.trim().equals(""))
			return "0";
		return toNumberString(num, null);
	}

	/**
	 * Object를 int로 변환
	 * 
	 * @param data
	 * @return
	 */
	public static int toInt(Object data) {
		return toInt(data, 0);
	}

	/**
	 * Object를 int로 변환
	 * 
	 * @param data
	 * @param defaultValue
	 * @return
	 */
	public static int toInt(Object data, int defaultValue) {
		int ret = defaultValue;
		if(data==null) return defaultValue;
		String value = String.valueOf(data).trim();
		if(value.length()==0) return defaultValue;
		try {
			ret = Integer.parseInt(value);
		} catch(Exception e) {
			System.err.println("StrUtil.toInt("+data+", "+defaultValue+") " + e);
		}
		return ret;
	}
	
	/**
	 * Object를 long으로 변환
	 * 
	 * @param data
	 * @return
	 */
	public static long toLong(Object data) {
		return toLong(data, 0);
	}
	
	/**
	 * Object를 long으로 변환
	 * 
	 * @param data
	 * @param defaultValue
	 * @return
	 */
	public static long toLong(Object data, long defaultValue) {
		long ret = defaultValue;
		if(data==null) return defaultValue;
		String value = String.valueOf(data).trim();
		if(value.length()==0) return defaultValue;
		try {
			ret = Long.parseLong(value);
		} catch(Exception e) {
			System.err.println("StrUtil.toLong("+data+", "+defaultValue+") " + e);
		}
		return ret;
	}
	
	/**
	 * Object를 double으로 변환
	 * 
	 * @param data
	 * @return
	 */
	public static double toDouble(Object data) {
		return toDouble(data, 0);
	}
	
	/**
	 * Object를 double으로 변환
	 * 
	 * @param data
	 * @param defaultValue
	 * @return
	 */
	public static double toDouble(Object data, double defaultValue) {
	    double ret = defaultValue;
		if(data==null) return defaultValue;
		String value = String.valueOf(data).trim();
		if(value.length()==0) return defaultValue;
		try {
			ret = Double.parseDouble(value);
		} catch(Exception e) {
			System.err.println("StrUtil.toDouble("+data+", "+defaultValue+") " + e);
		}
		return ret;
	}	
	
	/**
	 * 일정 길이만큼 문자를 나눔, 어절 단위로 일정길이 안쪽에서 짜름
	 */
	public static String[] subStrByBlk(String str, int len) {
		Vector<StringBuffer> tmpStrVec = new Vector<StringBuffer>();
		String tmpStr = null;
		StringBuffer strbuff = new StringBuffer();
		int cntLength = 0;

		StringTokenizer strToken = new StringTokenizer(str, " ");
		while (strToken.hasMoreTokens()) {
			tmpStr = strToken.nextToken();
			cntLength += tmpStr.length() + 1;

			if (cntLength <= len) {
				strbuff.append(tmpStr + " ");
			} else {
				tmpStrVec.add(strbuff);
				strbuff = new StringBuffer();
				strbuff.append(tmpStr + " ");
				cntLength = tmpStr.length() + 1;
			}
		}
		//마지막 문자열을 삽입
		tmpStrVec.add(strbuff);

		String[] retStr = new String[tmpStrVec.size()];
		for (int i = 0; i < tmpStrVec.size(); i++) {
			retStr[i] = ((StringBuffer) tmpStrVec.get(i)).toString();
		}

		return retStr;
	}

	/**
	 * Converts a String to a Date, using the specified pattern.
	 * (see java.text.SimpleDateFormat for pattern description)
	 *
	 * @param dateString the String to convert
	 * @param dateFormatPattern the pattern
	 * @return the corresponding Date
	 * @exception ParseException, if the String doesn't match the pattern
	 */
	public static Date toDate(String dateString, String dateFormatPattern) throws ParseException {
		Date date = null;
		if (dateFormatPattern == null) {
			dateFormatPattern = "yyyy-MM-dd";
		}
		synchronized (dateFormat) {
			dateFormat.applyPattern(dateFormatPattern);
			dateFormat.setLenient(false);
			date = dateFormat.parse(dateString);
		}
		return date;
	}

	/**
	 * 문자열을 지정된 패턴으로 분석하여 Calendar로 반환.
	 * 
	 * @param dateString the String to convert
	 * @param dateFormatPattern the pattern
	 * @return the corresponding Calendar
	 * @exception ParseException, if the String doesn't match the pattern
	 */
	public static Calendar toCalendar(String dateString, String dateFormatPattern) throws ParseException
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime( StringUtil.toDate(dateString, dateFormatPattern) );
		return cal;
	}

	/**
	 * 일정한 패턴으로 날짜를 변환한다.
	 * example) toDateString("200205111230", "yyyy년 MM월 dd일 HH시 mm분");  
	 *           -->  result는 2002년 05월11일 12시 30분
	 * @param data 변환할 날짜 (반드시 공백없이 8~14자리 숫자로만 되어야한다.)
	 * @param format 변환할 날짜 패턴 형식
	 * @return 변환된 형식의 값
	 */
	public static String toDateString(String data, String format) {
		String returnVal = "";

		if (format == null)
			format = "yyyy-MM-dd HH:mm:ss";
		if (data == null || data.trim().length() < 8)
			return "";
		data = data.trim();

		try {
			Calendar cal = null;
			if (data.length() >= 14) {
				cal =
					new GregorianCalendar(
						Integer.parseInt(data.substring(0, 4)),
						Integer.parseInt(data.substring(4, 6)) - 1,
						Integer.parseInt(data.substring(6, 8)),
						Integer.parseInt(data.substring(8, 10)),
						Integer.parseInt(data.substring(10, 12)),
						Integer.parseInt(data.substring(12, 14)));

			} else if (data.length() >= 12) {
				cal =
					new GregorianCalendar(
						Integer.parseInt(data.substring(0, 4)),
						Integer.parseInt(data.substring(4, 6)) - 1,
						Integer.parseInt(data.substring(6, 8)),
						Integer.parseInt(data.substring(8, 10)),
						Integer.parseInt(data.substring(10, 12)));

			} else if (data.length() >= 10) {
				cal =
					new GregorianCalendar(
						Integer.parseInt(data.substring(0, 4)),
						Integer.parseInt(data.substring(4, 6)) - 1,
						Integer.parseInt(data.substring(6, 8)),
						Integer.parseInt(data.substring(8, 10)),
						0);

			} else {
				cal =
					new GregorianCalendar(
						Integer.parseInt(data.substring(0, 4)),
						Integer.parseInt(data.substring(4, 6)) - 1,
						Integer.parseInt(data.substring(6, 8)));
			}

			returnVal = DateFormatUtils.format(cal.getTime(),format);
		} catch (Exception e) {
			System.err.println("StrUtil.toDateString() " + e.getMessage());
		}

		return returnVal;
	}

	/**
	 * 문자열을 html 형식으로 변환한다.
	 * '  -->  &#39;
	 * "  -->  &#34;
	 * \n -->  <BR>\n
	 * \t -->  &nbsp;&nbsp;&nbsp;&nbsp;
	 * <  -->  &lt;
	 * >  -->  &gt;
	 * &  -->  &amp;
	 */
	public static String toHTMLString(String in) {
		StringBuffer out = new StringBuffer();
		for (int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\'') {
				out.append("&#39;");
			} else if (c == '\"') {
				out.append("&#34;");
			} else if (c == '\n') {
				out.append("<BR>\n");
			} else if (c == '\t') {
				out.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			} else if (c == '<') {
				out.append("&lt;");
			} else if (c == '>') {
				out.append("&gt;");
			} else if (c == '&') {
				out.append("&amp;");
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	/**
	 * Converts a String to a Number, using the specified pattern.
	 * (see java.text.NumberFormat for pattern description)
	 *
	 * @param numString the String to convert
	 * @param numFormatPattern the pattern
	 * @return the corresponding Number
	 * @exception ParseException, if the String doesn't match the pattern
	 */
	public static Number toNumber(String numString, String numFormatPattern) throws ParseException {
		Number number = null;
		if (numFormatPattern == null) {
			numFormatPattern = "######.##";
		}
		synchronized (numberFormat) {
			numberFormat.applyPattern(numFormatPattern);
			number = numberFormat.parse(numString);
		}
		return number;
	}

	/**
	 * 일정한 패턴으로 숫자를 변환 한다. 
	 * example) toNumberString("12345.6789", "#,##.##");  -->  result는 1,23,45.68
	 * @param data 변환할 수
	 * @param format 변환할 패턴 형식
	 * @return 변환된 형식의 값
	 */
	public static String toNumberString(String data, String format) {
		String returnVal = "";

		if (format == null)
			format = "#,###";
		if (data == null || data.trim().equals(""))
			return "";
		try {
			DecimalFormat df = new DecimalFormat(format);
			returnVal = df.format(Double.parseDouble(data));
		} catch (Exception e) {
			System.err.println("StrUtil.toNumberString() " + e.getMessage());
		}

		return returnVal;
	}
	/**
	 * content를 일정한 길이로...
	 * example) toNumberString("12345.6789", "#,##.##");  -->  result는 1,23,45.68
	 * @param data 변환할 수
	 * @param format 변환할 패턴 형식
	 * @return 변환된 형식의 값
	 */
	public static final String[] toStringArray(String str, int columnSize)
	{
		StringBuffer sb = new StringBuffer();
		ArrayList<String> al = new ArrayList<String>();
		String c = null;
		int size = 0;
		int len = 0;
		for(int i = 0; i < str.length(); i++)
		{
			c = String.valueOf(str.charAt(i));
			len = c.getBytes().length;
			if(len == 2)
				size += 2;
			else
				size++;
			if(size > columnSize)
			{
				al.add(sb.toString());
				sb = new StringBuffer();
				size = len;
			}
			sb.append(c);
		}

		if(sb.length() > 0)
			al.add(sb.toString());
		String re[] = new String[al.size()];
		for(int i = 0; i < re.length; i++)
			re[i] = al.get(i).toString();

		return re;
	}

	public static String round(String number, String decimalLength){
		double input = Double.parseDouble(number);
		double length = Double.parseDouble(decimalLength);
		double result = Math.round(input * Math.pow(10, length)) / Math.pow(10, length);
		if(length == 0)	{
			return String.valueOf(new Double(result).intValue());
		}else{
			return String.valueOf(result);
		}
	}

}