package org.rakeshg.retailstore.security.authority;

import java.util.Set;

public interface AuthorityResolver {
    Set<String> resolve(String role);
}
