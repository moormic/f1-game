package com.moormic.f1.game.repository.race.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RaceTable {
    private String season;
    private String round;
    @JsonProperty("Races")
    List<Race> races;
}
