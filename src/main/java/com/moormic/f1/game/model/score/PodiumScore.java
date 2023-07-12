package com.moormic.f1.game.model.score;

import java.util.List;
import java.util.stream.IntStream;

public class PodiumScore extends Score<List<String>> {

    public PodiumScore(List<String> prediction, List<String> result) {
        super(prediction, result);
    }

    @Override
    protected String name() {
        return "podium";
    }

    @Override
    protected int calculateScore(List<String> prediction, List<String> result) {
        // correct driver + correct position (10 points)
        // correct driver + incorrect position (5 points)
        return IntStream.range(0, prediction.size())
                .map(i -> {
                    var predictedDriver = prediction.get(i);
                    var resultDriver = result.get(i);
                    return predictedDriver.equals(resultDriver) ? 10 : (result.contains(predictedDriver) ? 5 : 0);
                }).sum();
    }
}
