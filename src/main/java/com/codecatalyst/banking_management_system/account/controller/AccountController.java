
package com.codecatalyst.banking_management_system.account.controller;

import com.codecatalyst.banking_management_system.account.service.AccountService;
import com.codecatalyst.banking_management_system.entity.Account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    // Constructor Injection
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Display account opening page
    @GetMapping("/new")
    public String showAccountForm(Model model) {

        model.addAttribute("accountTypes",
                Account.AccountType.values());

        return "account/open-account";
    }

    // Create a new account
    @PostMapping("/create")
    public String createAccount(
            @RequestParam("accountType")
            Account.AccountType accountType,
            RedirectAttributes redirectAttributes) {

        Account account = accountService.createAccount(accountType);

        redirectAttributes.addFlashAttribute(
                "message",
                "Account created successfully! Account Number: "
                        + account.getAccountNumber()
        );

        return "redirect:/accounts";
    }

    // Display all accounts
    @GetMapping
    public String getAllAccounts(Model model) {

        List<Account> accounts =
                accountService.getAllAccounts();

        model.addAttribute("accounts", accounts);

        return "account/account-list";
    }

    // Display account details
    @GetMapping("/{accountNumber}")
    public String getAccountDetails(
            @PathVariable String accountNumber,
            Model model) {

        Account account =
                accountService.getAccountByNumber(accountNumber);

        model.addAttribute("account", account);

        return "account/account-details";
    }
}
