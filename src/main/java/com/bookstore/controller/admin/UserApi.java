package com.bookstore.controller.admin;

import com.bookstore.model.dto.UserDto;
import com.bookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class UserApi {

    @Autowired
    private IUserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> getAllUser(@RequestParam String key) {
        List<UserDto> results = userService.findAll(key);
        return results;
    }
}

