package com.bookstore.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.dto.UserDto;
import com.bookstore.model.response.ResponseLogin;
import com.bookstore.model.response.ResponseMessage;
import com.bookstore.service.IUserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class LoginController {
	
	@Autowired
	private IUserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> createUser(@RequestBody UserDto user) {
		String message = userService.createUser(user);
		return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDto user) {
		ResponseLogin res = userService.login(user);
		return ResponseEntity.ok(res);
	}
}
