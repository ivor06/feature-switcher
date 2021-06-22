package com.example.featureswitcher.repository;

import com.example.featureswitcher.domain.FeatureAccess;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface FeatureAccessRepository extends R2dbcRepository<FeatureAccess, Long> {
    Mono<FeatureAccess> findFirstByEmailAndFeatureName(String email, String featureName);
}
