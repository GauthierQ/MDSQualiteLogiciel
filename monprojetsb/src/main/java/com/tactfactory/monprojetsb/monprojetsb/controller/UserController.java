package com.tactfactory.monprojetsb.monprojetsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tactfactory.monprojetsb.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.monprojetsb.repositories.UserRepository;

@Controller
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = {"/index", "/"})

	public String index(Model model) {
		model.addAttribute("page", "User index");
		model.addAttribute("items", userRepository.findAll());
		return "user/index";
	}
	
	
	@GetMapping(value = {"/create"})
		public String createGet(Model model) {
		model.addAttribute("page", "user create");
		return "user/create";
	}
	
	
	@PostMapping(value = {"/create"})
	public String createPost(@ModelAttribute User user) {
		if (user != null) {
			userRepository.save(user);
		}
		return "redirect:index";
	}
	
	public void delete() {
		
	}
	
	public void details() {
		
	}
	
}
