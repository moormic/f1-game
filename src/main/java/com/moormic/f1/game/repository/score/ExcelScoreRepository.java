package com.moormic.f1.game.repository.score;

import com.moormic.f1.game.model.score.PlayerScore;
import com.moormic.f1.game.repository.game.ExcelGameRepository;
import com.moormic.f1.game.util.ExcelWorkbookUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExcelScoreRepository implements PlayerScoreRepository {
    private final ExcelGameRepository excelGameRepository;

    public void put(PlayerScore score) {
        var workbook = excelGameRepository.get();
        var scoreSheet = workbook.getSheet("Scores");
        var rows = scoreSheet.rowIterator();
        ExcelWorkbookUtil.writeFor(rows, score.getRound(), score.getPlayerName(), score.getScore());
        excelGameRepository.save(workbook);
        excelGameRepository.close(workbook);
    }

}

