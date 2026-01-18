package org.rakeshg.retailstore.store.store.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStoreCommand {
    private String storeName;
    private String email;
    private String phone;
    private String address;
}
