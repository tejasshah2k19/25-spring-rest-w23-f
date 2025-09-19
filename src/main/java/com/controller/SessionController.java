package com.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginDto;

@RestController
@RequestMapping("/api/public")
public class SessionController {

	// login api ->

	@PostMapping("login")
	public HashMap<String,Object> login(@RequestBody LoginDto loginDto) {
		// validation
		// authentication
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("data", loginDto);
		hm.put("token", "123");
		hm.put("profile", loginDto);
		return hm;// json
	}

	// signup api
	// jpa , mysql , user en , user repo
	//
	// android iphone webapp -->
	// rest client => POSTMAN

	// POST:new GET:read DELETE:delete PUT:update

}
