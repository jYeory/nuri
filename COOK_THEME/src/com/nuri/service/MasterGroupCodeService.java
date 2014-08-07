package com.nuri.service;

import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.MasterGroupCode;

public interface MasterGroupCodeService {
	MasterGroupCode getMasterGroupCode(String id);
	
	int addGroupCode(Parameters<String, String> params);
	int addGroupCode(MasterGroupCode groupCode);
	int updateGroupCode(MasterGroupCode groupCode);
	int disabled(String id);
	
	List<?> listMasterGroupCodes(Parameters<String, String> params);
	List<?> listMasterGroupCodes(Parameters<String, String> params, int pg, int ps);
	
	List<?> listMasterGroupCodeForTree(Parameters<String, String> params, int pg, int ps);
}
