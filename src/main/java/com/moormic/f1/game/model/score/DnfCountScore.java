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
        return result.equals(prediction) ? 10 : 0;
    }

}
