package com.moormic.f1.game.model.prediction;

import lombok.Value;

import java.util.List;

@Value
public class PlayerPrediction {
    String playerName;
    Integer round;
    String poleDriver;
    List<String> podiumDrivers;
    String fastestLapDriver;
    Integer dnfCount;
    List<String> dnfDrivers;
}
