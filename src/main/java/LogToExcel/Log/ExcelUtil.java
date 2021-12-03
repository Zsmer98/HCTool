package LogToExcel;

import org.apache.poi.ss.usermodel.*;

class ExcelUtil {
    static Sheet getSheet(Workbook wb, String sheetname) {
        return (wb.getSheet(sheetname) == null) ? wb.createSheet(sheetname) : wb.getSheet(sheetname);
    }

    static Row getRow(Sheet sheet, int row) {
        return (sheet.getRow(row) == null) ? sheet.createRow(row) : sheet.getRow(row);
    }

    static Cell setValue(Row row, CellStyle style, int column) {
        Cell c = row.createCell(column);
        c.setCellStyle(style);
        return c;
    }
}