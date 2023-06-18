package com.example.zai.controllers;

import com.example.zai.enums.Category;
import com.example.zai.enums.Roles;
import com.example.zai.services.InformationService;
import com.example.zai.services.ProductService;
import com.example.zai.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;

    private final InformationService informationService;

    private final UserService userService;


    @RequestMapping("/adminPage")
    public String showAdminPage(Model model) {
        model = informationService.addInformationToModel(model);
        model.addAttribute("products", productService.getAllProductsWithoutPagination());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("informations", informationService.getAllInformation());
        model.addAttribute("categories", Category.values());
        model.addAttribute("images", productService.getAllImages());
        model.addAttribute("roles", Roles.values());

        return "adminPage";
    }


    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(HttpServletRequest request, @RequestParam String name, @RequestParam String description, @RequestParam String category, @RequestParam String price, @RequestParam String image) {
        productService.addProduct(name, description, Category.valueOf(category), BigDecimal.valueOf(Double.parseDouble(price)), image);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }

    @RequestMapping(value = "/changeProduct", method = RequestMethod.POST)
    public String changeProduct(HttpServletRequest request, @RequestParam Long id, @RequestParam String name, @RequestParam String description, @RequestParam String category, @RequestParam String price, @RequestParam String image) {
        System.out.println("id: " + id + " name: " + name + " description: " + description + " category: " + category + " price: " + price + " image: " + image);

        productService.changeProduct(id, name, description, Category.valueOf(category), Double.valueOf(price), image);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public String deleteProduct(HttpServletRequest request, @RequestParam Long id) {
        productService.deleteProduct(id);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadImage(HttpServletRequest request, @RequestParam("image") MultipartFile image) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String uniqueFilename = System.currentTimeMillis() + "-" + filename;
        String uploadDir = "src/main/resources/static/img";

        Path uploadPath = Path.of(uploadDir);

        Path destinationPath = uploadPath.resolve(uniqueFilename);
        Files.copy(image.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }

    @RequestMapping(value = "/changeUserRole", method = RequestMethod.POST)
    public String changeUserRole(HttpServletRequest request, @RequestParam Long id, @RequestParam String role) {
        userService.changeUserRole(id, Roles.valueOf(role));
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }

    @RequestMapping(value = "/changeInformation", method = RequestMethod.POST)
    public String changeInformation(HttpServletRequest request, @RequestParam Long id, @RequestParam String category, @RequestParam String name, @RequestParam String content) {
        informationService.changeInformation(id, category, name, content);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }

    @RequestMapping(value = "/deleteInformation", method = RequestMethod.POST)
    public String deleteInformation(HttpServletRequest request, @RequestParam Long id) {
        informationService.deleteInformation(id);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }

    String error = "";

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String redirectToPage2(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("alertMessage", error);
        return "redirect:/admin/adminPage";
    }
}


//TODO: orders no cart/wishlist? moze tylko mail
//TODO: nowy cart po zlozeniu orderu
