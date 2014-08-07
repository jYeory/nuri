package com.nuri.dao;

import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.MasterGroupCode;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   MasterGroupCodeDao.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2013. 8. 6.      	jYeory<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author jYeory
 *
 */
public interface MasterGroupCodeDao{
	
	MasterGroupCode getMasterGroupCode(String id);
	
//	int addGroupCode(Parameters<String, String> params);
	int addGroupCode(MasterGroupCode groupCode);
	int updateGroupCode(MasterGroupCode groupCode);
	int disabled(String id);
	
	List<?> listMasterGroupCodes(Parameters<String, String> params);
	List<?> listMasterGroupCodes(Parameters<String, String> params, int pg, int ps);
}
