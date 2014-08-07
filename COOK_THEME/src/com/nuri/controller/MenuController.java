package com.nuri.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuri.common.controller.BaseController;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.Menu;
import com.nuri.domain.User;
import com.nuri.service.MenuService;

@Controller
public class MenuController extends BaseController{

	@Autowired private MenuService menuService;
	
	@RequestMapping("/admin/menu/menuMngt.do")
	public String noticePage(HttpServletRequest request, HttpServletResponse response){
		 return "admin/menu/menuMngt";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/menu/menuList.do")
	public void menuList(HttpServletRequest req, HttpServletResponse rep){
		Parameters<String, String> params = new Parameters<String, String>(req);
		String menuId = params.get("id");
		params.addValue("prtMenuId", menuId);
		
		try {
			List<Menu> menus = (List<Menu>) menuService.listMenus(params);
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> tempMap = new HashMap<String, Object>();
			
			for(Menu menu : menus){
				tempMap = new HashMap<String, Object>();
				
				tempMap.put("seq", menu.getMenuSeq());
				tempMap.put("id", menu.getMenuId());
				tempMap.put("parent", menu.getPrtMenuId());
				tempMap.put("text", menu.getMenuName());
				list.add(tempMap);
			}
			
			rep.setContentType("json");
			rep.setCharacterEncoding("UTF-8");
			rep.getWriter().write(JSON.encode(list));
			
		} catch (JSONException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/menu/getRootMenus.do")
	public void getRootMenus(HttpServletRequest req, HttpServletResponse rep){
		Parameters<String, Object> params = new Parameters<String, Object>(req);
		params.addValue("prtMenuId", params.get("id"));
		ArrayList<HashMap<String, Object>> rootMenus = (ArrayList<HashMap<String, Object>>) menuService.getRootMenus(params);
		
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		BigDecimal menuCnt = null;
		try {
			for(HashMap<String, Object> menu : rootMenus){
				tempMap = new HashMap<String, Object>();
				
				tempMap.put("seq", menu.get("menu_seq"));
				tempMap.put("id", menu.get("menu_id"));
				tempMap.put("parent", menu.get("prt_menu_id")); // menu.get("prt_menu_id")
				tempMap.put("text", menu.get("menu_name"));
				menuCnt = (BigDecimal) menu.get("menu_cnt");
				if(menuCnt.intValue() > 0){
					tempMap.put("children", true);
				}
				tempMap.put("type", (menuCnt.intValue() > 0)?"root":"child");
				
				list.add(tempMap);
			}
			
			rep.setContentType("json");
			rep.setCharacterEncoding("UTF-8");
			rep.getWriter().write(JSON.encode(list));
			
		} catch (JSONException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/admin/menu/updateMenu.do")
	public void updateMenu(HttpServletRequest req, HttpServletResponse rep, @ModelAttribute Menu menu){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		menu.setUpdateBy(user.getUserSeq());
//		menuService.		
	}
	
}
