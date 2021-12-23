package LogToExcel.Log.Entity;

import Utils.ExcelUtils;
import Utils.StringUtils;
import LogToExcel.Log.Log;
import LogToExcel.Log.LogText;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
    public void setText(Workbook book, int row, int column) {
        Sheet sheet = ExcelUtils.getSheet(book, "Master");
        //所有表格的数据均设置成上下居中，左右居中
        CellStyle style = book.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置顶部
        sheet.addMergedRegion(new CellRangeAddress(row, row, column, column + getHeader().length - 1));
        ExcelUtils.createCellSetStyle(ExcelUtils.getRow(sheet, row++), style, column)
                .setCellValue(getKey());

        //设置列表标题
        int col = column;
        for (String head : getHeader()) {
            sheet.setColumnWidth(col, 20 * 256);
            ExcelUtils.createCellSetStyle(ExcelUtils.getRow(sheet, row), style, col++)
                    .setCellValue(head);
        }

        //写入LogText
        final String startcolumn = String.valueOf((char) (column + 'A'));
        final String endcolumn = String.valueOf((char) (column + 'A' + 1));
        final AtomicInteger atomicrow = new AtomicInteger(++row);//记录行号
        final AtomicInteger atomiccol = new AtomicInteger(column);//记录列号
        final AtomicReference<Row> r = new AtomicReference<>();

        getList()
                .stream()
                .peek(logText -> {
                    atomiccol.set(column);
                    r.set(ExcelUtils.getRow(sheet, atomicrow.get()));
                    logText.getList()
                            .stream()
                            .limit(2)
                            .forEach(s -> ExcelUtils.createCellSetStyle(r.get(), style, atomiccol.getAndIncrement())
                                    .setCellValue(s));
                    ExcelUtils.createCellSetStyle(r.get(), style, atomiccol.getAndIncrement())
                            .setCellValue(Integer.parseInt(logText.getList().get(2)));
                    ExcelUtils.createCellSetStyle(r.get(), style, atomiccol.getAndIncrement())
                            .setCellFormula(endcolumn + atomicrow.incrementAndGet() + "-" + startcolumn + atomicrow.get());
                })
                .skip(1)
                .forEach(logText ->
                        ExcelUtils.createCellSetStyle(r.get(), style, atomiccol.get())
                                .setCellFormula(startcolumn + atomicrow.get() + "-" + endcolumn + (atomicrow.get() - 1))
                );
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
