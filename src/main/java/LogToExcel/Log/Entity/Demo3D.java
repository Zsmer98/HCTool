package LogToExcel.Log.Entity;

import LogToExcel.Log.Log;
import LogToExcel.Log.LogText;
import Utils.LogUtils;
import Utils.ExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Comparator;

public class Demo3D extends Log implements LogToExcel {
    public Demo3D(String key, String path) {
        super(key, new String[]{"DStart", "DStop", "包裹长度", "与上个包裹的间隔"}, path);
        setData("\\demo3dlog.txt");
    }

    @Override
    public LogText getDataByRules(String s) {
        String[] keys = {"DStart", "DStop"};
        return LogUtils.addListIntoText(s, getKey(), keys, getHeader().length);
    }

    @Override
    public Comparator<LogText> compare() {
        return Comparator.comparingInt(o -> Integer.parseInt(o.getList().get(0)));
    }

    @Override
    public void setText(Workbook book, int row, int column) {
        Sheet sheet = ExcelUtils.getSheet(book, "Demo3D");
        //所有表格的数据均设置成上下居中，左右居中
        CellStyle style = book.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置顶部
        int col = column;
        sheet.addMergedRegion(new CellRangeAddress(row, row, col, col + getHeader().length - 1));
        ExcelUtils.createCellSetStyle(ExcelUtils.getRow(sheet, row++), style, col)
                .setCellValue(getKey());

        //设置列表标题
        for (String head : getHeader()) {
            sheet.setColumnWidth(col, 20 * 256);
            ExcelUtils.createCellSetStyle(ExcelUtils.getRow(sheet, row), style, col++)
                    .setCellValue(head);
        }

        //写入LogText
        ++row;
        boolean isfirst = true;
        if (getList() == null) return;
        String startcolumn = String.valueOf((char) (column + 'A'));
        String endcolumn = String.valueOf((char) (column + 'A' + 1));
        for (LogText text : getList()) {
            col = column;
            Row r = ExcelUtils.getRow(sheet, row);
            for (String s : text.getList()) {
                ExcelUtils.createCellSetStyle(r, style, col++)
                        .setCellValue(Integer.parseInt(s));
            }
            ExcelUtils.createCellSetStyle(r, style, col++)
                    .setCellFormula(endcolumn + (++row) + "-" + startcolumn + row);
            if (!isfirst) {
                ExcelUtils.createCellSetStyle(r, style, col)
                        .setCellFormula(startcolumn + row + "-" + endcolumn + (row - 1));
            }
            isfirst = false;
        }
    }
}