
package com.codecatalyst.banking_management_system.account.repository;

import com.codecatalyst.banking_management_system.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Find account using account number
    Optional<Account> findByAccountNumber(String accountNumber);

    // Check whether account number already exists
    boolean existsByAccountNumber(String accountNumber);

}
