package com.ttpl.client;

import com.ttpl.common.ApiResponse;
import com.ttpl.dto.response.CollegeResponseDto;
import com.ttpl.dto.response.StudentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollegeServiceWebClient {

    private final WebClient webClient;

    public Mono<ResponseEntity<ApiResponse<CollegeResponseDto>>> getByCollegeCode(String code) {
        return webClient.get()
                .uri("/college/clgCode/{code}", code)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<CollegeResponseDto>>() {})
                .map(ResponseEntity::ok);
    }
}



