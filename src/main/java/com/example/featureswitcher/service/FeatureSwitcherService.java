package com.example.featureswitcher.service;

import reactor.core.publisher.Mono;

public interface FeatureSwitcherService {
    Mono<Boolean> isFeatureAllowed(String email, String feature);
    Mono<Boolean> setFeature(String email, String featureName, boolean enable);
}
