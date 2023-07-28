package com.moormic.f1.game.repository.prediction;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moormic.f1.game.model.prediction.PlayerPrediction;
import com.moormic.f1.game.repository.game.ExcelGameRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExcelPlayerPredictionRepository implements PlayerPredictionRepository {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final List<String> EXCLUDED_SHEETS = List.of("Scores", "Rules", "Metadata");
    private final ExcelGameRepository gameRepository;

    public List<PlayerPrediction> get(Integer round) {
        var workbook = gameRepository.get();
        var sheets = workbook.sheetIterator();
        var predictions = new ArrayList<PlayerPrediction>();

        while (sheets.hasNext()) {
            var sheet = sheets.next();
            var sheetName = sheet.getSheetName();

            if (!StringUtils.isBlank(sheetName) && !EXCLUDED_SHEETS.contains(sheetName)) {
                var rows = sheet.rowIterator();
                var firstRow = Optional.ofNullable(rows)
                        .filter(Iterator::hasNext)
                        .map(Iterator::next)
                        .orElseThrow(() -> new RuntimeException("Unable to find prediction header row for sheet " + sheetName));

                var columnMapping = columnMapping(firstRow);
                var row = getRowForRound(rows, round);
                var prediction = rowToExcelPrediction(row, columnMapping);
                predictions.add(playerPrediction(sheetName, prediction));
            }
        }

        return predictions;
    }


    private Map<Integer, String> columnMapping(Row topRow) {
        var cells = topRow.cellIterator();
        var map = new HashMap<Integer, String>();

        while (cells.hasNext()) {
            var cell = cells.next();
            map.put(cell.getColumnIndex(), cell.getStringCellValue());
        }

        return map;
    }

    private Row getRowForRound(Iterator<Row> rows, Integer round) {
        while (rows.hasNext()) {
            var row = rows.next();
            var cell = row.getCell(1); //round number is the first column in the row
            var roundNum = (int) cell.getNumericCellValue();

            if (round.equals(roundNum)) {
                return row;
            }
        }

        throw new RuntimeException("No prediction row found for round " + round);
    }

    private ExcelPlayerPrediction rowToExcelPrediction(Row row, Map<Integer, String> columnMapping) {
        var cells = row.cellIterator();
        var predictionMap = new HashMap<String, Object>();

        while (cells.hasNext()) {
             var cell = cells.next();
             var cellValue = cellValue(cell);
             var cellColumnIndex = cell.getColumnIndex();
             var cellColumnName = Optional.ofNullable(columnMapping.get(cellColumnIndex));

             cellColumnName.ifPresent(n -> predictionMap.put(n, cellValue));
        }

        return OBJECT_MAPPER.convertValue(predictionMap, new TypeReference<>(){});
    }

    private Object cellValue(Cell cell) {
        var cellType = cell.getCellType();

        if (CellType.STRING.equals(cellType)) {
            return cell.getStringCellValue();
        } else if (CellType.NUMERIC.equals(cellType)) {
            return cell.getNumericCellValue();
        }

        throw new RuntimeException("Unsupported cell type at " + cell.getAddress().toString());
    }

    private PlayerPrediction playerPrediction(String playerName, ExcelPlayerPrediction excelPlayerPrediction) {
        return new PlayerPrediction(
                playerName,
                excelPlayerPrediction.getRound(),
                excelPlayerPrediction.getPole(),
                List.of(excelPlayerPrediction.getP1Driver(), excelPlayerPrediction.getP2Driver(), excelPlayerPrediction.getP3Driver()),
                excelPlayerPrediction.getFastestLapDriver(),
                excelPlayerPrediction.getNumDnfDrivers(),
                List.of(excelPlayerPrediction.getDnf1Driver(), excelPlayerPrediction.getDnf2Driver(), excelPlayerPrediction.getDnf3Driver())
        );
    }

}
