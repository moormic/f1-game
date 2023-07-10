package com.moormic.f1.game.model;

import lombok.Value;

import java.util.List;

@Value
public class Prediction {
    Integer season;
    Integer raceNumber;
    String poleDriver;
    Podium podium;
    String fastestLapDriver;
    Integer dnfCount;
    List<String> dnfDrivers;
}
