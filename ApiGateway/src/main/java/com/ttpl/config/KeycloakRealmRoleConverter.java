package com.ttpl.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Collection<GrantedAuthority> authorities =
                jwt.getClaimAsMap("realm_access") == null ?
                        Collections.emptyList() :
                        ((List<String>) ((Map) jwt.getClaim("realm_access"))
                                .get("roles"))
                                .stream()
                                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                                .collect(Collectors.toList());

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt1 -> authorities);

        return jwtAuthenticationConverter.convert(jwt).getAuthorities();
    }



}
