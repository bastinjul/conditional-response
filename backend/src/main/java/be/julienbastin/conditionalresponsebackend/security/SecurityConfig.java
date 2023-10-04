package be.julienbastin.conditionalresponsebackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public KeycloakSecurityConverter keycloakSecurityConverter() {
        return new KeycloakSecurityConverter();
    }

    @Order(1)
    @Bean
    public SecurityWebFilterChain clientFilterChain(ServerHttpSecurity httpSecurity, KeycloakSecurityConverter keycloakSecurityConverter) {
        return httpSecurity
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/**"))
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange()
                        .authenticated())
                .oauth2ResourceServer(server -> server
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(keycloakSecurityConverter)))
                .build();
    }

}
