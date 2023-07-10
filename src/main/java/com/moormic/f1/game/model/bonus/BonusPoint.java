package com.moormic.f1.game.model.bonus;

import lombok.Getter;

@Getter
public abstract class BonusPoint<T> {
    private final String name;
    private final int points;

    public BonusPoint(T bonusPointCandidate) {
        this.name = name();
        this.points = calculatePoints(bonusPointCandidate);
    }

    protected abstract String name();

    protected abstract int calculatePoints(T condition);

}
