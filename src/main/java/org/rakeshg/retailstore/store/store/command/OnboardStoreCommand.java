package org.rakeshg.retailstore.store.store.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OnboardStoreCommand {
    private String owner;
    private String storeName;
    private String address;
}
