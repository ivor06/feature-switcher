package com.example.featureswitcher.api.dto;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import javax.validation.constraints.NotBlank;
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
public class GetFeatureRequestDto {
    /**
     * User' email.
     */
    @NotBlank String email;

    /**
     * Feature name.
     */
    @NotBlank String featureName;
}
