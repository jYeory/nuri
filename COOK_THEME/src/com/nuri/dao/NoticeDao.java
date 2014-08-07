package com.nuri.dao;

import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.Notice;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   NoticeDao.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :	 각 메서드는 NoticeSQL.xml id와 매핑된다.
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2013. 7. 24.      	jYeory<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author jYeory
 *
 */
public interface NoticeDao{
	Notice getNotice(String noticeSeq);
	int addNotice(Notice notice); 
	int updateNotice(Notice notice);
	int deleteNotice(String noticeSeq);
	List<Notice> noticeList(Parameters<String, ?> params);
	List<Notice> noticeList(Parameters<String, ?> params, int pg, int ps);
}
