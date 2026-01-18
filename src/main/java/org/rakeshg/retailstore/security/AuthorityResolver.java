package org.rakeshg.retailstore.security;

import java.util.Set;

public interface AuthorityResolver {
    Set<String> resolve(String role);
}
