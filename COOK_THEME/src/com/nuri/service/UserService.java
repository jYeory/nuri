package com.nuri.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.User;

public interface UserService extends UserDetailsService {
	/**
	 *  Web에서 호출되는 로그인 메서드
	 */
	User login(String userId, String passwd);
	/**
	 *  실제 로그인 비지니스 로직, Spring Security를 이용하기 때문에 사용하지 않는다..
	 */
	User secureLogin(String userId, String passwordHash, String serverTime);
	
	/**
	 *  조건에 의한 1개의 User 오브젝트를 가져온다. 
	 */
	User getUser(Parameters<String, String> params);
	/**
	 *	User 추가 
	 */
	int addUser(Parameters<String, String> params);
	
	/**
	 *	User Role 추가 
	 */
	int addUserRole(Parameters<String, String> params);
	/**
	 *	User 추가 
	 */
	int addUser(User user);
	/**
	 *	User 비사용 처리 
	 */
	int disableUser(int id);
	/**
	 *	User 정보 수정 
	 */
	int updateUser(User user);
	/**
	 * 	ID 중복확인
	 */
	int isExits(Parameters<String, String> params);
	
	/**
	 *	조건에 맞는 사용자를 페이징 없이 가져온다. 
	 */
	List<User> listUsers(Parameters<String, String> params);
	
	/**
	 *	조건에 맞는 사용자를 페이징 처리하여 가져온다. 
	 */
	PagedList<User> listUsers(Parameters<String, String> params, int pg, int ps);
	
}
