package com.silent.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silent.model.User;
import com.silent.model.UserRequest;
import com.silent.model.UserResponse;
import com.silent.service.UserService;
import com.silent.utility.JwtUtil;

@RestController
@RequestMapping("/jwt/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	//1. Save User in database
	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		return new ResponseEntity<User>(userService.insert(user), HttpStatus.CREATED);
	}
	
	//2. Validate user and generate token(login)
	@PostMapping(path = "/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		//generating token
		String token = util.generateToken(request.getUsername());
		return new ResponseEntity<UserResponse>(new UserResponse(token, "Success! Generated by Silent"), HttpStatus.OK);
	}
	
	
	@PostMapping(path = "/welcome")
	public ResponseEntity<String> accessData(Principal p){		
		return new ResponseEntity<String>("Hello My Love : "+p.getName(), HttpStatus.OK);
	}

}
