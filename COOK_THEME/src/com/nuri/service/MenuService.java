package com.nuri.service;

import java.util.HashMap;
import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.Menu;

public interface MenuService {
	Menu getMenu(Parameters<String, String> params);
	HashMap<String, Object> getMenuInfo(Parameters<String, String> params);
	List<?> listMenus(Parameters<String, String> params);
	List<?> listMenus(Parameters<String, String> params, int pg, int ps);
	
	List<?> getRootMenus(Parameters<String, Object> params);
	int updateMenu(Menu menu);
	int addMenu(Menu menu);
	int deleteMenu(String menuId);
}
