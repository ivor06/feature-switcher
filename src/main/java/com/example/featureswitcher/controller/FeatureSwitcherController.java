package com.example.featureswitcher.controller;

import static com.example.featureswitcher.api.Constants.FEATURE_SWITCHER_PATH;

import com.example.featureswitcher.api.dto.GetFeatureRequestDto;
import com.example.featureswitcher.api.dto.SetFeatureRequestDto;
import com.example.featureswitcher.api.dto.GetFeatureResponseDto;
import com.example.featureswitcher.service.FeatureSwitcherService;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = FEATURE_SWITCHER_PATH, consumes = { MediaType.ALL_VALUE })
public class FeatureSwitcherController {
    private final FeatureSwitcherService featureSwitcherService;

    /**
     * Get is feature access for user by email.
     *
     * @param getFeatureRequestDto get feature request
     * @return GetFeatureResponse dto
     */
    @GetMapping
    Mono<ResponseEntity<GetFeatureResponseDto>> isFeatureAllowed(@Valid GetFeatureRequestDto getFeatureRequestDto) {
        System.out.println(getFeatureRequestDto);
        return featureSwitcherService.isFeatureAllowed(getFeatureRequestDto.getEmail(), getFeatureRequestDto.getFeatureName())
                .map(isAllowed -> new ResponseEntity<>(
                        GetFeatureResponseDto.builder()
                            .canAccess(isAllowed)
                            .build(),
                        HttpStatus.OK));
    }

    /**
     * Set feature access for user by email.
     *
     * @param setFeatureRequestDto set feature request
     */
    @PostMapping
    Mono<ResponseEntity<?>> setFeature(@RequestBody SetFeatureRequestDto setFeatureRequestDto) {
        return featureSwitcherService.setFeature(setFeatureRequestDto.getEmail(), setFeatureRequestDto.getFeatureName(), setFeatureRequestDto.isEnable())
                .flatMap(wasSet -> {
                    log.info("\nwasSet: " + wasSet);
                    return Mono.just(new ResponseEntity<>(wasSet ? HttpStatus.OK : HttpStatus.NOT_MODIFIED));
                });
    }
}
