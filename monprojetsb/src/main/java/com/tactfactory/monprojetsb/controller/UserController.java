package com.tactfactory.monprojetsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tactfactory.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.repositories.ProductRepository;
import com.tactfactory.monprojetsb.repositories.UserRepository;

@Controller
@RequestMapping(value = {"/user"})
public class UserController {

	@Autowired
	private UserRepository userRepository;
	private ProductRepository productRepository;

	public UserController(UserRepository userRepository, ProductRepository productRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	@RequestMapping(value = { "/index", "/" })

	public String index(Model model) {
		model.addAttribute("page", "User index");
		model.addAttribute("items", userRepository.findAll());
		return "user/index";
	}

	@GetMapping(value = { "/create" })
	public String createGet(Model model) {
		model.addAttribute("page", "user create");
		model.addAttribute("products", productRepository.findAll());
		return "user/create";
	}

	@PostMapping(value = { "/create" })
	public String createPost(@ModelAttribute User user) {
		if (user != null) {
			userRepository.save(user);
		}
		return "redirect:index";
	}

	@PostMapping(value = {"/delete"})
	public String delete(Long id) {
		User user = userRepository.getOne(id);	
		userRepository.delete(user);
		return "redirect:index";
		
	}

	public void details() {

	}

}
