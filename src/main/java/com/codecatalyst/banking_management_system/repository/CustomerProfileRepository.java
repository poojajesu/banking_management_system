package com.codecatalyst.banking_management_system.repository;

import com.codecatalyst.banking_management_system.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {

    Optional<CustomerProfile> findByUserId(Long userId);

    Optional<CustomerProfile> findByPhone(String phone);

    boolean existsByPhone(String phone);
}