package com.nuri.dao;

import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.MasterCode;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   MasterCodeDao.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2013. 8. 8.      	jYeory<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author jYeory
 *
 */
public interface MasterCodeDao{
	
	MasterCode getMasterCode(String id);
	
//	int addMasterCode(Parameters<String, String> params);
	int addMasterCode(MasterCode groupCode);
	int updateMasterCode(MasterCode groupCode);
	int disabled(String id);
	int disabled(Parameters<String, String> params);
	
	List<?> listMasterCodes(Parameters<String, String> params);
	List<?> listMasterCodes(Parameters<String, String> params, int pg, int ps);
}
