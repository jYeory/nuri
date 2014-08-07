package com.nuri.common.domain;

import java.io.Serializable;

public class SecurityObject implements Serializable{

	private static final long serialVersionUID = 8359426924640562032L;
	
	protected boolean accountNonExpired;
	protected boolean accountNonLocked;
	protected boolean credentialsNonExpired;
	protected boolean accepted;
	
	protected String name;
	protected Object credentials;
	protected Object details;
	protected Object principal;
	protected boolean authenticated;

}
