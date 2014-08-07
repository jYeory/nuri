package com.nuri.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSONException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuri.common.utils.JSONUtil;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.MasterCode;
import com.nuri.domain.MasterGroupCode;
import com.nuri.domain.User;
import com.nuri.service.MasterCodeService;
import com.nuri.service.MasterGroupCodeService;

@Controller
public class MasterGroupCodeController {

	@Autowired private MasterGroupCodeService grpService;
	
	@Autowired private MasterCodeService codeService;
	
	@RequestMapping("/desktop/code/listGroupCode.do")
	public void sendJsonData(HttpServletRequest req, HttpServletResponse rep){
		Parameters<String, String> params = new Parameters<String, String>(req);
		int ps = params.getInt("limit", 50);
		try {
			rep.setCharacterEncoding("UTF-8");
			rep.getWriter().write(JSONUtil.toExtJSON((PagedList<?>) grpService.listMasterGroupCodes(params, 1, ps)));
			
		} catch (JSONException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/desktop/code/listGroupCodeForTree.do")
	public void listGroupCodeForTree(HttpServletRequest req, HttpServletResponse rep){
		Parameters<String, String> params = new Parameters<String, String>(req);
		System.out.println(params);
		int ps = params.getInt("limit", 50);
		String nodeValue = params.getString("node");
		try {
			String sort = params.getString("sort", "");
			if(sort.length() > 0){
				sort = sort.substring(1, sort.length()-1);
				HashMap<String,Object> map = new ObjectMapper().readValue(sort, HashMap.class);
				params.addValue("sort", (String)map.get("property"));
				params.addValue("dir", (String)map.get("direction"));
			}
			String jsonStr = null;
			if(nodeValue.equals("root") ){
				jsonStr = JSONUtil.toExtJSON((PagedList<?>) grpService.listMasterGroupCodeForTree(params, 1, ps));
			}
			// 오픈한 경우
			else{
				params.addValue("grpSeq", nodeValue);
				jsonStr = JSONUtil.toExtJSON((PagedList<?>) codeService.listMasterCodes(params, 1, ps));
			}
			System.out.println(jsonStr);

			rep.setCharacterEncoding("UTF-8");
			rep.getWriter().write(jsonStr);
			
		} catch (JSONException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/desktop/code/saveCode.do")
	public void saveGroupCode(HttpServletRequest req, HttpServletResponse rep){
		Parameters<String, String> params = new Parameters<String, String>(req);
		System.out.println("before > \n"+params);
		
		boolean isGroupCode = Boolean.parseBoolean(params.getString("isGroupCode"));
		String prefix = isGroupCode?"grp":"code";
		
		params.addValue(prefix+"NameKor", params.getString("name_kor"));
		params.remove("name_kor");
		params.addValue(prefix+"NameEng", params.getString("name_eng"));
		params.remove("name_eng");
		params.addValue(prefix+"Description", params.getString("description"));
		params.remove("description");
		
		if(!params.getString("parentId", "root").equals("root")){
			params.addValue("grpSeq", params.getString("parentId"));
		}
		
		String operation = params.getString("operation", "");
		if(operation.equals("update")){
			params.addValue(prefix+"Seq", params.getString("p_key"));
		}
		
		System.out.println("after > \n"+params);
		MasterGroupCode groupCode = null;
		MasterCode code = null;
		if(isGroupCode){
			groupCode = (MasterGroupCode) params.populate("", MasterGroupCode.class);
		}else{
			code = (MasterCode) params.populate("", MasterCode.class);
		}
		
		User loginUser = (User) SecurityContextHolder.getContext().getAuthentication();
		
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if(operation.equals("add")){
				if(isGroupCode){
					groupCode.setCreateBy(loginUser.getUserSeq());
					grpService.addGroupCode(groupCode); 
				}
				else {
					code.setCreateBy(loginUser.getUserSeq());
					codeService.addMasterCode(code); 
				}
			}
			else if(operation.equals("update")){
				// seq가 있으면 update
				if(isGroupCode){
					groupCode.setUpdateBy(loginUser.getUserSeq());
					grpService.updateGroupCode(groupCode); 
				}
				else {
					code.setUpdateBy(loginUser.getUserSeq());
					codeService.updateMasterCode(code); 
				}
			}
			jsonMap.put("result", "success");
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("result", "error");
			jsonMap.put("cause", e.getCause().toString());
			
		} finally{
			
			rep.setCharacterEncoding("UTF-8");
			try {
				rep.getWriter().write(JSONUtil.toJSON(jsonMap, null));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/desktop/code/removeCode.do")
	public void removeCode(HttpServletRequest req, HttpServletResponse rep){
		Parameters<String, String> params = new Parameters<String, String>(req);
		int result = 0;
		
		try {
			if(params.getString("codeSeq", "").length() > 0){
				result = codeService.disabled(params);
			}
			else if(params.getString("grpSeq", "").length() > 0){
				codeService.disabled(params);
				result = grpService.disabled(params.getString("grpSeq"));
			}
			HashMap<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("result", result);
			rep.setCharacterEncoding("UTF-8");
			rep.getWriter().write(JSONUtil.toJSON(jsonMap, null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
