package com.bookstore.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookstore.model.dto.Myuser;
import com.bookstore.model.entity.UserEntity;
import com.bookstore.repository.UserRepository;


@Service
public class UserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	// tìm user trên database theo user name
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Name not found" + username));
//		System.out.println(user.getId() + " " + user.getUserName() + " " + user.getPassWord());
		return Myuser.build(user);
	}

}
