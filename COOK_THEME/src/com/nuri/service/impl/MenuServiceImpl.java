package com.nuri.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nuri.common.service.AbstractGenericManager;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.dao.MenuDao;
import com.nuri.domain.Menu;
import com.nuri.domain.MenuRole;
import com.nuri.domain.User;
import com.nuri.service.MenuService;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   MenuServiceImpl.java
 *            기      능 :   MenuService 구현체
 *            인      수 :   
 *            특이  사항 :
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
@RemoteProxy(name="MenuDwr")
@Service("menuService")
public class MenuServiceImpl extends AbstractGenericManager implements MenuService{
	
	Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private MenuDao dao;
	
	@SuppressWarnings("unchecked")
	@RemoteMethod
	@Override
	public PagedList<?> listMenus(Parameters<String, String> params, int pg, int ps) {
		return (PagedList<Menu>) list("com.nuri.dao.MenuDao", "listMenu", params, pg, ps);
	}

	@RemoteMethod
	@Override
	public List<?> listMenus(Parameters<String, String> params) {
		return dao.listMenu(params);
	}
	
	@RemoteMethod
	@Override
	public List<?> getRootMenus(Parameters<String, Object> params) {
		return dao.getRootMenus(params);
	}

	@Override
	@RemoteMethod
	public Menu getMenu(Parameters<String, String> params) {
		return dao.getMenu(params);
	}

	@Override
	@RemoteMethod
	public HashMap<String, Object> getMenuInfo(Parameters<String, String> params) {
		return dao.getMenuInfo(params);
	}
	

	@Override
	@RemoteMethod
	@Transactional
	public int updateMenu(Menu menu) {
		menu.setUpdateBy( ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserSeq() );
		dao.deleteMenuRole(menu.getMenuId());
		
		String roles = menu.getRoles();
		String[] roleSeqs = roles.split(",");
		MenuRole mr = null;
		for(String roleSeq : roleSeqs){
			mr = new MenuRole();
			mr.setMenuId(menu.getMenuId());
			mr.setRoleSeq(roleSeq);
			mr.setCreateBy(menu.getUpdateBy());
			
			dao.addMenuRole(mr);
		}
		
		return dao.updateMenu(menu);
	}

	@Override
	@RemoteMethod
	@Transactional
	public int addMenu(Menu menu) {
		int result = 0;
		try{
			menu.setCreateBy( ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserSeq() );
			dao.addMenu(menu);
			
			menu.setMenuId( dao.getMenuId(menu.getMenuSeq()) );
			
			String roles = menu.getRoles();
			String[] roleSeqs = roles.split(",");
			MenuRole mr = null;
			for(String roleSeq : roleSeqs){
				mr = new MenuRole();
				mr.setMenuId(menu.getMenuId());
				mr.setRoleSeq(roleSeq);
				mr.setCreateBy(menu.getUpdateBy());
				
				dao.addMenuRole(mr);
			}
			result = 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		
		return result;
	}
	
	@Override
	@RemoteMethod
	@Transactional
	public int deleteMenu(String menuId) {
		dao.deleteMenuRole(menuId);
		return dao.deleteMenu(menuId);
	}
}
