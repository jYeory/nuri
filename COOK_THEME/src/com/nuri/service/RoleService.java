package com.nuri.service;

import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.Role;

public interface RoleService {
	Role getRole(String id);
	List<?> userRoles(Parameters<String, String> params);
	List<?> userRoles(Parameters<String, String> params, int pg, int ps);
	
	List<?> getRoles(Parameters<String, String> params);
	List<?> getRoles(Parameters<String, String> params, int pg, int ps);
	
	int addRole(Role role);
}
