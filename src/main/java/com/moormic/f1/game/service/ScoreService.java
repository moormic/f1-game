package com.moormic.f1.game.service;

import com.moormic.f1.game.model.Prediction;
import com.moormic.f1.game.model.bonus.CleanSweep;
import com.moormic.f1.game.model.bonus.PodiumCombo;
import com.moormic.f1.game.model.exception.RaceResultException;
import com.moormic.f1.game.model.score.*;
import com.moormic.f1.game.repository.RaceResultRepository;
import com.moormic.f1.game.repository.model.api.Driver;
import com.moormic.f1.game.repository.model.api.FastestLap;
import com.moormic.f1.game.repository.model.api.RaceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ScoreService {
    private final RaceResultRepository raceResultRepository;

    public TotalScore score(Prediction prediction) {
        var raceResults = raceResultRepository.get(prediction.getSeason(), prediction.getRaceNumber());

        var poleDriver = poleDriver(raceResults);
        var podium = podium(raceResults);
        var fastestLapDriver = fastestLapDriver(raceResults);
        var dnfDrivers = dnfDrivers(raceResults);

        var poleDriverScore = new PoleDriverScore(prediction.getPoleDriver(), poleDriver);
        var podiumScore = new PodiumScore(prediction.getPodiumDrivers(), podium);
        var fastestLapDriverScore = new FastestLapDriverScore(prediction.getFastestLapDriver(), fastestLapDriver);
        var dnfCountScore = new DnfCountScore(prediction.getDnfCount(), Optional.ofNullable(dnfDrivers).map(List::size).orElse(null));
        var dnfDriverScore = new DnfDriverScore(prediction.getDnfDrivers(), dnfDrivers);

        var podiumCombo = new PodiumCombo(podiumScore);
        var cleanSweepBonus = new CleanSweep(List.of(poleDriverScore, podiumScore, fastestLapDriverScore, dnfCountScore));

        return new TotalScore(
                List.of(poleDriverScore, podiumScore, fastestLapDriverScore, dnfCountScore, dnfDriverScore),
                List.of(podiumCombo, cleanSweepBonus)
        );
    }

    private String poleDriver(List<RaceResult> raceResults) {
        return raceResults.stream()
                .filter(r -> "1".equals(r.getGrid()))
                .findFirst()
                .map(RaceResult::getDriver)
                .map(Driver::getCode)
                .orElseThrow(() -> new RaceResultException("Unable to determine pole position driver."));
    }

    private List<String> podium(List<RaceResult> raceResults) {
        var drivers = raceResults.stream()
                .filter(r -> List.of("1", "2", "3").contains(r.getPosition()))
                .map(RaceResult::getDriver)
                .map(Driver::getCode)
                .distinct()
                .collect(toList());

        return Optional.of(drivers)
                .filter(d -> d.size() == 3)
                .orElseThrow(() -> new RaceResultException("Unable to determine podium drivers."));
    }

    private String fastestLapDriver(List<RaceResult> raceResults) {
        return raceResults.stream()
                .filter(r -> "1".equals(Optional.ofNullable(r.getFastestLap()).map(FastestLap::getRank).orElse(null)))
                .map(RaceResult::getDriver)
                .map(Driver::getCode)
                .findFirst()
                .orElseThrow(() -> new RaceResultException("Unable to find driver with fastest lap."));

    }

    private List<String> dnfDrivers(List<RaceResult> raceResults) {
        return raceResults.stream()
                .filter(r -> !"Finished".equals(r.getStatus()))
                .map(RaceResult::getDriver)
                .map(Driver::getCode)
                .collect(toList());
    }

}
