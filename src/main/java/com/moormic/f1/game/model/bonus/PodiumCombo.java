package com.moormic.f1.game.model.bonus;

import com.moormic.f1.game.model.score.PodiumScore;

public class PodiumCombo extends BonusPoint<PodiumScore> {

    public PodiumCombo(PodiumScore podiumScore) {
        super(podiumScore);
    }

    @Override
    protected String name() {
        return "podiumCombo";
    }

    @Override
    protected int calculatePoints(PodiumScore podiumScore) {
        return podiumScore.getScore() == 30 ? 15 : 0;
    }

}
