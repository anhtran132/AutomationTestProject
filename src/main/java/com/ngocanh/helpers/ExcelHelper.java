package com.ngocanh.helpers;

import com.ngocanh.constants.FrameworkConstants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ExcelHelper {
    FileInputStream fis;
    Workbook workbook;
    Sheet sheet;
    Row row;
    Cell cell;
    Map<String, Integer> columns = new HashMap<>();

    public Map<String, Integer> setExcelFile(String excelFilePath, String sheetName) {

        try {
            fis = new FileInputStream(excelFilePath);
            workbook = WorkbookFactory.create(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sheet = workbook.getSheet(sheetName);

        //adding all the column header names to the map 'columns'
        sheet.getRow(0).forEach(cell -> {
            columns.put(cell.getStringCellValue(), cell.getColumnIndex());
        });

        return columns;
    }

    public int getRows(){
        return sheet.getLastRowNum();
    }

    public int getColumns(){
        row = sheet.getRow(0);
        return row.getLastCellNum();
    }
    public Object[][] getDataHashTable(String excelPath, String sheetName, int startRow, int endRow) {
        Object[][] data = null;

        try {

            fis = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

            int rows = getRows();
            int columns = getColumns(); //4

            data = new Object[(endRow - startRow) + 1][1];

            Hashtable<String, String> table = null;
            for (int rowNums = startRow; rowNums <= endRow; rowNums++) {
                table = new Hashtable<>();
                for (int colNum = 0; colNum < columns; colNum++) {
                    table.put(getCellData(0, colNum), getCellData(rowNums, colNum));
                }
                data[rowNums - startRow][0] = table;
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return data;
    }

    public String getCellData(int rowNum, int colNum) {
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            String CellData = null;
            switch (cell.getCellType()) {
                case STRING:
                    CellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        CellData = String.valueOf(cell.getDateCellValue());
                    } else {
                        CellData = String.valueOf((long) cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    CellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    CellData = "";
                    break;
            }
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }
    public String getCellData(int rowNum, String columnName) {
        return getCellData(rowNum, columns.get(columnName));
    }

    // Step 1 : Create object XSSFWorkbook
    // Step 2: Get access to sheet
    // Step 3: Get access to all rows of sheet
    // Step 4 get all cells of the rows
    // Step 5: Get access to data of the cell
}

