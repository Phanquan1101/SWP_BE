package com.crowdsourced.wasteplatform.dto.auth.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @Email(message = "Email is invalid")
    private String email;

    private String phone;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank
    private String fullName;

    private String areaId; // UUID as String, optional

    @AssertTrue(message = "Either email or phone must be provided")
    public boolean isEmailOrPhonePresent() {
        return (email != null && !email.isBlank()) || (phone != null && !phone.isBlank());
    }
}
