package com.example.zai.controllers;

import com.example.zai.dto.ProductDTO;
import com.example.zai.enums.Category;
import com.example.zai.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController
{

    private final ProductService productService;
    private final InformationService informationService;


    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public String showProductPage(Model model, @PathVariable Long productId) {
        ProductDTO product = productService.getProductById(productId);
        model.addAttribute("product", product);
        informationService.addInformationToModel(model);
        return "product";
    }

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public String showShopPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<ProductDTO> productsPage = productService.getAllProducts(pageable);

        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("controller", "shop");
        informationService.addInformationToModel(model);
        return "index";
    }

    @RequestMapping(value = "/shop/{category}", method = RequestMethod.GET)
    public String showProductsByCategory(Model model, @PathVariable String category, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<ProductDTO> productsPage = productService.getAllProductsByCategory(Category.valueOf(category.toUpperCase()), pageable);

        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("controller", "shop/" + category);
        informationService.addInformationToModel(model);
        return "index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String showProductsBySearch(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam String category, @RequestParam String search) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<ProductDTO> productsPage;

        if(category.equals("All Categories")){
            productsPage = productService.getAllProductsBySearch(search, pageable);
        } else {
            productsPage = productService.getAllProductsByCategoryAndSearch(Category.valueOf(category.toUpperCase()), search, pageable);
        }
        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("controller",  category);
        model.addAttribute("search", search);
        informationService.addInformationToModel(model);
        return "index";
    }
}
