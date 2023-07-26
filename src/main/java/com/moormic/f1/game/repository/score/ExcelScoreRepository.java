package com.moormic.f1.game.repository.score;

import com.moormic.f1.game.model.score.PlayerScore;
import com.moormic.f1.game.repository.game.ExcelGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExcelScoreRepository implements PlayerScoreRepository {
    private final ExcelGameRepository excelGameRepository;

    public void put(PlayerScore score) {

    }

}

