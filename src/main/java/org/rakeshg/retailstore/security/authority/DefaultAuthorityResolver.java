package org.rakeshg.retailstore.security.authority;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DefaultAuthorityResolver implements AuthorityResolver {
    @Override
    public Set<String> resolve(String role) {

        if(role == null) return Set.of();

        return switch(role) {

            case "OWNER" -> Set.of(
                    Authorities.ROLE_OWNER,
                    Authorities.STORE_READ,
                    Authorities.STORE_WRITE,
                    Authorities.INVENTORY_READ,
                    Authorities.INVENTORY_WRITE
            );

            case "STAFF" -> Set.of(
                    Authorities.ROLE_STAFF,
                    Authorities.STORE_READ,
                    Authorities.INVENTORY_READ
            );

            default -> Set.of();

        };
    }
}
