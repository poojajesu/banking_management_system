package com.codecatalyst.banking_management_system.dto;

import java.time.LocalDate;

public class CustomerProfileResponse {

    private final Long id;
    private final String fullName;
    private final String phone;
    private final String address;
    private final LocalDate dateOfBirth;

    public CustomerProfileResponse(Long id, String fullName, String phone,
                                   String address, LocalDate dateOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
