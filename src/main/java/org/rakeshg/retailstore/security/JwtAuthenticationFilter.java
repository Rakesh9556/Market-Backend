package org.rakeshg.retailstore.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.auth.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthorityResolver authorityResolver;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth");
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);

            Claims claims = jwtService.parse(token);

            Long userId = Long.valueOf(claims.getSubject());
            Long storeId = claims.get("storeId", Long.class);
            String role = claims.get("role", String.class);

            // Resolve authorities from role
            Set<String> authorities = authorityResolver.resolve(role);

            AuthUser authUser = new AuthUser(
                    userId,
                    storeId,
                    role,
                    authorities
            );

            // Convert to Spring Security authorities
            List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                            .map(SimpleGrantedAuthority::new).toList();

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authUser,
                    null,
                    grantedAuthorities
            );

            // Set authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return;
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

}
