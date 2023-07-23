package com.moormic.f1.game.model.bonus;

import com.moormic.f1.game.model.score.PodiumScore;
import com.moormic.f1.game.model.score.Score;

import java.util.List;

public class CleanSweep extends BonusPoint<List<Score>> {

    public CleanSweep(List<Score> basicScores) {
        super(basicScores);
    }

    @Override
    protected String name() {
        return "cleanSweep";
    }

    @Override
    protected int calculatePoints(List<Score> basicScores) {
        return basicScores.stream()
                .allMatch(s -> {
                    if (s instanceof PodiumScore) {
                        // for Podium score we only care if all drivers are correct, not positions
                        return ((PodiumScore) s).getResult().containsAll(((PodiumScore) s).getPrediction());
                    }
                    return s.getScore() > 0;
                }) ? 30: 0;
    }
}
