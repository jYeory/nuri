package com.nuri.service.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nuri.common.service.AbstractGenericManager;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.dao.MasterCodeDao;
import com.nuri.domain.MasterCode;
import com.nuri.domain.User;
import com.nuri.service.MasterCodeService;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   MasterCodeServiceImpl.java
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
@RemoteProxy(name="MasterCodeDwr")
@Service("masterCodeService")
public class MasterCodeServiceImpl extends AbstractGenericManager implements MasterCodeService{

	@Autowired
	private MasterCodeDao codeDao;
	
	@Override
	public MasterCode getMasterCode(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addMasterCode(Parameters<String, String> params) {
		return 0;
	}

	@Override
	@Transactional
	public int addMasterCode(MasterCode masterCode) {
		return codeDao.addMasterCode(masterCode);
	}

	@Override
	@Transactional
	public int updateMasterCode(MasterCode masterCode) {
		return codeDao.updateMasterCode(masterCode);
	}

	@Override
	@Transactional
	public int disabled(String id) {
		return codeDao.disabled(id);
	}
	
	@Override
	@Transactional
	public int disabled(Parameters<String, String> params) {
		return codeDao.disabled(params);
	}

	@Override
	public List<?> listMasterCodes(Parameters<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> listMasterCodes(Parameters<String, String> params, int pg, int ps) {
		return (PagedList<User>) list("com.nuri.dao.MasterCodeDao", "listMasterCode", params, pg, ps);
	}

}
