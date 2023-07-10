package com.moormic.f1.game.model.score;

public class FastestLapDriverScore extends Score<String> {

    public FastestLapDriverScore(String prediction, String result) {
        super(prediction, result);
    }

    @Override
    protected String name() {
        return "fastestLapDriver";
    }

    @Override
    protected int calculateScore(String prediction, String result) {
        return prediction.equals(result) ? 10 : 0;
    }

}
