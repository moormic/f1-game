package com.moormic.f1.game.repository.prediction;

import com.moormic.f1.game.model.prediction.PlayerPrediction;
import com.moormic.f1.game.repository.game.ExcelGameRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExcelPredictionRepository implements PlayerPredictionRepository {
    private static final List<String> EXCLUDED_SHEETS = List.of("Scores", "Rules", "Metadata");
    private final ExcelGameRepository gameRepository;

    public List<PlayerPrediction> get(Integer raceNumber) {
        var workbook = gameRepository.get();
        var sheets = workbook.sheetIterator();

        while (sheets.hasNext()) {
            var sheet = sheets.next();
            var sheetName = sheet.getSheetName();

            if (!StringUtils.isBlank(sheetName) && !EXCLUDED_SHEETS.contains(sheetName)) {
                var rows = sheet.rowIterator();

                //TODO: Grab the column names along with their respective indices and store them in memory somewhere
                // then navigate down to the correct row per the race number and build a map of values
                // finally convert this object to a PlayerPrediction

                while (rows.hasNext()) {
                    var row = rows.next();
                    var cells = row.cellIterator();

                    while (cells.hasNext()) {
                        var cell = cells.next();
                        var columnNum = cell.getColumnIndex();

                        if (columnNum == 0) {

                        }
                        cell.getStringCellValue();
                    }
                }


                //TODO: iterate to the correct row based on race number
                //TODO: parse the columns and create prediction POJO

                //TODO: get an excel prediction and convert it to a player prediction

                var row = sheet.getRow(0);



            }
        }


        return List.of();
    }



}
