package LogToExcel.LowFlow;

import Utils.ExcelUtils;
import Utils.LogUtils;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExportPE {
    private static XSSFWorkbook BOOK;
    private static final Map<String, Integer> PE_LOC_MAP = new HashMap<>();
    private static final int start;
    private static final int COLUMNSIZE = 30 * 256;

    static {
        try {
            BOOK = new XSSFWorkbook(Main.EXCEL_MODEL_PATH);
            BOOK.getSheet("source").iterator().forEachRemaining(
                    row -> PE_LOC_MAP.put(ExcelUtils.readCell(row.getCell(1)), row.getRowNum())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        start = (int) ExcelUtils.getRowSize(BOOK.getSheet("source").getRow(0));
    }

    public void processPE(PE pe) {
        pe.sort();

        System.out.println(pe.name + " start");
        int sheetNum = 1, rowNum = PE_LOC_MAP.get(pe.name), loc = start;
        Row peRow = getSheet(sheetNum).getRow(rowNum), firstRow = getSheet(sheetNum).getRow(0);
        int headerSize = (int) ExcelUtils.getRowSize(firstRow);
        Pair<String, String> piror = null;

        for (Pair<String, String> pair : pe.getList()) {
            ExcelUtils.setCellValue(peRow, loc++, LogUtils.millsToDate(pair.getFirst()));
            ExcelUtils.setCellValue(peRow, loc++, LogUtils.millsToDate(pair.getSecond()));
            if (piror != null) {
                ExcelUtils.setCellValue(peRow, loc, String.valueOf(Long.parseLong(pair.getFirst()) - Long.parseLong(piror.getSecond())));
            }
            loc++;
            piror = pair;
            if (loc > headerSize) {
                ExcelUtils.setCellValue(firstRow, headerSize++, "Cstart");
                firstRow.getSheet().setColumnWidth(headerSize - 1, COLUMNSIZE);

                ExcelUtils.setCellValue(firstRow, headerSize++, "Cstart");
                firstRow.getSheet().setColumnWidth(headerSize - 1, COLUMNSIZE);

                ExcelUtils.setCellValue(firstRow, headerSize++, "Gap");
                firstRow.getSheet().setColumnWidth(headerSize - 1, 10 * 256);
            }

            if (loc > 16000) {
                peRow = getSheet(++sheetNum).getRow(rowNum);
                loc = start;
                firstRow = getSheet(sheetNum).getRow(0);
                headerSize = (int) ExcelUtils.getRowSize(firstRow);
            }
        }
        System.out.println(pe.name);
    }

    private Sheet getSheet(int num) {
        Sheet target = BOOK.getSheet("Data" + num);
        if (target != null) return target;
        return BOOK.cloneSheet(0, "Data" + num);
    }

    public void exportExcel(String path) {
        ExcelUtils.exportExcel(path, BOOK);
    }
}
