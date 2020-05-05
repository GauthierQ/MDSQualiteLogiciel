package com.tactfactory.monprojetsb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tactfactory.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.services.ProductService;


@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = { "/index", "/" })
    public String index(Model model) {
        model.addAttribute("page", "Product index");
        model.addAttribute("items", productService.findAll());
        return "product/index";
    }

    @GetMapping(value = {"/create"})
    public String createGet(Model model) {
        model.addAttribute("page", "Product create");
        return "product/create";
    }

    @PostMapping(value = {"/create"})
    public String createPost(@ModelAttribute Product product) {
        if (product != null) {
            productService.save(product);
        }
        return "redirect:index";
    }

    @PostMapping(value = {"/delete"})
    public String delete(Long id) {
        Product product = productService.getProductById(id);
        productService.delete(product);
        return "redirect:index";
    }

    @GetMapping(value = {"/show/{id}"})
    public String details(Model model, @PathVariable(value = "id") String id) {
        model.addAttribute("product", productService.getProductById(Long.parseLong(id)));
        return "product/detail";
    }

}
