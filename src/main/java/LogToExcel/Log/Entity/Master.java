package LogToExcel.Log.Entity;

import Utils.ExcelUtil;
import Utils.StringUtils;
import LogToExcel.Log.Log;
import LogToExcel.Log.LogText;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Comparator;
import java.util.HashMap;

public class Master extends Log implements LogToExcel {
    public Master(String key, String path) {
        super(key, new String[]{"newStart", "newEnd", "MergeTime", "包裹长度", "与上个包裹的间隔"}, path);
        setData("\\master-logger.log");
    }

    @Override
    public LogText getDataByRules(String s) {
        return getDataByRules2(s);
    }

    @Override
    public Comparator<LogText> compare() {
        return (o1, o2) -> (int)
                (Long.parseLong(o1.getList().get(0)) -
                        Long.parseLong(o2.getList().get(0)));
    }

    @Override
    public void setText(Workbook book, int column) {
        Sheet sheet = ExcelUtil.getSheet(book, "Master");
        //所有表格的数据均设置成上下居中，左右居中
        CellStyle style = book.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置顶部
        int col = column;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, col, col + getHeader().length - 1));
        ExcelUtil.createCellSetStyle(ExcelUtil.getRow(sheet, 0), style, col)
                .setCellValue(getKey());

        //设置列表标题
        for (String head : getHeader()) {
            sheet.setColumnWidth(col, 20 * 256);
            ExcelUtil.createCellSetStyle(ExcelUtil.getRow(sheet, 1), style, col++)
                    .setCellValue(head);
        }

        //写入LogText
        if (getList() == null) return;
        int row = 3;
        col = column;
        String startcolumn = String.valueOf((char) (column + 'A'));
        String endcolumn = String.valueOf((char) (column + 'A' + 1));
        for (LogText text : getList()) {
            Row r = ExcelUtil.getRow(sheet, row - 1);
            for (int i = 0; i < 2; ++i) {
                ExcelUtil.createCellSetStyle(r, style, col++)
                        .setCellValue(text.getList().get(i));
            }
            ExcelUtil.createCellSetStyle(r, style, col++)
                    .setCellValue(Integer.parseInt(text.getList().get(2)));
            ExcelUtil.createCellSetStyle(r, style, col++)
                    .setCellFormula(endcolumn + row + "-" + startcolumn + row);
            if (row != 3) {
                ExcelUtil.createCellSetStyle(r, style, col)
                        .setCellFormula(startcolumn + row + "-" + endcolumn + (row - 1));
            }
            ++row;
            col = column;
        }
    }

    //初始修改方法
    private LogText getDataByRules1(String s) {
        String[] keys = {"newStart", "newEnd", "MergeTime"};
        HashMap<String, String> hashMap = StringUtils.getValue(keys, s);

        LogText logText = new LogText(getHeader().length);
        if (s.contains(getKey())) {
            for (String key : keys) {
                logText.addElement(hashMap.get(key));
            }
        }
        return logText;
    }

    //2021.12.7修改后的Master处理方法
    private LogText getDataByRules2(String s) {
        String[] keys = {"newStart", "newEnd", "MergeTime", "MergeTimeStamp"};
        HashMap<String, String> hashMap = StringUtils.getValue(keys, s);

        LogText logText = new LogText(getHeader().length);
        if (s.contains(getKey())) {
            logText.addElement(String.valueOf(
                    Long.parseLong(hashMap.get(keys[0])) +
                            Long.parseLong((hashMap.get(keys[3])))
            ));
            logText.addElement(String.valueOf(
                    Long.parseLong(hashMap.get(keys[1])) +
                            Long.parseLong((hashMap.get(keys[3])))
            ));
            logText.addElement(hashMap.get(keys[2]));
        }
        return logText;
    }
}
