package LogToExcel.EntityBuild;

import LogToExcel.Log.Entity.Master;
import LogToExcel.Log.ExcelUtil;
import LogToExcel.Log.LogText;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class MasterBuild implements LogToExcel<Master> {
    @Override
    public void setText(Master master, Sheet sheet, int column, CellStyle style) {
        int row = 3, coltemp = column;
        String startcolumn = String.valueOf((char) (column + 'A'));
        String endcolumn = String.valueOf((char) (column + 'A' + 1));
        for (LogText text : master.getList()) {
            Row r = ExcelUtil.getRow(sheet, row - 1);
            for (String s : text.getList()) {
                ExcelUtil.createCellSetStyle(r, style, coltemp++)
                        .setCellValue(Integer.parseInt(s));
            }
            ExcelUtil.createCellSetStyle(r, style, coltemp++)
                    .setCellFormula(endcolumn + row + "-" + startcolumn + row);
            if (row != 3) {
                ExcelUtil.createCellSetStyle(r, style, coltemp)
                        .setCellFormula(startcolumn + row + "-" + endcolumn + (row - 1));
            }
            ++row;
            coltemp = column;
        }
    }
}
