package LogToExcel.Log.Entity;

import LogToExcel.Log.Log;
import LogToExcel.Log.LogText;
import Utils.LogUtils;
import Utils.ExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
    public void setText(Workbook book, int row, final int column) {
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
                    logText.getList().forEach(s ->
                            ExcelUtils.createCellSetStyle(r.get(), style, atomiccol.getAndIncrement()).setCellValue(Integer.parseInt(s))
                    );
                    ExcelUtils.createCellSetStyle(r.get(), style, atomiccol.getAndIncrement())
                            .setCellFormula(endcolumn + atomicrow.incrementAndGet() + "-" + startcolumn + atomicrow.get());
                })
                .skip(1)
                .forEach(s ->
                        ExcelUtils.createCellSetStyle(r.get(), style, atomiccol.get())
                                .setCellFormula(startcolumn + atomicrow.get() + "-" + endcolumn + (atomicrow.get() - 1))
                );
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\Zsm\\Desktop";
        XSSFWorkbook book = new XSSFWorkbook();

        new Demo3D("PE20", path).setText(book, 0, 0);

        try (FileOutputStream out = new FileOutputStream(path + "\\log.xlsx")) {
            book.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}