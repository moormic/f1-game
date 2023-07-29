package com.moormic.f1.game.repository.game;

import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelGameRepository {

    Workbook get();

    void save(Workbook workbook);

    void close(Workbook workbook);

}
