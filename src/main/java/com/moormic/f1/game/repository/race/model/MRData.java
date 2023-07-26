package com.moormic.f1.game.repository.race.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MRData {
    private String limit;
    private String offset;
    private String total;
    private String url;
    private String series;
    @JsonProperty("RaceTable")
    private RaceTable raceTable;
}
