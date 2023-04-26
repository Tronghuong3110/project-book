package com.bookstore.model.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookstore.model.entity.RoleEntity;
import com.bookstore.model.entity.UserEntity;

public class Myuser implements UserDetails{
	
	private Long id;
	private String fullName;
	private String email;
	private String userName;
	private String password;
	private Collection<? extends GrantedAuthority> roles;

	public Myuser(Long id, String fullName, String email, String userName, String password,
			Collection<? extends GrantedAuthority> roles) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.roles = roles;
	}

	public static Myuser build(UserEntity userEntity) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		// dua role tu user ve role cua he thong
		for(RoleEntity role : userEntity.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return new Myuser(
				userEntity.getId(),
				userEntity.getFullName(),
				userEntity.getEmail(),
				userEntity.getUserName(),
				userEntity.getPassWord(),
				authorities
		);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
