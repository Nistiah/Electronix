package com.example.zai.controllers;

import com.example.zai.models.Information;
import com.example.zai.services.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/information")
@RequiredArgsConstructor
public class MyInformationController {
    private final InformationService informationService;

    @RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
    public String showAboutUsPage(Model model) {
        List<Information> information = informationService.getInformationByCategory("AboutUs");
        model.addAttribute("informations", information);
        informationService.addInformationToModel(model);
        return "information";
    }

    @RequestMapping(value = "/privacyPolicy", method = RequestMethod.GET)
    public String showPrivacyPolicyPage(Model model) {
        List<Information> information = informationService.getInformationByCategory("privacyPolicy");
        model.addAttribute("informations", information);
        informationService.addInformationToModel(model);
        return "information";
    }

    @RequestMapping(value = "/ordersAndReturns", method = RequestMethod.GET)
    public String showShippingAndReturnsPage(Model model) {
        List<Information> information = informationService.getInformationByCategory("ordersAndReturns");
        model.addAttribute("informations", information);
        informationService.addInformationToModel(model);
        return "information";
    }

    @RequestMapping(value = "/termsAndConditions", method = RequestMethod.GET)
    public String showTermsAndConditionsPage(Model model) {
        List<Information> information = informationService.getInformationByCategory("termsAndConditions");
        model.addAttribute("informations", information);
        informationService.addInformationToModel(model);
        return "information";

    }

    @RequestMapping(value = "/faq", method = RequestMethod.GET)
    public String showFaqPage(Model model) {
        List<Information> information = informationService.getInformationByCategory("FAQ");
        model.addAttribute("informations", information);
        informationService.addInformationToModel(model);
        return "information";
    }

    @RequestMapping(value = "/contactUs", method = RequestMethod.GET)
    public String showContactUsPage(Model model) {
        List<Information> information = informationService.getInformationByCategory("ContactUs");
        model.addAttribute("informations", information);
        informationService.addInformationToModel(model);
        return "information";
    }






}
