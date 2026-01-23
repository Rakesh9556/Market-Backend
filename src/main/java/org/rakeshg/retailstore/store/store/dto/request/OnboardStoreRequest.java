package org.rakeshg.retailstore.store.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.rakeshg.retailstore.store.store.command.OnboardStoreCommand;

@Data
public class OnboardStoreRequest {
    @NotBlank private String owner;
    @NotBlank private String storeName;
    @NotBlank private String address;

    public OnboardStoreCommand toCommand() {
        return OnboardStoreCommand.builder()
                .owner(owner)
                .storeName(storeName)
                .address(address)
                .build();
    }
}
