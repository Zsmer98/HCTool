package LogToExcel.LowFlow;

import Utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class ProcessModel {
    public static void main(String[] args) throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook("C:\\Users\\Zsm\\Desktop\\fast.xlsx");
        Sheet sheet = xssfWorkbook.getSheet("sheet1");

        for (int i = 0; i < 15000; i++) {
            if (i % 100 == 0) System.out.println(i);
            sheet.setColumnWidth(i, 30 * 256);
        }

        ExcelUtils.exportExcel("C:\\Users\\Zsm\\Desktop\\newModel.xlsx", xssfWorkbook);
    }
}
