package com.moormic.f1.game.service;

import com.moormic.f1.game.model.prediction.PlayerPrediction;
import com.moormic.f1.game.model.bonus.CleanSweep;
import com.moormic.f1.game.model.bonus.PodiumCombo;
import com.moormic.f1.game.model.exception.RaceResultException;
import com.moormic.f1.game.model.score.*;
import com.moormic.f1.game.repository.race.RaceResultRepository;
import com.moormic.f1.game.repository.race.model.Driver;
import com.moormic.f1.game.repository.race.model.FastestLap;
import com.moormic.f1.game.repository.race.model.RaceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ScoreService {
    private final RaceResultRepository raceResultRepository;

    public PlayerScore score(PlayerPrediction playerPrediction) {
        var raceResults = raceResultRepository.get(playerPrediction.getSeason(), playerPrediction.getRaceNumber());

        var poleDriver = poleDriver(raceResults);
        var podium = podium(raceResults);
        var fastestLapDriver = fastestLapDriver(raceResults);
        var dnfDrivers = dnfDrivers(raceResults);

        var poleDriverScore = new PoleDriverScore(playerPrediction.getPoleDriver(), poleDriver);
        var podiumScore = new PodiumScore(playerPrediction.getPodiumDrivers(), podium);
        var fastestLapDriverScore = new FastestLapDriverScore(playerPrediction.getFastestLapDriver(), fastestLapDriver);
        var dnfCountScore = new DnfCountScore(playerPrediction.getDnfCount(), Optional.ofNullable(dnfDrivers).map(List::size).orElse(null));
        var dnfDriverScore = new DnfDriverScore(playerPrediction.getDnfDrivers(), dnfDrivers);

        var podiumCombo = new PodiumCombo(podiumScore);
        var cleanSweepBonus = new CleanSweep(List.of(poleDriverScore, podiumScore, fastestLapDriverScore, dnfCountScore));

        return new PlayerScore(
                playerPrediction.getPlayerName(),
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
        var finished = Pattern.compile("^Finished$");
        var lapDown = Pattern.compile("^\\+\\d\\sLap[s]?$");

        // any driver not classified as finishing on the lead lap or a lap down is counted as DNF
        // TODO: see if there's a better way to do this
        return raceResults.stream()
                .filter(r -> !finished.matcher(r.getStatus()).find())
                .filter(r -> !lapDown.matcher(r.getStatus()).find())
                .map(RaceResult::getDriver)
                .map(Driver::getCode)
                .collect(toList());
    }

}
