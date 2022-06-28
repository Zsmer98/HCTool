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
    private XSSFWorkbook BOOK;
    private final Map<String, Integer> PE_LOC_MAP = new HashMap<>();
    private final int start;

    public ExportPE() {
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
        Row peRow = getSheet(sheetNum).getRow(rowNum);
        Pair<String, String> piror = null;

        for (Pair<String, String> pair : pe.getList()) {
            ExcelUtils.setCellValue(peRow, loc++, LogUtils.millsToDate(pair.getFirst()));
            ExcelUtils.setCellValue(peRow, loc++, LogUtils.millsToDate(pair.getSecond()));
            if (piror != null) {
                ExcelUtils.setCellValue(peRow, loc, String.valueOf(Long.parseLong(pair.getFirst()) - Long.parseLong(piror.getSecond())));
            }
            loc++;
            piror = pair;

            if (loc > 16000) {
                peRow = getSheet(++sheetNum).getRow(rowNum);
                loc = start;
            }
        }
        System.out.println(pe.name + " end");
    }

    private Sheet getSheet(int num) {
        Sheet target = BOOK.getSheet("Data" + num);
        if (target != null) return target;
        return BOOK.cloneSheet(0, "Data" + num);
    }

    private void increaseTitle() {
        BOOK.sheetIterator().forEachRemaining(sheet -> {
            Row row = sheet.getRow(0);
            String[] titles = {"Cstart", "Cstop", "Gap"};
            int maxColumn = (int) ExcelUtils.getMaxColumn(sheet);
            int loc = start;

            while (loc < maxColumn) {
                ExcelUtils.setCellValue(row, loc, titles[(loc - start) % 3]);
                sheet.setColumnWidth(loc++, (loc - start) % 3 == 0 ? 10 * 256 : 30 * 256);
            }
        });
    }

    public void exportExcel(String path) {
        increaseTitle();
        ExcelUtils.exportExcel(path, BOOK);
    }
}
