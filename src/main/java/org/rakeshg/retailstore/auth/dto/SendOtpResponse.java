package org.rakeshg.retailstore.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendOtpResponse {
    private String otp;
}
