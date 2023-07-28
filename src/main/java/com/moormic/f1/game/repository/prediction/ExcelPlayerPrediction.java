package com.moormic.f1.game.repository.prediction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class ExcelPlayerPrediction {
    @JsonProperty("Round")
    private Integer round;
    @JsonProperty("Race")
    private String race;
    @JsonProperty("Pole")
    private String pole;
    @JsonProperty("P1")
    private String p1Driver;
    @JsonProperty("P2")
    private String p2Driver;
    @JsonProperty("P3")
    private String p3Driver;
    @JsonProperty("Fastest Lap")
    private String fastestLapDriver;
    @JsonProperty("# DNFs")
    private Integer numDnfDrivers;
    @JsonProperty("DNF 1")
    private String dnf1Driver;
    @JsonProperty("DNF 2")
    private String dnf2Driver;
    @JsonProperty("DNF 3")
    private String dnf3Driver;
}
