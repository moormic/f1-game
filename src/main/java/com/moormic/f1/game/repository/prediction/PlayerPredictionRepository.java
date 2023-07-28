package com.moormic.f1.game.repository.prediction;

import com.moormic.f1.game.model.prediction.PlayerPrediction;

import java.util.List;

public interface PlayerPredictionRepository {

    List<PlayerPrediction> get(Integer round);

}
