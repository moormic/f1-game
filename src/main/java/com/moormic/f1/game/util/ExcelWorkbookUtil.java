package com.moormic.f1.game.util;

import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ExcelWorkbookUtil {

    public static Map<Integer, String> columnMapping(Row topRow) {
        var cells = topRow.cellIterator();
        var map = new HashMap<Integer, String>();

        while (cells.hasNext()) {
            var cell = cells.next();
            map.put(cell.getColumnIndex(), cell.getStringCellValue());
        }

        return map;
    }

    public static Row getRowForRound(Map<Integer, String> columnMapping, Iterator<Row> rows, Integer round) {
        var roundColumnIndex = columnMapping.entrySet().stream()
                .filter(e -> "Round".equals(e.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No 'Round' column found in row"));

        while (rows.hasNext()) {
            var row = rows.next();
            var cell = row.getCell(roundColumnIndex);
            var roundNum = (int) cell.getNumericCellValue();

            if (round.equals(roundNum)) {
                return row;
            }
        }

        throw new RuntimeException("No prediction row found for round " + round);
    }

    public static Object cellValue(Cell cell) {
        var cellType = cell.getCellType();

        if (CellType.STRING.equals(cellType)) {
            return cell.getStringCellValue();
        } else if (CellType.NUMERIC.equals(cellType)) {
            return cell.getNumericCellValue();
        }

        return null;
    }

}
