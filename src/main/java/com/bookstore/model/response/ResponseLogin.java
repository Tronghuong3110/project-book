package com.bookstore.model.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class ResponseLogin {

	private Long id;
	private String token;
	private String fullName;
	private String type = "Bearer";
	private Collection<? extends GrantedAuthority> roles;

	public ResponseLogin(Long id, String token, String fullName, Collection<? extends GrantedAuthority> roles) {
		super();
		this.id = id;
		this.token = token;
		this.fullName = fullName;
		this.setRoles(roles);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}
	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}
	
	
}
