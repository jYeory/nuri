package com.nuri.service.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nuri.common.service.AbstractGenericManager;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.dao.RoleDao;
import com.nuri.dao.UserDao;
import com.nuri.domain.User;
import com.nuri.service.UserService;

@RemoteProxy(name="UserDwr")
@Service("userService")
public class UserServiceImpl extends AbstractGenericManager implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private StandardPasswordEncoder passwordEncoder;
	
	public StandardPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(StandardPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@RemoteMethod
	@Override
	public User login(String userId, String passwd) {
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Parameters<String, String> params = new Parameters<String, String>();
		params.put("userId", userId);
		User user = null;
		try{
			user = userDao.getUser(params);
			System.out.println(user.getPasswd());
			// 유저가 없을경우.
			if(user == null){
				return null;
			}
			user.setRoles(roleDao.userRoles(params));
			
			user.setName( user.getAuth().get(0).getAuthority() );
			user.setAuthenticated(true);
			user.setIsCredentialsNonExpired(true);
			user.setIsAccountNonExpired(true);
			user.setIsAccountNonLocked(true);
			user.setIsEnabled(true);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}catch(Exception e){
			StringBuilder logMsg = (new StringBuilder("Username(")).append(userId).append(") access exception!");
			logger.error(logMsg.toString(), e);
            throw new UsernameNotFoundException(logMsg.toString(), e);
		}
		return user;
	}
	
	@Override
	public User secureLogin(String userId, String passwordHash, String serverTime) {
		return null;
	}
	
	@Override
	public User getUser(Parameters<String, String> params) {
		return userDao.getUser(params);
	}

	@RemoteMethod
	@Override
	public int isExits(Parameters<String, String> params) {
		return userDao.isExits(params);
	}

	@Override
	@Transactional
	public int addUser(User user) {
		return userDao.addUser(user);
	}
	
	@Override
	@RemoteMethod
	@Transactional
	public int addUser(Parameters<String, String> params) {
		User user = (User) params.populate("", User.class);
		user.setPasswd(passwordEncoder.encode(user.getPasswd()));
		user.setUserType(0);		// 개인
		boolean result1 = (userDao.addUser(user) == 1);
		
		String[] roleSeqs = new String[]{"role90000000", "role10000000"};
		
		boolean result2 = false;
		for(String roleSeq : roleSeqs){
			params.clear();
			params.addValue("userSeq", user.getUserSeq());
			params.addValue("roleSeq", roleSeq);
			
			result2 = (userDao.addUserRole(params) == 1);
		}
		
		return (result1 && result2)?1:0;
	}
	
	@Override
	@RemoteMethod
	@Transactional
	public int addUserRole(Parameters<String, String> params) {
		return userDao.addUserRole(params);
	}

	@Override
	public int disableUser(int id) {
		return userDao.disableUser(id);
	}

	@Override
	@Transactional
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public List<User> listUsers(Parameters<String, String> params) {
//		return (List<User>) list("com.nuri.dao.UserDao", "listUser", params);
		return userDao.listUser(params);
	}

	@SuppressWarnings({ "unchecked" })
	@RemoteMethod
	@Override
	public PagedList<User> listUsers(Parameters<String, String> params, int pg, int ps) {
		return (PagedList<User>) list("com.nuri.dao.UserDao", "listUser", params, pg, ps);
	}

}
