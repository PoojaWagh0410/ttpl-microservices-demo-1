package com.ttpl.client;

import com.ttpl.common.ApiResponse;
import com.ttpl.dto.response.StudentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceWebClient {

    private final WebClient webClient;

//    public Mono<ResponseEntity<ApiResponse<List<StudentResponseDto>>>> getStudentsByClgCode(String code) {
//        return webClient.get()
//                .uri("/student/clgCode/{code}", code)
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
//                .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<StudentResponseDto>>>() {})
//                .map(ResponseEntity::ok);
//    }


    public Mono<ResponseEntity<ApiResponse<List<StudentResponseDto>>>> getStudentsByClgCode(String code) {
        return webClient.get()
                .uri("/student/clgCode/{code}", code)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(new ParameterizedTypeReference<ApiResponse<List<StudentResponseDto>>>() {})
                                .map(ResponseEntity::ok);
                    } else if (response.statusCode().is4xxClientError()) {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse<>("Error",HttpStatus.NOT_FOUND.value(), "Students not found for code " + code, null)));
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                });
    }

    public Mono<ResponseEntity<Void>> deleteByClgCode(String code) {
        return webClient.delete()
                .uri("/student/clgCode/{code}", code)
                .retrieve()
                .toBodilessEntity();
    }

}

