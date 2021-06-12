package com.bilal.learning_platform.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckValidityResponse {
    private Boolean isBought;
    private Boolean isWished;
}
