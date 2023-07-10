package com.moormic.f1.game.model.score;

public class PoleDriverScore extends Score<String> {

    public PoleDriverScore(String prediction, String result) {
        super(prediction, result);
    }

    @Override
    protected String name() {
        return "poleDriver";
    }

    @Override
    protected int calculateScore(String prediction, String result) {
        return prediction.equals(result) ? 10 : 0;
    }

}
