package LogToExcel.Log;

import FileUtils.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;

public class LogUtils {
    public static Sheet getSheet(Workbook wb, String sheetname) {
        return (wb.getSheet(sheetname) == null) ? wb.createSheet(sheetname) : wb.getSheet(sheetname);
    }

    public static Row getRow(Sheet sheet, int row) {
        return (sheet.getRow(row) == null) ? sheet.createRow(row) : sheet.getRow(row);
    }

    public static Cell createCellSetStyle(Row row, CellStyle style, int column) {
        Cell c = row.createCell(column);
        c.setCellStyle(style);
        return c;
    }

    public static LogText addListIntoText(String s,String key, String[] keys, int textlength){
        LogText logText = new LogText(textlength);
        if (s.contains(key)) {
            HashMap<String, String> res = StringUtils.getValue(keys, s);
            if (res.size() != 0) {
                for (String k : keys) {
                    logText.addElement(res.get(k));
                }
            }
        }

        return logText;
    }
}