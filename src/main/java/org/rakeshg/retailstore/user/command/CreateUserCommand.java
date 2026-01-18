package org.rakeshg.retailstore.user.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserCommand {
    private String phone;
    private String name;
    private Long storeId;
    private String role;
}
