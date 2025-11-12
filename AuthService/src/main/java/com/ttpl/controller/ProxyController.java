package com.ttpl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    private final WebClient webClient;

    public ProxyController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://api-gateway:8080").build();
    }

    @GetMapping("/**")
    public Mono<ResponseEntity<String>> proxyRequest(
            @RequestHeader("Authorization") String authHeader,
            ServerHttpRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String path = request.getPath().pathWithinApplication().value().replace("/proxy", "");

        return webClient.get()
                .uri(path)
                .header("X-User", jwt.getSubject())
                .header("X-Roles", String.join(",", jwt.getClaimAsStringList("roles")))
                .retrieve()
                .toEntity(String.class);
    }
}
