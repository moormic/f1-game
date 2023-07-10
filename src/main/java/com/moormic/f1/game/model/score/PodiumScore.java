package com.moormic.f1.game.model.score;

import com.moormic.f1.game.model.Podium;

import java.util.List;

public class PodiumScore extends Score<Podium> {

    public PodiumScore(Podium prediction, Podium result) {
        super(prediction, result);
    }

    @Override
    protected String name() {
        return "podium";
    }

    @Override
    protected int calculateScore(Podium prediction, Podium result) {
        // loop through the predicted podium and check if:
        // 1. correct driver + correct position (10 points), or
        // 2. correct driver + incorrect position (5 points)


        var podiumDrivers = List.of(result.getP1Driver(), result.getP2Driver(), result.getP3Driver());



        return 0;
    }

}
