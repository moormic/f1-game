package com.moormic.f1.game.repository.race.model;

import lombok.Data;

@Data
public class Driver {
    private String driverId;
    private String permanentNumber;
    private String code;
    private String givenName;
    private String familyName;
}
