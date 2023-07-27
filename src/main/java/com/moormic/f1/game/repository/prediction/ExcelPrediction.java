package com.moormic.f1.game.repository.prediction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ExcelPrediction {
    @JsonProperty("Round")
    Integer round;
    @JsonProperty("Race")
    String race;
    @JsonProperty("Pole")
    String pole;
    @JsonProperty("P1")
    String p1Driver;
    @JsonProperty("P2")
    String p2Driver;
    @JsonProperty("P3")
    String p3Driver;
    @JsonProperty("Fastest Lap")
    String fastestLapDriver;
    @JsonProperty("# DNFs")
    Integer numDnfDrivers;
    @JsonProperty("DNF 1")
    String dnf1Driver;
    @JsonProperty("DNF 2")
    String dnf2Driver;
    @JsonProperty("DNF 3")
    String dnf3Driver;
}
