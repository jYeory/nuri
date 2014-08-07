package com.nuri.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nuri.common.domain.SecurityObject;

@Alias("User")
public class User extends SecurityObject implements Serializable, UserDetails, Authentication {

	private static final long serialVersionUID = 1L;
	
	private String userSeq;
	private int userType;
	private String userId;
	private String passwd;
	private String userName;
	private String nickname;
	private String contract1;
	private String contract2;
	private String authorizedCode;
	private Date authorizedDate;
	private int hasNotice;
	private Date createDate;
	private Date updateDate;
	private Date disabled;
	
	private List<Role> roles;

	public String getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getContract1() {
		return contract1;
	}

	public void setContract1(String contract1) {
		this.contract1 = contract1;
	}

	public String getContract2() {
		return contract2;
	}

	public void setContract2(String contract2) {
		this.contract2 = contract2;
	}

	public String getAuthorizedCode() {
		return authorizedCode;
	}

	public void setAuthorizedCode(String authorizedCode) {
		this.authorizedCode = authorizedCode;
	}

	public Date getAuthorizedDate() {
		return authorizedDate;
	}

	public void setAuthorizedDate(Date authorizedDate) {
		this.authorizedDate = authorizedDate;
	}

	public int getHasNotice() {
		return hasNotice;
	}

	public void setHasNotice(int hasNotice) {
		this.hasNotice = hasNotice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getDisabled() {
		return disabled;
	}

	public void setDisabled(Date disabled) {
		this.disabled = disabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
	/* Spring Security */

	public void setIsAccountNonExpired(boolean accountNonExpired){
		this.accountNonExpired = accountNonExpired;
	}

	public void setIsAccountNonLocked(boolean accountNonLocked){
		this.accountNonLocked = accountNonLocked;
	}
	
	public void setIsCredentialsNonExpired(boolean credentialsNonExpired){
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public void setIsEnabled(boolean isEnabled){
		this.accepted = isEnabled;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public void setName(String authority) {
		this.name = authority;
	}
	
	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getDetails() {
		return details;
	}
	
	@Override
	public Object getPrincipal() {
		return principal;
	}
	
	public void setPrincipal(Object principal){
		this.principal = principal;
	}
	
	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}
	
	@SuppressWarnings("unchecked")
	public List<SimpleGrantedAuthority> getAuth(){
		return (List<SimpleGrantedAuthority>) this.getAuthorities();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		for(Role role : roles){
			list.add(new SimpleGrantedAuthority(role.getRoleCode()));
		}
        return list;
	}
	
	@Override
	public String getPassword() {
		return this.passwd;
	}
	@Override
	public String getUsername() {
		return this.userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	@Override
	public boolean isEnabled() {
		return super.accepted;
	}
}
