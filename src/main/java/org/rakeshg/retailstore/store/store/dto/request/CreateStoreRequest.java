package org.rakeshg.retailstore.store.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.rakeshg.retailstore.store.store.command.CreateStoreCommand;

@Data
public class CreateStoreRequest {
    @NotBlank private String storeName;
    @NotBlank private String email;
    @NotBlank private String phone;
    @NotBlank private String address;

    public CreateStoreCommand toCommand() {
        return CreateStoreCommand.builder()
                .storeName(storeName)
                .email(email)
                .phone(phone)
                .address(address)
                .build();
    }
}
