package com.codecatalyst.banking_management_system.auth;

import com.codecatalyst.banking_management_system.dto.RegisterRequest;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {

        model.addAttribute("registerRequest", new RegisterRequest());

        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(
            @Valid @ModelAttribute("registerRequest") RegisterRequest request,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        try {
            authService.registerCustomer(request);
            return "redirect:/login?registered=true";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }

    @GetMapping("/home")
    public String home(
            org.springframework.security.core.Authentication authentication) {

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN")
                );

        if (isAdmin) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/customer/dashboard";
    }
}