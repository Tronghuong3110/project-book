package com.bookstore.converter;

import com.bookstore.model.dto.RoleDto;
import com.bookstore.model.dto.UserDto;
import com.bookstore.model.entity.RoleEntity;
import com.bookstore.model.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {
	
	public static UserEntity toEntity(UserDto user) {
		UserEntity entity = new UserEntity();
		entity.setFullName(user.getFullname());
		entity.setUserName(user.getUserName());
		entity.setEmail(user.getEmail());
		return entity;
	}

	public static UserDto toDto(UserEntity entity) {
		UserDto user = new UserDto();
		user.setEmail(entity.getEmail());
		user.setFullname(entity.getFullName());
		user.setId(entity.getId());
		user.setUserName(entity.getUserName());
		user.setRoles(roleDto(entity.getRoles()));
		return user;
	}

	private static List<RoleDto> roleDto(List<RoleEntity> role) {
		List<RoleDto> roles = new ArrayList<>();
		for(RoleEntity entity : role) {
			roles.add(RoleConverter.toDto(entity));
		}
		return roles;
	}
}
