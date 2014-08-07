package com.nuri.dao;

import java.util.HashMap;
import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.Menu;
import com.nuri.domain.MenuRole;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   MenuDao.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :	 각 메서드는 MenuSQL.xml id와 매핑된다.
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
public interface MenuDao{
	Menu getMenu(Parameters<String, ?> params);
	String getMenuId(String menuSeq);
	
	HashMap<String, Object> getMenuInfo(Parameters<String, ?> params);
	List<Menu> listMenu(Parameters<String, ?> params);
	List<Menu> listMenu(Parameters<String, ?> params, int pg, int ps);
	
	List<?> getRootMenus(Parameters<String, ?> params);
	int updateMenu(Menu menu);
	int addMenu(Menu menu);
	int deleteMenu(String menuId);
	
	int addMenuRole(MenuRole menuRole);
	int deleteMenuRole(String menuId);
}
