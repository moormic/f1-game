package com.moormic.f1.game.util;

import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ExcelWorkbookUtil {

    private static Row headerRow(Iterator<Row> rows) {
        return Optional.ofNullable(rows)
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .orElseThrow(() -> new RuntimeException("Unable to header row."));
    }

    private static Map<Integer, String> columnMapping(Row topRow) {
        var cells = topRow.cellIterator();
        var map = new HashMap<Integer, String>();

        while (cells.hasNext()) {
            var cell = cells.next();
            map.put(cell.getColumnIndex(), cell.getStringCellValue());
        }

        return map;
    }

    // TODO: Encapsulate row finding logic in a sheet POJO or another util class
    public static Map<String, Object> readRowForRound(Iterator<Row> rows, Integer round) {
        var headerRow = headerRow(rows);
        var columnMapping = columnMapping(headerRow);

        while (rows.hasNext()) {
            var row = rows.next();
            var cell = row.getCell(findColumnIndex(columnMapping, "Round"));
            var roundNum = (int) cell.getNumericCellValue();

            if (round.equals(roundNum)) {
                return mapRow(row, columnMapping);
            }
        }

        throw new RuntimeException("No row found for round " + round);
    }

    public static void writeFor(Iterator<Row> rows, Integer round, String columnName, Object writeValue) {
        var headerRow = headerRow(rows);
        var columnMapping = columnMapping(headerRow);

        while (rows.hasNext()) {
            var row = rows.next();
            var roundCell = row.getCell(findColumnIndex(columnMapping, "Round"));
            var roundNum = (int) roundCell.getNumericCellValue();

            if (round.equals(roundNum)) {
                // find the player's name in the column mapping
                var cellToWrite = row.getCell(findColumnIndex(columnMapping, columnName));
                writeCellValue(cellToWrite, writeValue);
                return;
            }
        }

        throw new RuntimeException("Unable to write for round " + round);
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

    private static void writeCellValue(Cell cell, Object value) {
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }
    }

    private static Integer findColumnIndex(Map<Integer, String> columnMapping, String columnName) {
        return columnMapping.entrySet().stream()
                .filter(e -> columnName.equals(e.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("'%s' column not found found in header row.", columnName)));
    }

    private static Map<String, Object> mapRow(Row row, Map<Integer, String> columnMapping) {
        var cells = row.cellIterator();
        var map = new HashMap<String, Object>();

        while (cells.hasNext()) {
            var cell = cells.next();
            var cellColumnIndex = cell.getColumnIndex();
            var cellValue = Optional.ofNullable(ExcelWorkbookUtil.cellValue(cell));
            var cellColumnName = Optional.ofNullable(columnMapping.get(cellColumnIndex));

            if (cellValue.isPresent() && cellColumnName.isPresent()) {
                map.put(cellColumnName.get(), cellValue.get());
            }
        }

        return map;
    }

}
