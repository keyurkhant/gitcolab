package com.gitcolab.dto.inhouse.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ValidateOTPRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String otp;
}
