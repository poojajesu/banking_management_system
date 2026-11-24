package com.codecatalyst.banking_management_system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codecatalyst.banking_management_system.dto.CustomerProfileRequest;
import com.codecatalyst.banking_management_system.entity.CustomerProfile;
import com.codecatalyst.banking_management_system.entity.User;
import com.codecatalyst.banking_management_system.repository.CustomerProfileRepository;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository profileRepository;

    public CustomerProfileService(
            CustomerProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Transactional
    public CustomerProfile createProfile(
            User user,
            CustomerProfileRequest request) {

        if (profileRepository.findByPhone(request.getPhone())
                .isPresent()) {
            throw new IllegalArgumentException(
                "Phone number already registered"
            );
        }

        CustomerProfile profile = new CustomerProfile();

        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setUser(user);

        return profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public CustomerProfile getProfile(User user) {

        return profileRepository.findByUser(user)
                .orElseThrow(() ->
                    new RuntimeException("Profile not found")
                );
    }

    @Transactional
    public CustomerProfile updateProfile(
            User user,
            CustomerProfileRequest request) {

        CustomerProfile profile = getProfile(user);

        profileRepository.findByPhone(request.getPhone())
                .filter(existing ->
                    !existing.getId().equals(profile.getId()))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                        "Phone number already registered"
                    );
                });

        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());

        return profileRepository.save(profile);
    }
}
