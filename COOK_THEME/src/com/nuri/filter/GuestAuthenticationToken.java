package com.nuri.filter;

import java.util.Collection;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


public class GuestAuthenticationToken extends AnonymousAuthenticationToken {

	private static final long serialVersionUID = 1L;

	public GuestAuthenticationToken(String key, Object principal, Collection<? extends GrantedAuthority> authorities) { 
		super(key, principal, authorities);
	}

}
