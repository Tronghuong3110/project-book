package com.bookstore.converter;

import com.bookstore.model.dto.UserDto;
import com.bookstore.model.entity.UserEntity;

public class UserConverter {
	
	public static UserEntity toEntity(UserDto user) {
		UserEntity entity = new UserEntity();
		entity.setFullName(user.getFullname());
		entity.setUserName(user.getUserName());
		entity.setEmail(user.getEmail());
		return entity;
	}
}
