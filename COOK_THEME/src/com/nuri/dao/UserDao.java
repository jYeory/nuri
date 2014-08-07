package com.nuri.dao;

import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.User;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   UserDao.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :	 각 메서드는 UserSQL.xml 각 id와 매핑된다.
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
public interface UserDao {
	User getUser(Parameters<String, String> params);
	int addUser(User user);
	int addUserRole(Parameters<String, String> params);
	int updateUser(User user);
	int disableUser(int id);
	int isExits(Parameters<String, String> params);
	
	List<User> listUser(Parameters<String, ?> params);
	List<User> listUser(Parameters<String, ?> params, int pg, int ps);
}
