package com.moormic.f1.game.repository.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErgastApiResponse {
    @JsonProperty("MRData")
    private MRData mrData;
}
