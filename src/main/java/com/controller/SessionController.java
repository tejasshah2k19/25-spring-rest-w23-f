package com.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginDto;

@RestController
public class SessionController {

	// login api ->

	@PostMapping("login")
	public LoginDto login(@RequestBody LoginDto loginDto) {
		// validation
		// authentication
		return loginDto;// json
	}

	// signup api
	// jpa , mysql , user en , user repo
	//
	// android iphone webapp -->
	// rest client => POSTMAN

	// POST:new GET:read DELETE:delete PUT:update

}
