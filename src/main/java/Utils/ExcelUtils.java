package Utils;

import org.apache.poi.ss.usermodel.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ExcelUtils {
    public static Sheet getSheet(Workbook wb, String sheetname) {
        return (wb.getSheet(sheetname) == null) ? wb.createSheet(sheetname) : wb.getSheet(sheetname);
    }

    public static Row getRow(Sheet sheet, int row) {
        return (sheet.getRow(row) == null) ? sheet.createRow(row) : sheet.getRow(row);
    }

    /**
     * 创建第row行第column列单元格，并建立style，返回单元格
     *
     * @param row    行号
     * @param style  样式
     * @param column 列号
     * @return 单元格
     */
    public static Cell createCellSetStyle(Row row, CellStyle style, int column) {
        Cell c = row.createCell(column);
        c.setCellStyle(style);
        return c;
    }

    /**
     * 返回一个Sheet里最后一行数据的行号
     *
     * @param sheet 待查询的sheet
     * @return 行号，从0开始
     */
    public static int getLastRow(Sheet sheet) {
        int res = 0;
        while (sheet.getRow(res) != null) {
            ++res;
        }
        return res;
    }

    public static <T> int getIndexWithSpecifiedName(Row row, T name) {
        for (Cell cell : row) {
            if (Objects.equals(readCell(cell), name)) return cell.getColumnIndex();
        }
        throw new NoSuchElementException();
    }

    /**
     * 读取Cell里的值,并将其转化成String类型返回
     */

    public static String readCell(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) return "";
        switch (cell.getCellType()) {
            case STRING, FORMULA -> {
                return cell.getStringCellValue();
            }
            case NUMERIC -> {
                return String.valueOf(cell.getNumericCellValue());
            }
        }
        throw new IllegalArgumentException("At Sheet " + cell.getSheet().getSheetName() + " Row " + (cell.getRowIndex() + 1) + " Column " + (cell.getColumnIndex() + 1) + " has wrong type or bad value");
    }


    public static Map<String, String> rowToMap(Row dataSource) {
        return rowToMapWithTitleRow(dataSource, dataSource.getSheet().getRow(0));
    }

    public static Map<String, String> rowToMapWithTitleRow(Row dataSource, Row title) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(dataSource.cellIterator(), Spliterator.ORDERED), false)
                .collect(Collectors.toMap(
                        cell -> ExcelUtils.readCell(title.getCell(cell.getColumnIndex())),
                        ExcelUtils::readCell
                ));
    }
}