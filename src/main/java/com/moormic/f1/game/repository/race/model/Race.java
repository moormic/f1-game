package com.moormic.f1.game.repository.race.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Race {
    private String season;
    private String round;
    private String url;
    private String raceName;
    private String date;
    @JsonProperty("time")
    private String timeZ;
    @JsonProperty("Results")
    private List<RaceResult> raceResults;
}
