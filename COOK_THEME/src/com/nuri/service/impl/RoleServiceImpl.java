package com.nuri.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nuri.common.service.AbstractGenericManager;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.dao.RoleDao;
import com.nuri.domain.Role;
import com.nuri.service.RoleService;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   RoleServiceImpl.java
 *            기      능 :   RoleService 구현체
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
@RemoteProxy(name="RoleDwr")
@Service("roleService")
public class RoleServiceImpl extends AbstractGenericManager implements RoleService{
	
	Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private RoleDao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public PagedList<?> userRoles(Parameters<String, String> params, int pg, int ps) {
		return (PagedList<Role>) list("com.nuri.dao.RoleDao", "listUser", params, pg, ps);
	}

	@Override
	public List<?> userRoles(Parameters<String, String> params) {
		return dao.userRoles(params);
	}

	@RemoteMethod
	@Override
	public Role getRole(String id) {
		return dao.getRole(id);
	}

	@RemoteMethod
	@Override
	public PagedList<Role> getRoles(Parameters<String, String> params){
		return dao.getRoles(params);
	}
	
	@Override
	public PagedList<Role> getRoles(Parameters<String, String> params, int pg, int ps){
		return dao.getRoles(params);
	}

	@RemoteMethod
	@Transactional
	@Override
	public int addRole(Role role) {
		return dao.addRole(role);
	}
}
