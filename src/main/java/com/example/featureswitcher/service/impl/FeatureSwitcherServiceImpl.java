package com.example.featureswitcher.service.impl;

import com.example.featureswitcher.domain.FeatureAccess;
import com.example.featureswitcher.exception.FeatureNotFoundException;
import com.example.featureswitcher.repository.FeatureAccessRepository;
import com.example.featureswitcher.service.FeatureSwitcherService;

import java.util.concurrent.atomic.AtomicReference;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class FeatureSwitcherServiceImpl implements FeatureSwitcherService {
    private final @NonNull
    FeatureAccessRepository featureAccessRepository;

    @Override
    public Mono<Boolean> isFeatureAllowed(String email, String featureName) {
        Mono<FeatureAccess> featureAccess = featureAccessRepository.findFirstByEmailAndFeatureName(email, featureName);

        return featureAccess
                .map(FeatureAccess::isEnable)
                .switchIfEmpty(Mono.error(new FeatureNotFoundException(email, featureName)));
    }

    @Override
    public Mono<Boolean> setFeature(String email, String featureName, boolean enable) {
        AtomicReference<FeatureAccess> currentHolder = new AtomicReference<>();

        return featureAccessRepository.findFirstByEmailAndFeatureName(email, featureName)
                .filter(existed -> {
                    currentHolder.set(existed);
                    return existed.isEnable() == enable;
                })
                .flatMap(existed -> Mono.just(false))
                .switchIfEmpty(Mono.defer(() -> {
                    FeatureAccess featureAccess = currentHolder.get();

                    if (featureAccess == null)
                        featureAccess = FeatureAccess.builder()
                                .email(email)
                                .featureName(featureName)
                                .build();

                    featureAccess.setEnable(enable);

                    return featureAccessRepository.save(featureAccess)
                            .map(fa -> Boolean.TRUE);
                }))
                .cast(Boolean.class);
    }
}
