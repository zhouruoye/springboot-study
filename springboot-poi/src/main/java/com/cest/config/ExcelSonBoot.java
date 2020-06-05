package com.cest.config;

import com.excel.poi.ExcelBoot;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

public class ExcelSonBoot extends ExcelBoot {

    protected ExcelSonBoot(HttpServletResponse response, OutputStream outputStream, InputStream inputStream, String fileName, Class excelClass, Integer pageSize, Integer rowAccessWindowSize, Integer recordCountPerSheet, Boolean openAutoColumWidth) {
        super(response, outputStream, inputStream, fileName, excelClass, 10, rowAccessWindowSize, recordCountPerSheet, openAutoColumWidth);
    }
}
