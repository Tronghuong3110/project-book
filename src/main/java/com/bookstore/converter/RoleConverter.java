package com.bookstore.converter;

import com.bookstore.model.dto.RoleDto;
import com.bookstore.model.entity.RoleEntity;

public class RoleConverter {

    public static RoleDto toDto(RoleEntity role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }
}
