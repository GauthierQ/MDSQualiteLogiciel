package com.tactfactory.monprojetsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tactfactory.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.repositories.ProductRepository;
import com.tactfactory.monprojetsb.repositories.UserRepository;


@Controller
@RequestMapping(value = {"/product"})
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@RequestMapping(value = {"/index", "/"})

	public String index(Model model) {
		model.addAttribute("page", "Product index");
		model.addAttribute("items", productRepository.findAll());
		return "product/index";
	}
	
	
	@GetMapping(value = {"/create"})
		public String createGet(Model model) {
		model.addAttribute("page", "product create");
		return "product/create";
	}
	
	
	@PostMapping(value = {"/create"})
	public String createPost(@ModelAttribute Product product) {
		if (product != null) {
			productRepository.save(product);
		}
		return "redirect:index";
	}
	
	
	public void delete() {
		
	}
	
	public void details() {
		
	}
	
}
