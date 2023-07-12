package com.moormic.f1.game.repository.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RaceResult {
    private String position;
    @JsonProperty("Driver")
    private Driver driver;
    private String grid;
    private String status;
    @JsonProperty("FastestLap")
    private FastestLap fastestLap;
}
