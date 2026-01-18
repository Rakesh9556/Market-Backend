package org.rakeshg.retailstore.store.store.command;

import lombok.Data;

@Data
public class OnboardOwnerCommand {
    private String phone;
    private String ownerName;
    private String ownerEmail;
    private String storeName;
    private String storeAddress;
}
