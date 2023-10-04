package be.julienbastin.conditionalresponsebackend.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class KeycloakSecurityConverter implements Converter<Jwt, Mono<JwtAuthenticationToken>> {

    @Override
    public Mono<JwtAuthenticationToken> convert(@NonNull Jwt source) {
        return Mono.just(new JwtAuthenticationToken(source, extractAuthorities(source)));
    }

    private Set<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if (realmAccess == null) {
            return Set.of();
        }

        Object rolesObj = realmAccess.get("roles");
        if (!(rolesObj instanceof List<?> roles)) {
            return Set.of();
        }

        for (Object role : roles) {
            if (!(role instanceof String)) {
                return Set.of();
            }
        }

        return ((List<String>)roles).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
