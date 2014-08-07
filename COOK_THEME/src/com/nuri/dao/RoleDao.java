package com.nuri.dao;

import java.util.HashMap;
import java.util.List;

import com.nuri.common.exception.DefaultException;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.Role;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   RoleDao.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :	 각 메서드는 RoleSQL.xml id와 매핑된다.
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
public interface RoleDao{
	Role getRole(String id);
	PagedList<Role> getRoles(HashMap<String, String> params);
	List<Role> userRoles(Parameters<String, ?> params);
	List<Role> userRoles(Parameters<String, ?> params, int pg, int ps);
	int addRole(Role role);
}
