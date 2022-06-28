package LogToExcel.LowFlow;

import Utils.ExcelUtils;
import Utils.FileUtils;
import Utils.LogUtils;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.*;

/**
 * 仅支持数据导出到单个表格当中，数据量有限
 */
@Deprecated
public class LowFlowOPCUA {
    private static XSSFWorkbook SOURCE;
    private static final int COLUMNSIZE = 30 * 256;
    private static final int PEMSG = 1;

    static {
        try {
            SOURCE = new XSSFWorkbook("src\\Files\\ExcelModel\\LowFlowModel.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Row firstRow;
    private int lastHeader;
    private final int startHeader;
    private int sheetNum = 1;

    private final Map<String, PE> keyPE = new HashMap<>();
    private final Map<PE, Row> PEMap = new HashMap<>();

    public LowFlowOPCUA() {
        Sheet sheet = SOURCE.getSheet("source");
        this.firstRow = sheet.getRow(0);
        this.lastHeader = (int) ExcelUtils.getRowSize(firstRow);
        this.startHeader = lastHeader;
        putRowWithKey(sheet);
    }

    /**
     * 将每行与PE对应上
     */
    public void putRowWithKey(Sheet sheet) {
        for (Row row : sheet) {
            PE pe = new PE(ExcelUtils.readCell(row.getCell(PEMSG)));
            keyPE.put(pe.getName(), pe);
            PEMap.put(pe, row);
        }
    }

    /**
     * 将每一行的数据读取储存进PE里
     */
    private void add(String PEname, String Cstart, String Cstop) {
        PE pe = keyPE.get(PEname);
        if (pe != null) pe.add(Cstart, Cstop);
    }

    /**
     * 按照PE的顺序导出里面的数据至Excel里
     */
    private void exportPE(PE pe) {
        pe.sort();
        Row row = PEMap.get(pe);
        if (row == null) return;

        int loc = startHeader;
        System.out.println(pe.name);
        Pair<String, String> prior = null;
        for (Pair<String, String> pair : pe.getList()) {
            ExcelUtils.setCellValue(row, loc++, LogUtils.millsToDate(pair.getFirst()));
            ExcelUtils.setCellValue(row, loc++, LogUtils.millsToDate(pair.getSecond()));
            if (prior != null) {
                ExcelUtils.setCellValue(row, loc++, String.valueOf(Long.parseLong(pair.getFirst()) - Long.parseLong(prior.getSecond())));
            } else {
                loc++;
            }
            prior = pair;
            if (loc > lastHeader) {
                increaseHeader();
            }
            if (lastHeader > 16000) {
                increaseSheet();
                loc = startHeader;
            }
        }
    }

    /**
     * 当当前行的长度大于整张表的最大长度时增加标题
     */
    private void increaseHeader() {
        ExcelUtils.setCellValue(firstRow, lastHeader++, "Cstart");
        firstRow.getSheet().setColumnWidth(lastHeader - 1, COLUMNSIZE);

        ExcelUtils.setCellValue(firstRow, lastHeader++, "Cstop");
        firstRow.getSheet().setColumnWidth(lastHeader - 1, COLUMNSIZE);

        ExcelUtils.setCellValue(firstRow, lastHeader++, "Gap");
        firstRow.getSheet().setColumnWidth(lastHeader - 1, COLUMNSIZE);
    }

    private void increaseSheet() {
        SOURCE.cloneSheet(0, "sheet" + sheetNum);
        firstRow = SOURCE.getSheet("sheet" + sheetNum).getRow(0);
        ++sheetNum;
        lastHeader = startHeader;
    }

    public static void main(String[] args) throws IOException {
        LowFlowOPCUA flowOPCUA = new LowFlowOPCUA();
        long t1 = System.nanoTime();
        FileUtils.readFile("C:\\Users\\Zsm\\Desktop\\opcua-logger.log")
                .stream()
                .filter(s -> s.contains("PE"))
                .forEach(s -> {
                    String[] strings = s.split(",");
                    flowOPCUA.add(strings[0].substring(s.indexOf("P")), strings[2], strings[4]);
                });

        flowOPCUA.PEMap.keySet().forEach(flowOPCUA::exportPE);

        FileUtils.exportExcel(SOURCE, "C:\\Users\\Zsm\\Desktop\\random1.xlsx");
        System.out.println((System.nanoTime() - t1) / 1000000000);
    }
}
