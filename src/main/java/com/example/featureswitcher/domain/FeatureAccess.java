package com.example.featureswitcher.domain;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

@Entity
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class FeatureAccess {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;


    String email;

    String featureName;

    @Getter
    @Setter
    boolean enable;
}
