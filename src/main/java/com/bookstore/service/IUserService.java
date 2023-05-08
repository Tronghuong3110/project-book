package com.bookstore.service;

import com.bookstore.model.dto.UserDto;
import com.bookstore.model.response.ResponseLogin;

import java.util.List;

public interface IUserService {
	String createUser(UserDto user);
	ResponseLogin login(UserDto user);
	List<UserDto> findAll(String key);
}
