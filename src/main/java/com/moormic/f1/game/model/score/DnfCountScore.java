package com.moormic.f1.game.model.score;

public class DnfCountScore extends Score<Integer> {

    public DnfCountScore(Integer prediction, Integer result) {
        super(prediction, result);
    }

    @Override
    protected String name() {
        return "dnfCount";
    }

    @Override
    protected int calculateScore(Integer prediction, Integer result) {
        return prediction.equals(result) ? 10 : 0;
    }

}
