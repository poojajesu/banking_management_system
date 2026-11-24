
package com.codecatalyst.banking_management_system.account.service;

import com.codecatalyst.banking_management_system.account.repository.AccountRepository;
import com.codecatalyst.banking_management_system.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final SecureRandom random = new SecureRandom();

    // Constructor Injection
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Generate unique account number
    private String generateAccountNumber() {

        String accountNumber;

        do {
            long number = 100000000000L
                    + random.nextLong(900000000000L);

            accountNumber = String.valueOf(number);

        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    // Create new bank account
    @Transactional
    public Account createAccount(Account.AccountType accountType) {

        Account account = new Account();

        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(accountType);
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(Account.AccountStatus.ACTIVE);

        return accountRepository.save(account);
    }

    // Get all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Find account by account number
    public Account getAccountByNumber(String accountNumber) {

        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));
    }

    // Check account balance
    public BigDecimal checkBalance(String accountNumber) {

        Account account = getAccountByNumber(accountNumber);

        return account.getBalance();
    }
}
