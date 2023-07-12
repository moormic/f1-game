package com.moormic.f1.game.model.bonus;

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
        return basicScores.stream().mapToInt(Score::getScore).allMatch(s -> s > 0) ? 30 : 0;
    }
}
