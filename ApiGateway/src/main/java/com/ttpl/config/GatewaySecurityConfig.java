//package com.ttpl.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//public class GatewaySecurityConfig {
//
//        @Bean
//        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//
//            // Configure how roles are extracted from Keycloak token
//            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
//            authoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
//            authoritiesConverter.setAuthorityPrefix("ROLE_");
//
//            JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
//            jwtAuthConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
//
//            var reactiveJwtConverter = new ReactiveJwtAuthenticationConverterAdapter(jwtAuthConverter);
//
//            http
//                    .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF for API
//                    .authorizeExchange(exchange -> exchange
//                            .pathMatchers(
//                                    "/swagger-ui.html",
//                                    "/v3/api-docs/**",
//                                    "/actuator/**",
//                                    "/public/**"
//                            ).permitAll()
//                            .anyExchange().authenticated()
//                    )
//                    .oauth2ResourceServer(oauth2 ->
//                            oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(reactiveJwtConverter))
//                    );
//
//            return http.build();
//        }
//    }
//
//
