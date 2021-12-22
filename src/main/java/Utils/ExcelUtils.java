package Utils;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {
    public static Sheet getSheet(Workbook wb, String sheetname) {
        return (wb.getSheet(sheetname) == null) ? wb.createSheet(sheetname) : wb.getSheet(sheetname);
    }

    public static Row getRow(Sheet sheet, int row) {
        return (sheet.getRow(row) == null) ? sheet.createRow(row) : sheet.getRow(row);
    }

    /**
     * 创建第row行第column列单元格，并建立style，返回单元格
     * @param row 行号
     * @param style 样式
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
     * @param sheet 待查询的sheet
     * @return 行号，从0开始
     */
    public static int getLastRow(Sheet sheet){
        int res = 0;
        while(sheet.getRow(res) != null){
            ++res;
        }
        return res;
    }
}