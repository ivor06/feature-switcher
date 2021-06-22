package com.example.featureswitcher.api.dto;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PROTECTED)
@Builder(toBuilder = true)
@FieldDefaults(level = PRIVATE)
public class GetFeatureResponseDto {
    /**
     * Will be true if the user has access to the featureName; otherwise will be false.
     *  */
    boolean canAccess;
}
