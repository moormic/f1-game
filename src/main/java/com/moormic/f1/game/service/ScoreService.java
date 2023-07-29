package com.moormic.f1.game.service;

import com.moormic.f1.game.model.bonus.CleanSweep;
import com.moormic.f1.game.model.bonus.PodiumCombo;
import com.moormic.f1.game.model.prediction.PlayerPrediction;
import com.moormic.f1.game.model.score.*;
import com.moormic.f1.game.repository.race.model.RaceResults;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ScoreService {

    public PlayerScore score(RaceResults raceResults, PlayerPrediction playerPrediction) {
        var poleDriverScore = new PoleDriverScore(playerPrediction.getPoleDriver(), raceResults.poleDriver());
        var podiumScore = new PodiumScore(playerPrediction.getPodiumDrivers(), raceResults.podium());
        var fastestLapDriverScore = new FastestLapDriverScore(playerPrediction.getFastestLapDriver(), raceResults.fastestLapDriver());
        var dnfCountScore = new DnfCountScore(playerPrediction.getDnfCount(), Optional.ofNullable(raceResults.dnfDrivers()).map(List::size).orElse(null));
        var dnfDriverScore = new DnfDriverScore(playerPrediction.getDnfDrivers(), raceResults.dnfDrivers());

        var podiumCombo = new PodiumCombo(podiumScore);
        var cleanSweepBonus = new CleanSweep(List.of(poleDriverScore, podiumScore, fastestLapDriverScore, dnfCountScore));

        return new PlayerScore(
                playerPrediction.getPlayerName(),
                playerPrediction.getRound(),
                List.of(poleDriverScore, podiumScore, fastestLapDriverScore, dnfCountScore, dnfDriverScore),
                List.of(podiumCombo, cleanSweepBonus)
        );
    }

}
