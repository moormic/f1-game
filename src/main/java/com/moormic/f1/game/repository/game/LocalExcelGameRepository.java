package com.moormic.f1.game.repository.game;

import com.moormic.f1.game.config.GameConfig;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LocalExcelGameRepository implements ExcelGameRepository {
    private final GameConfig config;

    public Workbook get() {
        try {
            FileInputStream file = new FileInputStream(new File(config.getUrl()));
            return new XSSFWorkbook(file);
        } catch (IOException e) {
            System.out.println("Unable to load excel game file. Error: " + e.getMessage());
            System.exit(-1);
            return null;
        }
    }

}
