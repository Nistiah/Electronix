package com.example.zai.controllers;

import com.example.zai.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {


    private final InformationService informationService;

    private final ProductService productService;

    private final OrderService orderService;

    private final AuthenticationService authenticationService;


    @RequestMapping(value = "/myAccount", method = RequestMethod.GET)
    public String showMyAccountPage(Model model, @RequestParam(required = false) String error, @RequestParam(required = false) String success) {
        informationService.addInformationToModel(model);
        if (authenticationService.getLoggedUser() == null) {
            return "login";
        } else {
            model.addAttribute("user", authenticationService.getLoggedUser());

            if (error != null) {
                String decodedError = URLDecoder.decode(error, StandardCharsets.UTF_8);
                model.addAttribute("error", decodedError);
            }

            if (success != null) {
                String decodedSuccess = URLDecoder.decode(success, StandardCharsets.UTF_8);
                model.addAttribute("success", decodedSuccess);
            }
            return "myAccount";
        }
    }


    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String showCartPage(Model model) {
        if (authenticationService.getLoggedUser() == null) {
            return "redirect:redirect";
        }
        model.addAttribute("cart", orderService.getShoppingCart());
        informationService.addInformationToModel(model);
        return "cart";
    }

    @RequestMapping(value = "/wishlist", method = RequestMethod.GET)
    public String showWishlistPage(Model model) {
        if (authenticationService.getLoggedUser() == null) {
            return "redirect:redirect";
        }
        model.addAttribute("wishlist", orderService.getWishList());
        informationService.addInformationToModel(model);
        return "wishlist";
    }


    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String redirectToPage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("alertMessage", "Login to continue");
        return "redirect:/user/myAccount";
    }

    @RequestMapping(value = "/removeItemFromCart", method = RequestMethod.POST)
    public String removeItemFromCart(@RequestParam("productId") Long productId, Model model) {
        productService.removeItemFromCart(productId);
        model.addAttribute("cart", orderService.getShoppingCart());
        informationService.addInformationToModel(model);
        return "cart";
    }


    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    public String addToCart(@RequestParam("productId") Long productId, Model model, HttpServletRequest request) {
        if (authenticationService.getLoggedUser() == null) {
            return "redirect:redirect";
        }
        productService.addToCart(productId);


        model.addAttribute("cart", orderService.getShoppingCart());
        informationService.addInformationToModel(model);
        String referrer = request.getHeader("referer");


        return "redirect:" + referrer;

    }

    @RequestMapping(value = "/removeItemFromWishlist", method = RequestMethod.POST)
    public String removeItemFromWishlist(@RequestParam("productId") Long productId, Model model) {
        System.out.println("removeItemFromWishlist");
        productService.removeItemFromWishList(productId);
        model.addAttribute("wishlist", orderService.getWishList());
        informationService.addInformationToModel(model);
        return "wishlist";
    }

    @RequestMapping(value = "/addToWishList", method = RequestMethod.POST)
    public String addToWishList(@RequestParam("productId") Long productId, Model model, HttpServletRequest request) {
        if (authenticationService.getLoggedUser() == null) {
            return "redirect:redirect";
        }
        productService.addToWishList(productId);

        model.addAttribute("wishlist", orderService.getWishList());
        informationService.addInformationToModel(model);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;

    }

    @RequestMapping(value="/order", method = RequestMethod.POST)
    public String order(@RequestParam("orderId") Long productId, Model model, HttpServletRequest request){
        orderService.order(productId);

        informationService.addInformationToModel(model);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }





}
