package com.codecatalyst.banking_management_system.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;

import com.codecatalyst.banking_management_system.dto.CustomerProfileRequest;
import com.codecatalyst.banking_management_system.entity.User;
import com.codecatalyst.banking_management_system.repository.UserRepository;
import com.codecatalyst.banking_management_system.service.CustomerProfileService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerProfileService profileService;
    private final UserRepository userRepository;

    public CustomerController(
            CustomerProfileService profileService,
            UserRepository userRepository) {
        this.profileService = profileService;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() ->
                    new RuntimeException("User not found")
                );
    }

    @GetMapping("/profile")
    public String viewProfile(
            Principal principal,
            Model model) {

        User user = getLoggedInUser(principal);

        model.addAttribute(
            "profile",
            profileService.getProfile(user)
        );

        return "customer/profile";
    }

    @GetMapping("/edit-profile")
    public String editProfile(
            Principal principal,
            Model model) {

        User user = getLoggedInUser(principal);

        model.addAttribute(
            "profileRequest",
            profileService.getProfile(user)
        );

        return "customer/edit-profile";
    }

    @PostMapping("/edit-profile")
    public String updateProfile(
            @Valid @ModelAttribute("profileRequest")
            CustomerProfileRequest request,
            BindingResult result,
            Principal principal,
            Model model) {

        if (result.hasErrors()) {
            return "customer/edit-profile";
        }

        User user = getLoggedInUser(principal);

        profileService.updateProfile(user, request);

        return "redirect:/customer/profile";
    }
}
