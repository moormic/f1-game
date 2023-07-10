package com.moormic.f1.game.service;

import com.moormic.f1.game.model.Podium;
import com.moormic.f1.game.model.Prediction;
import com.moormic.f1.game.model.score.*;
import com.moormic.f1.game.model.bonus.CleanSweep;
import com.moormic.f1.game.model.bonus.PodiumCombo;
import com.moormic.f1.game.repository.RaceResultRepository;
import com.moormic.f1.game.repository.model.RaceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        var podiumScore = new PodiumScore(prediction.getPodium(), podium);
        var fastestLapDriverScore = new FastestLapDriverScore(prediction.getFastestLapDriver(), fastestLapDriver);
        var dnfCountScore = new DnfCountScore(prediction.getDnfCount(), dnfDrivers.size());
        var dnfDriverScore = new DnfDriverScore(prediction.getDnfDrivers(), dnfDrivers);

        var podiumCombo = new PodiumCombo(podiumScore);
        var cleanSweepBonus = new CleanSweep(List.of(poleDriverScore, podiumScore, fastestLapDriverScore, dnfCountScore));

        return new TotalScore(
                List.of(poleDriverScore, podiumScore, fastestLapDriverScore, dnfCountScore, dnfDriverScore),
                List.of(podiumCombo, cleanSweepBonus)
        );
    }

    private String poleDriver(List<RaceResult> raceResults) {
        return "";
    }

    private Podium podium(List<RaceResult> raceResults) {
        return new Podium("", "", "");
    }

    private String fastestLapDriver(List<RaceResult> raceResults) {
        return "";
    }

    private List<String> dnfDrivers(List<RaceResult> raceResults) {
        return List.of();
    }

}
