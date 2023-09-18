package com.moormic.f1.game.repository.race.model;

import com.moormic.f1.game.model.exception.RaceResultsException;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public record RaceResults(List<RaceResult> raceResults) {

    public String poleDriver() {
        return raceResults.stream()
                .filter(r -> "1".equals(r.getGrid()))
                .findFirst()
                .map(RaceResult::getDriver)
                .map(Driver::getCode)
                .orElseThrow(() -> new RaceResultsException("Unable to determine pole position driver."));
    }

    public List<String> podium() {
        var drivers = raceResults.stream()
                .filter(r -> List.of("1", "2", "3").contains(r.getPosition()))
                .map(RaceResult::getDriver)
                .map(Driver::getCode)
                .distinct()
                .collect(toList());

        return Optional.of(drivers)
                .filter(d -> d.size() == 3)
                .orElseThrow(() -> new RaceResultsException("Unable to determine podium drivers."));
    }

    public String fastestLapDriver() {
        return raceResults.stream()
                .filter(r -> "1".equals(Optional.ofNullable(r.getFastestLap()).map(FastestLap::getRank).orElse(null)))
                .map(RaceResult::getDriver)
                .map(Driver::getCode)
                .findFirst()
                .orElseThrow(() -> new RaceResultsException("Unable to find driver with fastest lap."));
    }

    public List<String> dnfDrivers() {
        // positionText 'W' indicates a DNS (withdrew). TODO: Look for other DNS indicators
        return raceResults.stream()
                .filter(r -> !"Finished".equals(r.getStatus()))
                .filter(r -> !"W".equals(r.getPositionText()))
                .map(RaceResult::getDriver)
                .map(Driver::getCode)
                .collect(toList());
    }

}
