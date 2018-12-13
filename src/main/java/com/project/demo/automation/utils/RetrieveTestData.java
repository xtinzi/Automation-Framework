package com.project.demo.automation.utils;


import com.project.demo.automation.leadDBUtils.*;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.project.demo.automation.utils.Util.*;


public class RetrieveTestData {

    CaseForm caseFormDataRow;


    private String testDataFileToRead;
    private List<CaseForm> _caseDataRowSet = new ArrayList<CaseForm>();

    Logger log = Logger.getLogger(RetrieveTestData.class);
    private ReadConfig readConfig = null;
    private static XSSFWorkbook wBook = null;
    private XSSFWorkbook workBook = null;
    LoginDetail loginDetail = null;

    public RetrieveTestData(String testDataFileToRead) {
        this.testDataFileToRead = testDataFileToRead;
    }

    public List<CaseForm> readCaseData() {
        try {
            // Get the workbook object for XLSX file
            XSSFWorkbook wBook = getWorkBook();
            XSSFSheet sheet = null;
            // Get the sheet from the workbook
            if (wBook != null) {
                sheet = wBook.getSheetAt(TESTS_BOOK_NO);
            }

            for (List list : getStringRows(sheet, getReadConfig().getFromRow(), getReadConfig().getUpToRow(), TESTS_COLUMNS)) {
                caseFormDataRow = new CaseForm(list);
                _caseDataRowSet.add(caseFormDataRow);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return _caseDataRowSet;
    }


    /**
     * @return
     */
    public LoginDetail getLoginDetail() {
        try {
            ArrayList<String> localList;
            // Get the workbook object for XLSX file
            XSSFWorkbook wBook = getWorkBook();
            // Get the sheet from the workbook
            if (wBook != null) {
                XSSFSheet sheet = wBook.getSheetAt(LOGIN_BOOK_NO);
                if (loginDetail == null) {
                    localList = (ArrayList) getStringRows(sheet, 2, 2, LOGIN_COLUMNS).get(0);
                    loginDetail = new LoginDetail(localList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return loginDetail;
    }

    /**
     * Setting retrieved test data to the lead test data Object
     *
     * @param
     * @return
     */


    public void writeToDataSheet(String dataSheet, int bookNo, int rowNo, int colNo, String value) {
        FileOutputStream out = null;
        try {
            // Get the workbook object for XLSX file
            XSSFWorkbook wBook = new XSSFWorkbook(new FileInputStream(dataSheet));
            // Get first sheet from the workbook
            XSSFSheet sheet = wBook.getSheetAt(bookNo);
            Row r1 = sheet.getRow(rowNo);
            r1.createCell(colNo).setCellValue(value);
            out = new FileOutputStream(dataSheet);
            wBook.write(out);

        } catch (IOException e) {
            log.error(e.getMessage());

        } finally {
            try {
                if (out != null) {
                    out.close();

                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }

        }
    }

    public ReadConfig getReadConfig() {
        ArrayList<String> localList;
        try {

            if (readConfig == null) {

                // Get the workbook object for XLSX file
                XSSFWorkbook wBook = getWorkBook();
                // Get the sheet from the workbook
                if (wBook != null) {
                    XSSFSheet sheet = wBook.getSheetAt(CONFIG_BOOK_NO);
                    localList = (ArrayList) getStringRows(sheet, CONFIG_DATA_ROW_NO, CONFIG_DATA_ROW_NO, CONFIG_DATA_COLUMNS).get(0);
                    readConfig = new ReadConfig(localList);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return readConfig;

    }

    public List<String> readRow(int bookNo, int rowNum) {
        List cells = new ArrayList();

        try {
            // Get the workbook object for XLSX file
            XSSFWorkbook wBook = getWorkBook();
            // Get the sheet from the workbook
            if (wBook != null) {
                XSSFSheet sheet = wBook.getSheetAt(bookNo);

                Row row = sheet.getRow(rowNum);
                for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                    Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
                    //In case a cell is null
                    if (cell == null) {
                        cell = row.createCell((short) 0);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellValue(" ");
                    }
                    addToCell(cell, cells);

                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RetrieveTestData.class).error(ex.getMessage());

        }
        return cells;
    }

    public List<String> readRow(int bookNo, int rowNum, int columns) {
        List cells = new ArrayList();

        try {
            // Get the workbook object for XLSX file
            XSSFWorkbook wBook = getWorkBook();
            if (wBook != null) {
                // Get the sheet from the workbook
                XSSFSheet sheet = wBook.getSheetAt(bookNo);

                Row row = sheet.getRow(rowNum);
                for (int i = 0; i < columns; i++) {
                    Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
                    //In case a cell is null
                    if (cell == null) {
                        cell = row.createCell((short) 0);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellValue(" ");
                    }
                    addToCell(cell, cells);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RetrieveTestData.class).error("IOException - " + ex.getCause());

        }
        return cells;
    }

    /**
     * @param cellContent
     * @param bookNo
     * @return
     */
    public List<String> getRow(String cellContent, int bookNo) {
        List<String> stringList = null;
        try {
            // Get the workbook object for XLSX file
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(testDataFileToRead));
            // Get first sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(bookNo);
            stringList = new ArrayList<String>();
            String cellValue = "";
            for (Row row : sheet) {
                if (row.getRowNum() > 1) {
                    for (Cell cell : row) {
                        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            cellValue = cell.getStringCellValue();
                            if (cellValue.trim().equalsIgnoreCase(cellContent)) {
                                for (Cell matchCell : row) {
                                    stringList.add(matchCell.toString());
                                }
                                return stringList;
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RetrieveTestData.class).error("IOException - " + ex.getCause());

        }
        return stringList;
    }

    public List<String> getRow(String cellContent, int bookNo, int colNumber, int columns) {
        List<String> stringList = new ArrayList<String>();
        List<List<String>> listOfLists;
        int from = getReadConfig().getFromRow();
        int uptTo = getReadConfig().getUpToRow();
        try {
            XSSFWorkbook wBook = getWorkBook();
            if (wBook != null) {
                XSSFSheet sheet = wBook.getSheetAt(bookNo);
                listOfLists = getStringRows(sheet, from, uptTo, columns);
                for (List list : listOfLists) {
                    if (list.get(colNumber).toString().equalsIgnoreCase(cellContent)) {
                        stringList = list;
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Exception - " + ex.getCause());

        }
        return stringList;
    }


    /**
     * This method first checks if an instance of XSSFWorkbook exists first
     * and if it doesn't it creates one
     *
     * @return
     */
    private XSSFWorkbook getwBook() {
        try {

            if (wBook == null) {
                wBook = new XSSFWorkbook(new FileInputStream(testDataFileToRead));
            }
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return wBook;
    }

    /**
     * @return
     */
    private XSSFWorkbook getWorkBook() {
        try {
            if (workBook == null) {
                workBook = new XSSFWorkbook(new FileInputStream(testDataFileToRead));
            }

        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return workBook;
    }


    public List<String> getColumns(Integer sheetNumber, int columnNumber, Integer rowUpTo) {
        List<String> values = new ArrayList();
        XSSFWorkbook wBook = getWorkBook();
        if (wBook != null) {
            XSSFSheet sheet = wBook.getSheetAt(sheetNumber);
            int count = 0;
            readConfig = getReadConfig();
            for (Row r : sheet) {
                count++;
                if (count > 2) {
                    Cell c = r.getCell(columnNumber);
                    if (c == null) {
                        //c.setCellType(Cell.CELL_TYPE_STRING);
                        //c.setCellValue(" ");
                    }
                    if (c != null) {
                        System.out.print(c);
                        if (c.getCellType() == Cell.CELL_TYPE_STRING) {
                            if (!c.getStringCellValue().equalsIgnoreCase(" ")) {
                                values.add(c.getStringCellValue());
                            }
                        } else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            values.add(String.valueOf(c.getNumericCellValue()));
                        }
                        if (count == readConfig.getUpToRow()) {
                            break;
                        }
                    }
                }
            }
        }
        return values;
    }

    /**
     * This method reads exactly from a specific row to a specific row. Nothing before and/or after
     *
     * @param sheet
     * @param from
     * @param to
     * @param columnNumbers
     * @return
     */
    private List<List<String>> getStringRows(XSSFSheet sheet, int from, int to, int columnNumbers) {
        List<String> cells = null;
        List<List<String>> listOfCells = new ArrayList<List<String>>();
        try {
            //Because the for loop is zero indexed. Start looping from one less
            for (int i = from - 1; i <= to - 1; i++) {
                cells = new ArrayList();
                for (int j = 0; j < columnNumbers; j++) {
                    Cell cell = sheet.getRow(i).getCell(j, Row.RETURN_BLANK_AS_NULL);

                    //In case a cell is null
                    if (cell == null) {
                        cell = sheet.getRow(i).createCell((short) 0);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellValue(" ");
                    }
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            cells.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            String numeric = String.valueOf((long) cell.getNumericCellValue());
                            cells.add(numeric);
                            break;
                        case Cell.CELL_TYPE_STRING:
                            cells.add(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            cells.add(" ");
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            cells.add(cell.getStringCellValue());
                            break;
                        default:
                            cells.add(" ");
                    }
                }
                if (cells.size() == columnNumbers) {
                    listOfCells.add(cells);
                }
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return listOfCells;
    }

    private void addToCell(Cell cell, List cells) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                cells.add(String.valueOf(cell.getBooleanCellValue()));
                break;
            case Cell.CELL_TYPE_NUMERIC:
                String numeric = String.valueOf((long) cell.getNumericCellValue());
                cells.add(numeric);
                break;
            case Cell.CELL_TYPE_STRING:
                cells.add(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                cells.add(" ");
                break;
            default:
                cells.add(" ");
        }
    }
}

