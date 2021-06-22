package com.example.featureswitcher.controller;

import com.example.featureswitcher.FeatureswitcherApplication;
import com.example.featureswitcher.api.dto.GetFeatureResponseDto;
import com.example.featureswitcher.api.dto.SetFeatureRequestDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(classes = FeatureswitcherApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeatureSwitcherControllerTest {
    private static final String BASE_PATH = "/api/v1/feature";
    private static final String STATUS_NOT_FOUND = "Not Found";

    @Autowired
    private WebTestClient webClient;

    @Test
    void isFeatureAllowed() {
        // expect
        webClient.get()
                .uri(BASE_PATH + "?email=e&featureName=f1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectStatus().reasonEquals(STATUS_NOT_FOUND);
    }

    @Test
    void setFeature() {
        // given
        SetFeatureRequestDto setTrueRequestDto = SetFeatureRequestDto.builder()
                .email("e")
                .featureName("f2")
                .enable(true)
                .build();

        // expect
        webClient.post()
                .uri(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(setTrueRequestDto), SetFeatureRequestDto.class)
                .exchange()
                .expectStatus().isOk();

        webClient.post()
                .uri(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(setTrueRequestDto), SetFeatureRequestDto.class)
                .exchange()
                .expectStatus().isNotModified();

        webClient.post()
                .uri(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(SetFeatureRequestDto.builder()
                        .email("e")
                        .featureName("f2")
                        .enable(false)
                        .build()), SetFeatureRequestDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void setAndGetFeature() {
        // expect
        webClient.get()
                .uri(BASE_PATH + "?email=e&featureName=f3")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectStatus().reasonEquals(STATUS_NOT_FOUND);

        webClient.post()
                .uri(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(SetFeatureRequestDto.builder()
                        .email("e")
                        .featureName("f3")
                        .enable(true)
                        .build()), SetFeatureRequestDto.class)
                .exchange()
                .expectStatus().isOk();

        webClient.get()
                .uri(BASE_PATH + "?email=e&featureName=f3")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(GetFeatureResponseDto.class)
                .isEqualTo(GetFeatureResponseDto.builder()
                        .canAccess(true)
                        .build());

        webClient.post()
                .uri(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(SetFeatureRequestDto.builder()
                        .email("e")
                        .featureName("f3")
                        .enable(false)
                        .build()), SetFeatureRequestDto.class)
                .exchange()
                .expectStatus().isOk();

        webClient.get()
                .uri(BASE_PATH + "?email=e&featureName=f3")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(GetFeatureResponseDto.class)
                .isEqualTo(GetFeatureResponseDto.builder()
                        .canAccess(false)
                        .build());
    }
}