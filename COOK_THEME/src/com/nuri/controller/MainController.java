package com.nuri.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nuri.common.controller.BaseController;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.User;
import com.nuri.service.MenuService;

@Controller
public class MainController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/gaon/main.do")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		
		Parameters<String, Object> params = new Parameters<String, Object>();
		
		SecurityContext sc = SecurityContextHolder.getContext();
		User user = null;
		String[] roleSeqs = null;
		if(sc.getAuthentication().getPrincipal() instanceof User){
			user = (User) sc.getAuthentication().getPrincipal();
			roleSeqs = new String[user.getRoles().size()];
			for(int i=0; i<user.getRoles().size(); i++){
				roleSeqs[i] = user.getRoles().get(i).getRoleSeq();
			}
			params.put("roleSeqs", roleSeqs);
		}else{
			// 게스트 권한
			roleSeqs = new String[]{"role00000010"};
			params.put("roleSeqs", roleSeqs);
		}
		mv.addObject("menus",  menuService.getRootMenus(params));
		
		mv.setViewName("/main");
		return mv;
	}
	
	@RequestMapping("/gaon/dashboard.do")
	public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/test");
		return mv;
	}
	
	@RequestMapping("/blank.do")
	public ModelAndView blank(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/blank");
		return mv;
	}
}
