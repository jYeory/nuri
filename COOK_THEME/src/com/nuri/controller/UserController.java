package com.nuri.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nuri.common.controller.BaseController;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.User;
import com.nuri.service.RoleService;
import com.nuri.service.UserService;
import com.nuri.service.test.UserServiceTest;

@Controller
public class UserController extends BaseController {

	@Autowired private RoleService roleService;
	
	@Autowired private UserService userService;
	
	// 유저 비밀번호 암호화-복호화 클래스
	@Autowired private StandardPasswordEncoder passwdEncoder;
	
	@RequestMapping("/user/isDuplication.do")
	public void isDuplication(HttpServletRequest request, HttpServletResponse response){
		Parameters<String, String> params = new Parameters<String, String>(request);
		User user = userService.getUser(params);
		
		PrintWriter writer = null;
		try {
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			writer.write( String.valueOf((user==null)?1:0) );
			writer.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	@RequestMapping("/user/register.do")
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response){
		Parameters<String, String> params = new Parameters<String, String>(request);
		
		ModelAndView mv = new ModelAndView();
		
		try{
			User user = (User) params.populate("", User.class);
			user.setPasswd( passwdEncoder.encode(user.getPasswd()) );
			userService.addUser(user);
			
			String[] roleSeqs = new String[]{"role90000000", "role10000000"};
			Parameters<String, String> p = new Parameters<String, String>();
			for(String roleSeq : roleSeqs){
				p.clear();
				p.addValue("userSeq", user.getUserSeq());
				p.addValue("roleSeq", roleSeq);
				userService.addUserRole(p);
			}
			mv.addObject("resultMessage", "회원가입을 완료하였습니다. 로그인페이지로 이동합니다.");
		}catch(Exception e){
			mv.addObject("resultMessage", "회원가입에 실패하였습니다. 관리자에게 문의해주세요..");
		}
		mv.addObject("nextLocation", "/login/login.jsp");
		mv.setViewName("/resultCallback");
		
		return mv;
	}
}
