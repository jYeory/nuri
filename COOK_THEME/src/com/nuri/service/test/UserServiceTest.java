package com.nuri.service.test;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.nuri.common.utils.JSONUtil;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.Role;
import com.nuri.domain.User;
import com.nuri.service.RoleService;
import com.nuri.service.UserService;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   UserServiceTest.java
 *            기      능 :   UserService Test case 파일
 *            인      수 :   
 *            특이  사항 :
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2013. 7. 24.      	jYeory<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author jYeory
 *
 */
public class UserServiceTest extends AbstractApplicationContextTest{
	private static Log log = LogFactory.getLog(UserServiceTest.class);
	
	@Resource(name="userService")
    private UserService userService;
	
	@Resource(name="roleService")
    private RoleService roleService;
	
	// 유저 비밀번호 암호화-복호화 클래스
	@Resource(name="passwordEncoder")
	private StandardPasswordEncoder encoder;
	
    @Test
    public void testInsertUser(){
    	// Create an encoder with strength 16
    	
//    	assertTrue(encoder.matches("myPassword", result));
    	
    	Parameters<String, Object> params = new Parameters<String, Object>();
    	params.addValue("p_userType", "0");
    	params.addValue("p_userId", "test01@test.com");
    	params.addValue("p_passwd", encoder.encode("test01"));
    	params.addValue("p_userName", "테스트");
    	params.addValue("p_nickname", "TestMan");
    	params.addValue("p_contract1", "lalala");
    	params.addValue("p_contract2", "01010101010");
    	params.addValue("p_authorizeCode", "1");
    	params.addValue("p_createDate", Calendar.getInstance().getTime());
    	params.addValue("p_updateDate", null);
    	params.addValue("p_disabled", null);

    	User user = (User) params.populate(User.class);
    	// insert query를 하게 되면 파라미터인 user에 insert 된 key가 매핑되어 return 된다.
    	userService.addUser(user);
    	
    	String[] roleSeqs = new String[]{"role90000000", "role10000000"};
		
		boolean result2 = false;
		Parameters<String, String> p = new Parameters<String, String>();
		for(String roleSeq : roleSeqs){
			p.clear();
			p.addValue("userSeq", user.getUserSeq());
			p.addValue("roleSeq", roleSeq);
			
			result2 = (userService.addUserRole(p) == 1);
		}
		
		// 모든 유저는 역할을 가지고 있다. 불러와 주어야 한다.
		p.clear();
		p.addValue("userSeq", user.getUserSeq());
		List<Role> roles = (List<Role>) roleService.userRoles(p);
    	user.setRoles(roles);
    	
    	System.out.println("user > \n"+JSONUtil.encode(user));
    }
    
    @Test
	public void isExits() {
    	Parameters<String, String> params = new Parameters<String, String>();
    	params.addValue("userId", "test01@test.com");
    	System.out.println("isExits >>>>>>>>> "+userService.isExits(params));
	}
    
    @Test
    public void testGetUser(){
    	Parameters<String, String> params = new Parameters<String, String>();
    	params.addValue("userSeq", "2");
    	params.addValue("isDisable", "");
    	User user = (User) userService.getUser(params);
    	
    	// 비밀번호 일치 확인
    	if(encoder.matches("test01@test.com", user.getPasswd())){
    		System.out.println("비밀번호 일치!!");
    		
    		// 모든 유저는 역할을 가지고 있다. 불러와 주어야 한다.
    		List<Role> roles = (List<Role>) roleService.userRoles(params);
    		user.setRoles(roles);
    		
    		System.out.println("testGetUser > \n"+JSONUtil.encode(user));
    	}
    }
    
    @Test
    public void testUpdateUser(){
    	Parameters<String, String> params = new Parameters<String, String>();
    	params.addValue("userSeq", "1");
    	params.addValue("isDisable", "true");
    	
    	User user = (User) userService.getUser(params);
    	System.out.println("testUpdateUser _ userSeq > "+user.getUserSeq());
    	user.setDisabled(null);
    	user.setNickname("Test Completed");
    	
    	userService.updateUser(user);
    }
    
    @Test
    public void testDisabledUser(){
    	userService.disableUser(1);
    }
    
    @Test
    public void testSelectAllUser(){
        Parameters<String, String> params = new Parameters<String, String>();
        params.addValue("isDisable", "");
        List<User> users = userService.listUsers(params);
        System.out.println(JSONUtil.toJSON(users, null));
    }
    
    @Test
    public void testGetAllUser(){
    	Parameters<String, String> params = new Parameters<String, String>();
    	params.addValue("isDisable", "");
    	PagedList<User> users = userService.listUsers(params, 0, Integer.MAX_VALUE);
    	System.out.println(JSONUtil.toExtJSON(users));
    }
}
