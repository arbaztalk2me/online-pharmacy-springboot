package com.arbaz.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.arbaz.demo.models.User;
import com.arbaz.demo.services.UserService;

@Controller
public class DefaultController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String Home() {
		return "index";
	}
	
	@GetMapping("/signup")
	public String Signup(Model m) {
		m.addAttribute("user",new User());
		return "signup";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("user") User user) {
		user.setActive(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.addUser(user);
		
		return "signup";
	}
	
	@GetMapping("/signin")
	public String Signin(Model m) {	
		return "Login";
	}
	
	
	
}
