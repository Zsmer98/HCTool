package LogToExcel.Log.Entity;

import Utils.ExcelUtils;
import Utils.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 2021年12月22日办公室环线日志输出
 */
public class LoopLineOPCUA {

    public void setHeader(Workbook book, int row, int column, String[][] header) {
        Sheet sheet = ExcelUtils.getSheet(book, "OPCUA");
        CellStyle style = book.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        int col = column;
        for (int i = 0; i < 2; i++) {
            sheet.setColumnWidth(col++, 20 * 256);
        }

        for (String[] strings : header) {
            col = column;
            for (String string : strings) {
                ExcelUtils.createCellSetStyle(ExcelUtils.getRow(sheet, row), style, col++)
                        .setCellValue(string);
            }
            ++row;
        }
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\Zsm\\Desktop";
        XSSFWorkbook book = new XSSFWorkbook();

        String[][] header = {
                {"时长", "20min"},
                {"包裹大小", "50×30×15"},
                {"包裹数量", "3"},
                {"是否移动", "否"}
        };
        new LoopLineOPCUA().setHeader(book, 0, 10, header);

        new OPCUA("PE01", path, "spring-boot-logger.log").setText(book, 0, 0);
        new OPCUA("PE02", path, "spring-boot-logger.log").setText(book, 0, 5);

        String newname = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date());
        FileUtils.exportExcel(book, path + "\\" + newname + "log.xlsx");

        File file = new File(path + "\\spring-boot-logger.log");
        if(file.renameTo(new File(path + "\\" + newname + "log.log"))){
            System.out.println("Log文件改名成功");
        }
    }
}
