package Utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ExcelUtils {

    public static void exportExcel(String path, Workbook book) {
        try (FileOutputStream out = new FileOutputStream(path)) {
            book.write(out);
            System.out.printf("The file located in the %s was created successfull\n", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Sheet getSheet(Workbook wb, String sheetname) {
        return (wb.getSheet(sheetname) == null) ? wb.createSheet(sheetname) : wb.getSheet(sheetname);
    }

    public static Row getRow(Sheet sheet, int row) {
        return (sheet.getRow(row) == null) ? sheet.createRow(row) : sheet.getRow(row);
    }

    public static Cell getCell(Row row, int cell) {
        return (row.getCell(cell) == null) ? row.createCell(cell) : row.getCell(cell);
    }

    public static Cell getCell(Sheet sheet, int row, int cell) {
        Row row1 = ExcelUtils.getRow(sheet, row);
        return ExcelUtils.getCell(row1, cell);
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
    public static long getSheetSize(Sheet sheet) {
        return CollectionUtils.getIterableSize(sheet);
    }

    public static long getRowSize(Row row) {
        return CollectionUtils.getIterableSize(row);
    }

    public static void setCellValue(Row row, int loc, String value) {
        Cell cell = ExcelUtils.getCell(row, loc);
        cell.setCellValue(value);
    }

    public static void setCellValue(Row row, int loc, String value, int width) {
        Cell cell = ExcelUtils.getCell(row, loc);
        cell.setCellValue(value);

    }

    public static <T> int getIndexWithSpecifiedName(Row row, T name) {
        for (Cell cell : row) {
            if (Objects.equals(readCell(cell), name)) {
                return cell.getColumnIndex();
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * 读取Cell里的值,并将其转化成String类型返回
     */
    public static String readCell(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }
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

    public static boolean notBlankCell(Cell cell) {
        return Objects.nonNull(cell) && !readCell(cell).isEmpty();
    }

    public static Map<String, String> rowToMapWithTitleRow(Row dataSource, Row title) {
        System.out.println(dataSource.getRowNum() + " row start");
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(dataSource.cellIterator(), Spliterator.ORDERED), false)
                .filter(ExcelUtils::notBlankCell)
                .collect(Collectors.toMap(
                        cell -> ExcelUtils.readCell(title.getCell(cell.getColumnIndex())),
                        ExcelUtils::readCell
                ));
    }
}