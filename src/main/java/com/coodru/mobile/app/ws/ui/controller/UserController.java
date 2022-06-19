package com.coodru.mobile.app.ws.ui.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "users")
public class UserController {

	@GetMapping
	public String getUser()	{
		return "get user method was called";
	}

	@PostMapping
	public String createUser() {
		return "create user method was called";
	}

	@PutMapping
	public String updateUser() {
		return "update user method was called";
	}

	@DeleteMapping
	public String deleteUser() {
		return "delete method was called";
	}
}
