package com.nuri.dao;

import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.Attachment;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   
 *            화  일  명 :   AttachmentDao.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :	 각 메서드는 SQL.Attachment.xml id와 매핑된다.
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2014. 7. 14.      	jYeory<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author jYeory
 *
 */
public interface AttachmentDao{
	Attachment getAttachment(Parameters<String, String> params);
	int addAttachment(Attachment attach); 
	int deleteAttachment(String attachSeq);
	List<Attachment> attachList(Parameters<String, ?> params);
	List<Attachment> attachList(Parameters<String, ?> params, int pg, int ps);
}
