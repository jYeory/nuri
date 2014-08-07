package com.nuri.service;

import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.MasterCode;

public interface MasterCodeService {
	MasterCode getMasterCode(String id);
	
	int addMasterCode(Parameters<String, String> params);
	int addMasterCode(MasterCode code);
	int updateMasterCode(MasterCode code);
	int disabled(String id);
	int disabled(Parameters<String, String> params);
	
	List<?> listMasterCodes(Parameters<String, String> params);
	List<?> listMasterCodes(Parameters<String, String> params, int pg, int ps);
}
