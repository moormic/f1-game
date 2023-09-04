package com.moormic.f1.game.repository.race.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RaceResult {
    private String position;
    private String positionText;
    private String laps;
    @JsonProperty("Driver")
    private Driver driver;
    private String grid;
    @JsonProperty("FastestLap")
    private FastestLap fastestLap;
}
