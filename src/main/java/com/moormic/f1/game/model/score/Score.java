package com.moormic.f1.game.model.score;

import lombok.Getter;

@Getter
public abstract class Score<T> {
    private final T prediction;
    private final T result;
    private final String name;
    private final int score;

    protected Score(T prediction, T result) {
        this.prediction = prediction;
        this.result = result;
        this.name = name();
        this.score = calculateScore(prediction, result);
    }

    protected abstract String name();

    protected abstract int calculateScore(T prediction, T result);
}
