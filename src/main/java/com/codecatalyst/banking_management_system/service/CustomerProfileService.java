package com.codecatalyst.banking_management_system.service;

import com.codecatalyst.banking_management_system.dto.CustomerProfileRequest;
import com.codecatalyst.banking_management_system.dto.CustomerProfileResponse;
import com.codecatalyst.banking_management_system.entity.CustomerProfile;
import com.codecatalyst.banking_management_system.entity.User;
import com.codecatalyst.banking_management_system.repository.CustomerProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;

    public CustomerProfileService(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    @Transactional
    public CustomerProfileResponse createProfile(User user, CustomerProfileRequest request) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("A saved User is required to create a customer profile");
        }
        if (request == null) {
            throw new IllegalArgumentException("Customer profile request is required");
        }
        if (customerProfileRepository.findByUserId(user.getId()).isPresent()) {
            throw new IllegalStateException("A customer profile already exists for this User");
        }
        if (customerProfileRepository.existsByPhone(request.getPhone())) {
            throw new IllegalStateException("A customer profile already exists with this phone number");
        }

        CustomerProfile profile = new CustomerProfile();
        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setUser(user);
        user.setCustomerProfile(profile);

        CustomerProfile savedProfile = customerProfileRepository.save(profile);
        return toResponse(savedProfile);
    }

    @Transactional(readOnly = true)
    public CustomerProfileResponse getProfileByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        CustomerProfile profile = customerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException(
                        "No customer profile exists for User ID " + userId));
        return toResponse(profile);
    }

    private CustomerProfileResponse toResponse(CustomerProfile profile) {
        return new CustomerProfileResponse(
                profile.getId(),
                profile.getFullName(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getDateOfBirth()
        );
    }
}
