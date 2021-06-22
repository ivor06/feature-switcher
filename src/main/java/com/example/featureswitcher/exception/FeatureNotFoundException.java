package com.example.featureswitcher.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class FeatureNotFoundException extends RuntimeException {
    public FeatureNotFoundException(String email, String featureName) {
        super(String.format("Feature %s not found for user with email %s", featureName, email));
    }
}
