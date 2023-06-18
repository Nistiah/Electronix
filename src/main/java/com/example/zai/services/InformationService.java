package com.example.zai.services;

import com.example.zai.enums.Category;
import com.example.zai.models.Information;
import com.example.zai.repositories.InformationRepository;
import com.example.zai.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InformationService {

    private final InformationRepository informationRepository;

    private final ProductService productService;

    private final OrderService orderService;

    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;

    public Information getInformationByName(String name) {
        return informationRepository.findByName(name);
    }

    public List<Information> getInformationByCategory(String category) {
        return informationRepository.findByCategory(category);
    }

    public List<Information> getAllInformation() {
        return informationRepository.findAll();
    }

    public Model addInformationToModel(Model model) {
        Information phone = getInformationByName("Phone");
        model.addAttribute("Phone", phone);
        Information email = getInformationByName("Email");
        model.addAttribute("Email", email);
        Information address = getInformationByName("Address");
        model.addAttribute("Address", address);
        Information aboutUs = getInformationByName("About Us");
        model.addAttribute("AboutUs", aboutUs);
        List<Category> categories = productService.getAllCategories();
        model.addAttribute("Categories", categories);
        model.addAttribute("Categories", categories);
        int wishlistSize;
        int cartSize;
        try {
            wishlistSize = orderService.getNumberOfProductsInWishlist(userRepository.getReferenceById(authenticationService.getLoggedUser().getId()));
            cartSize = orderService.getNumberOfProductsInCart(userRepository.getReferenceById(authenticationService.getLoggedUser().getId()));
        }catch (NullPointerException e){
            wishlistSize = 0;
            cartSize = 0;
        }
        model.addAttribute("WishlistSize", wishlistSize);
        model.addAttribute("CartSize", cartSize);
        return model;
    }

    public void deleteInformation(Long id) {
        informationRepository.deleteById(id);
    }

    public void changeInformation(Long id,  String category, String name, String content) {
        Information information = informationRepository.getReferenceById(id);
        information.setName(name);
        information.setCategory(category);
        information.setContent(content);
        informationRepository.save(information);
    }




}
