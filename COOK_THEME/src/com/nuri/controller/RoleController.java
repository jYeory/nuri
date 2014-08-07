package com.nuri.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuri.common.controller.BaseController;
import com.nuri.common.utils.JSONUtil;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.Role;
import com.nuri.service.RoleService;
import com.nuri.service.UserService;

@Controller
public class RoleController extends BaseController {

	@Autowired private RoleService roleService;
	
	@Autowired private UserService userService;
	
	@RequestMapping("/admin/role/roleMngt.do")
	public String noticePage(HttpServletRequest request, HttpServletResponse response){
		 return "admin/role/roleMngt";
	}
	
	@RequestMapping("/json/sampleData.do")
	public void sendJsonData(HttpServletRequest req, HttpServletResponse rep){
		String key = req.getParameter("key");
		try {
			rep.setCharacterEncoding("UTF-8");
			rep.getWriter().write(JSONUtil.toJSON(roleService.getRole(key), null));
			
		} catch (JSONException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/admin/role/roleList.do")
	public void noticeList(HttpServletRequest request, HttpServletResponse response){
		
		Parameters<String, String> params = new Parameters<String, String>(request);
		PrintWriter writer = null;
		try {
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			
			List<Role> list = (List<Role>) roleService.getRoles(params, 0, 0);
			writer.write(JSONUtil.toJSON(list, null));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
		
	}
}
