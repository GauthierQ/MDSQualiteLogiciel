package com.tactfactory.monprojetsb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tactfactory.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.services.ProductService;
import com.tactfactory.monprojetsb.services.UserService;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    private ProductService productService;

    public UserController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @RequestMapping(value = { "/index", "/" })
    public String index(Model model) {
        model.addAttribute("page", "User index");
        model.addAttribute("items", userService.findAll());
        return "user/index";
    }

    @GetMapping(value = {"/create"})
    public String createGet(Model model) {
        model.addAttribute("page", "User Create");
        model.addAttribute("products", productService.findAll());
        return "user/create";
    }

    @PostMapping(value = {"/create"})
    public String createPost(@ModelAttribute User user) {
        if (user != null) {
            userService.save(user);
        }
        return "redirect:index";
    }

    @PostMapping(value = {"/delete"})
    public String delete(Long id) {
        User user = userService.getUserById(id);
        userService.delete(user);
        return "redirect:index";
    }

    @GetMapping(value = {"/show/{id}"})
    public String details(Model model, @PathVariable(value = "id") String id) {
        model.addAttribute("user", userService.getUserById(Long.parseLong(id)));
        model.addAttribute("items", userService.getUserById(Long.parseLong(id)).getProducts());
        return "user/detail";
    }
}
