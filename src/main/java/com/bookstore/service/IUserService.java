package com.bookstore.service;

import com.bookstore.model.dto.UserDto;
import com.bookstore.model.response.ResponseLogin;

public interface IUserService {
	String createUser(UserDto user);
	ResponseLogin login(UserDto user);
}
