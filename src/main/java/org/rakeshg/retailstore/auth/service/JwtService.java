package org.rakeshg.retailstore.auth.service;

import io.jsonwebtoken.Claims;
import org.rakeshg.retailstore.user.model.User;

public interface JwtService {
    String generate(User user);
    Claims parse(String token);
}
