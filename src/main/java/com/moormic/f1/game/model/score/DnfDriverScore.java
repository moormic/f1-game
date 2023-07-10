package com.moormic.f1.game.model.score;

import java.util.List;

public class DnfDriverScore extends Score<List<String>> {

    public DnfDriverScore(List<String> prediction, List<String> result) {
        super(prediction, result);
    }

    @Override
    protected String name() {
        return "dnfDrivers";
    }

    @Override
    protected int calculateScore(List<String> prediction, List<String> result) {
        return prediction.stream()
                .mapToInt(d -> result.contains(d) ? 20 : -10)
                .sum();
    }

}
