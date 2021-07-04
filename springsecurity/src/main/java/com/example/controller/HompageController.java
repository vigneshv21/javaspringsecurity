package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Basic;
import com.example.model.BasicReponse;
import com.example.service.JWTConfig;
import com.example.service.UserService;

@RestController
@RequestMapping("content")
public class HompageController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTConfig jwtConfig;

	@Autowired
	private UserService userService;

	@GetMapping("/welcome")
	public String welcome() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		return "<h1>Hello Vignesh</h1>";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<BasicReponse> authenticate(@RequestBody Basic basic) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(basic.getName(), basic.getPassword()));
		UserDetails userDetails = this.userService.loadUserByUsername(basic.getName());
		final String jwt=this.jwtConfig.generateToken(userDetails);
		return ResponseEntity.ok(new BasicReponse(jwt));
	}
}