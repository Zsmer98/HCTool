package LogToExcel.Log.Entity;

import Utils.ExcelUtils;
import Utils.FileUtils;
import Utils.LogUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LowFlowOPCUA {
    private final Row firstRow;
    private int lastHeader;
    private final Map<String, Row> PEMap = new HashMap<>();
    private final Map<Row, Integer> lastMsg = new HashMap<>();

    public LowFlowOPCUA(Sheet sheet) {
        this.firstRow = sheet.getRow(0);
        this.lastHeader = (int) ExcelUtils.getRowSize(firstRow);
        for (Row row : sheet) {
            PEMap.put(ExcelUtils.readCell(row.getCell(1)), row);
            lastMsg.put(row, lastHeader);
        }
    }

    private void add(String PE, String Cstart, String Cstop) {
        Row row = PEMap.get(PE);
        if (row == null) return;
        int loc = lastMsg.get(row);
        ExcelUtils.setCellValue(row, loc++, Cstart);
        ExcelUtils.setCellValue(row, loc++, Cstop);
        if (loc > lastHeader) {
            increaseHeader();
        }
        lastMsg.put(row, loc);
    }

    private void increaseHeader() {
        ExcelUtils.setCellValue(firstRow, lastHeader++, "Cstart");
        firstRow.getSheet().setColumnWidth(lastHeader - 1, 20 * 256);
        ExcelUtils.setCellValue(firstRow, lastHeader++, "Cstop");
        firstRow.getSheet().setColumnWidth(lastHeader - 1, 20 * 256);
    }

    public static void main(String[] args) throws IOException {
        XSSFWorkbook book = new XSSFWorkbook("C:\\Users\\Zsm\\Desktop\\random.xlsx");
        Sheet sheet = book.getSheet("sheet1");
        LowFlowOPCUA flowOPCUA = new LowFlowOPCUA(sheet);

        FileUtils.readFile("C:\\Users\\Zsm\\Desktop\\opcua-logger.log")
                .stream()
                .filter(s -> s.contains("PE"))
                .forEach(s -> {
                    String[] strings = s.split(",");
                    String pe = strings[0].substring(s.indexOf("P"));
                    flowOPCUA.add(pe, LogUtils.millsToDate(strings[2]), LogUtils.millsToDate(strings[4]));
                });

        try (FileOutputStream out = new FileOutputStream("C:\\Users\\Zsm\\Desktop\\random1.xlsx")) {
            book.write(out);
            System.out.println("Excel文件写入成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
