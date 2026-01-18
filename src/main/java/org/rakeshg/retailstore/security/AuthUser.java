package org.rakeshg.retailstore.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

@Getter
@AllArgsConstructor
public class AuthUser implements Serializable {
    private final Long userId;
    private final Long storeId;
    private final String role;
    private final Set<String> authorities;

}
