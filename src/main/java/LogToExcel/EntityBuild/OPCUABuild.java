package LogToExcel.EntityBuild;

import LogToExcel.Log.Entity.OPCUA;
import LogToExcel.Log.ExcelUtil;
import LogToExcel.Log.LogText;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class OPCUABuild implements LogToExcel<OPCUA> {
    @Override
    public void setText(OPCUA opcua, Sheet sheet, int column, CellStyle style) {
        int row = 3, coltemp = column;
        String startcolumn = String.valueOf((char) (column + 'A'));
        String endcolumn = String.valueOf((char) (column + 'A' + 1));
        for (LogText text : opcua.getList()) {
            Row r = ExcelUtil.getRow(sheet, row - 1);
            for (String s : text.getList()) {
                ExcelUtil.createCellSetStyle(r, style, coltemp++).setCellValue(s);
            }
            ExcelUtil.createCellSetStyle(r, style, coltemp++)
                    .setCellFormula("(" + endcolumn + row + "-" + startcolumn + row + ")/10");
            if (row != 3) {
                ExcelUtil.createCellSetStyle(r, style, coltemp)
                        .setCellFormula("(" + startcolumn + row + "-" + endcolumn + (row - 1) + ")/10");
            }
            ++row;
            coltemp = column;
        }
    }
}
