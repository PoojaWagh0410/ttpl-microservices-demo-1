package com.ttpl.client;

import com.ttpl.common.ApiResponse;
import com.ttpl.dto.response.CollegeResponseDto;
import com.ttpl.dto.response.StudentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceWebClient {

    private final WebClient webClient;

    public Mono<ResponseEntity<ApiResponse<List<StudentResponseDto>>>> getStudentsByClgCode(String code) {

        return webClient.get()
                .uri("/student/clgCode/{code}", code)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<StudentResponseDto>>>() {})
                .map(ResponseEntity::ok);
    }

    public Mono<ResponseEntity<Void>> deleteByClgCode(String code) {
        return webClient.delete()
                .uri("/student/clgCode/{code}", code)
                .retrieve()
                .toBodilessEntity();
    }

}

