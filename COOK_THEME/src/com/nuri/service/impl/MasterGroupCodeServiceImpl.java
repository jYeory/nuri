package com.nuri.service.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nuri.common.service.AbstractGenericManager;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.dao.MasterGroupCodeDao;
import com.nuri.domain.MasterGroupCode;
import com.nuri.domain.User;
import com.nuri.service.MasterGroupCodeService;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   MasterGroupCodeServiceImpl.java
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
@RemoteProxy(name="MasterGroupCodeDwr")
@Service("masterGroupCodeService")
public class MasterGroupCodeServiceImpl extends AbstractGenericManager implements MasterGroupCodeService{

	@Autowired
	private MasterGroupCodeDao grpDao;
	
	@Override
	public MasterGroupCode getMasterGroupCode(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addGroupCode(Parameters<String, String> params) {
		return 0;
	}

	@Override
	@Transactional
	public int addGroupCode(MasterGroupCode groupCode) {
		return grpDao.addGroupCode(groupCode);
	}

	@Override
	@Transactional
	public int updateGroupCode(MasterGroupCode groupCode) {
		return grpDao.updateGroupCode(groupCode);
	}

	@Override
	@Transactional
	public int disabled(String id) {
		System.out.println(id);
		return grpDao.disabled(id);
	}

	@Override
	public List<?> listMasterGroupCodes(Parameters<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> listMasterGroupCodes(Parameters<String, String> params, int pg, int ps) {
		return (PagedList<User>) list("com.nuri.dao.MasterGroupCodeDao", "listMasterGroupCode", params, pg, ps);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> listMasterGroupCodeForTree(Parameters<String, String> params, int pg, int ps) {
		return (PagedList<User>) list("com.nuri.dao.MasterGroupCodeDao", "listMasterGroupCodeForTree", params, pg, ps);
	}

}
