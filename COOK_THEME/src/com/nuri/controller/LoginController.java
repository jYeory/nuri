package com.nuri.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nuri.common.controller.BaseController;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.Role;
import com.nuri.domain.User;
import com.nuri.service.RoleService;
import com.nuri.service.UserService;

@Controller
public class LoginController extends BaseController {

	@Autowired private RoleService roleService;
	
	@Autowired private UserService userService;
	
	@RequestMapping("/login/loginPage.do")
	public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login/login");
		return mv;
	}
	
	@RequestMapping("/home/login.do")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.getSession().setAttribute("User", user);
		mv.addObject("User", user);
		mv.setViewName("redirect:/gaon/main.do");
		
		return mv;
	}
	
	@RequestMapping("/home/logout.do")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/gaon/main.do");
		
		SecurityContextHolder.getContext().setAuthentication(null);
		request.getSession().setAttribute("User", null);
		
		return mv;
	}
}
