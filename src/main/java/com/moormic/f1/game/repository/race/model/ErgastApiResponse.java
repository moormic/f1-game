package com.moormic.f1.game.repository.race.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErgastApiResponse {
    @JsonProperty("MRData")
    private MRData mrData;
}
