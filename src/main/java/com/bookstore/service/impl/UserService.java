package com.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.bookstore.converter.UserConverter;
import com.bookstore.model.dto.Myuser;
import com.bookstore.model.dto.UserDto;
import com.bookstore.model.entity.RoleEntity;
import com.bookstore.model.entity.UserEntity;
import com.bookstore.model.response.ResponseLogin;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.security.JwtProvider;
import com.bookstore.service.IUserService;

import com.bookstore.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public String createUser(UserDto user) {
		if (!Constants.validateEmail(user.getEmail())) {
			return "INVALID Email";
		}
		if(userRepository.existsByUserName(user.getUserName())) {
			return "The user name existed! Please try again";
		}
		if(userRepository.existsByEmail(user.getEmail())) {
			return "The email existed! Please try again";
		}
		List<RoleEntity> roles = new ArrayList<>();
		UserEntity userEntity = UserConverter.toEntity(user);
//		RoleEntity role = roleRepository.findByName("USER")
//				.orElseThrow(() -> null);
		RoleEntity role = roleRepository.findByName("USER")
				.orElseThrow(() -> new NoSuchElementException("No role found"));
		roles.add(role);
		userEntity.setRoles(roles);
		userEntity.setPassWord(passwordEncoder.encode(user.getPassword()));

		userRepository.save(userEntity);
		return "Create user success";
	}

	@Override
	public ResponseLogin login(UserDto user) {
		Authentication authentication = authenticationManager.authenticate((
				new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
		));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.createToken(authentication);
		Myuser myUser = (Myuser) authentication.getPrincipal();
		ResponseLogin response = new ResponseLogin(myUser.getId(), token, myUser.getFullName(), myUser.getAuthorities());
		return response;
	}

}
