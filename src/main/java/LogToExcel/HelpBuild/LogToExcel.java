package LogToExcel.HelpBuild;

import LogToExcel.Log.ExcelUtil;
import LogToExcel.Log.Log;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.IOException;

public interface LogToExcel<T extends Log> {
    default void insertIntoExcel(Workbook book, String sheetname, T t, int column) {
        Sheet sheet = ExcelUtil.getSheet(book, sheetname);
        //所有表格的数据均设置成上下居中，左右居中
        CellStyle style = book.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        int row = 0, col = column;
        sheet.addMergedRegion(new CellRangeAddress(row, row, col, col + t.getHeader().length - 1));
        ExcelUtil.setValue(ExcelUtil.getRow(sheet, row++), style, col).setCellValue(t.getKey());

        for (String head : t.getHeader()) {
            sheet.setColumnWidth(col, 20 * 256);
            ExcelUtil.setValue(ExcelUtil.getRow(sheet, row), style, col++).setCellValue(head);
        }

        if (t.getList() == null) return;
        setText(t, sheet, column, style);

        String LogPath = t.path + "\\log.xlsx";

        try (FileOutputStream out = new FileOutputStream(LogPath)) {
            book.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setText(T t, Sheet sheet, int column, CellStyle style);
}