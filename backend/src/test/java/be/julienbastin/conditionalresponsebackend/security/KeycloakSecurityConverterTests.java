package be.julienbastin.conditionalresponsebackend.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

class KeycloakSecurityConverterTests {

    private static final String TOKEN = "FyZXIiLCJhenAiOiJjb25kaXRpb25hbC1yZXNwb25zZS1iYWNrZW5kIiwic2Vzc2lvbl9zdGF0ZSI6ImU3OTc4ZDM4LTFiMzYtNDcyZS04ZDk0LTdjMGRhMGM2ODdjNiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo0MjIyIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWNvbmRpdGlvbmFsLXJlc3BvbnNlIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsInVzZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiJlNzk3OGQzOC0xYjM2LTQ3MmUtOGQ5NC03YzBkYTBjNjg3YzYiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlcjEiLCJnaXZlbl9uYW1lIjoiIiwiZmFtaWx5X25hbWUiOiIifQ.WQfq7-Fz5l4bTqy0yntz-ppoFtDLut0JK2Dq5C57eIoA0bqHWiuvzFUOuwRhtTG6Xio8CIwqsXchvzHP8A3iRCwpuA5YGygpSuod1X3zWVMw1qvQFCgEeAojdTzlB37ycL2JgzwyKGKOKKmqJq1Lla272ylVJOLbHhhCES_y5eUxdyHFS4KAbTlUTcvun-Sju-VynLF0LCmRWGfDyeL772sbODWmC0DPLQna7QzwOZaz8Hh02oIBtk0_RidoA7W33CXawKYpZ3BZ-E2P_6H7ni0UaF-HA2s8Y2XU4WjXxNtzuVdcrV2GqSSUw3-FaJe1A-e5pCzeGup92imVBK5Y4Q\",\"expires_in\":300,\"refresh_expires_in\":1800,\"refresh_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI1YzI1ZmQ4OC0zZTg0LTRkNGQtOGNkOC1mYmZmMWUwYmM5OTIifQ.eyJleHAiOjE2OTY0NDM2OTAsImlhdCI6MTY5NjQ0MTg5MCwianRpIjoiMTU0N2U1YzItNWRkNS00ODM0LWFhOGMtYmFjMWVlOTA2MWQyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NDg0L3JlYWxtcy9jb25kaXRpb25hbC1yZXNwb25zZSIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODQ4NC9yZWFsbXMvY29uZGl0aW9uYWwtcmVzcG9uc2UiLCJzdWIiOiIxYTEwYjJkMi1jNDc3LTQxMTAtOGE3ZC1jYjcxY2NjMmQ1YmMiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoiY29uZGl0aW9uYWwtcmVzcG9uc2UtYmFja2VuZCIsInNlc3Npb25fc3RhdGUiOiJlNzk3OGQzOC0xYjM2LTQ3MmUtOGQ5NC03YzBkYTBjNjg3YzYiLCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiJlNzk3OGQzOC0xYjM2LTQ3MmUtOGQ5NC03YzBkYTBjNjg3YzYifQ.zIughEee3dv_gsjPdnqW-V8iJ8xTi6fAY7Rnm5TpC2A";

    @Test
    void convertTest() {
        KeycloakSecurityConverter converter = new KeycloakSecurityConverter();
        Mono<JwtAuthenticationToken> token = converter.convert(this.createJwt());
        Assertions.assertThat(token).isNotNull();
        StepVerifier
                .create(token)
                .consumeNextWith(t -> {
                    Assertions.assertThat(t.getAuthorities())
                            .hasSize(4)
                            .contains(new SimpleGrantedAuthority("user"));
                })
                .verifyComplete();
    }

    private Jwt createJwt() {
        return Jwt.withTokenValue(TOKEN)
                .header("alg", "RS256")
                .header("typ", "JWT")
                .header("kid", "TdKLHgWH38avE8jv6Bq-M3WgalEAvw8SKkVD3noiAaQ")
                .claim("type", "Bearer")
                .claim("sub", "1a10b2d2-c477-4110-8a7d-cb71ccc2d5bc")
                .claim("realm_access", Map.of("roles", List.of("default-roles-conditional-response", "offline_access", "uma_authorization", "user")))
                .build();
    }

}
