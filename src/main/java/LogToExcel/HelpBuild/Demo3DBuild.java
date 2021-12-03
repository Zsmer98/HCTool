package LogToExcel.HelpBuild;

import LogToExcel.Log.Entity.Demo3D;
import LogToExcel.Log.ExcelUtil;
import LogToExcel.Log.LogText;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Demo3DBuild implements LogToExcel<Demo3D> {
    @Override
    public void setText(Demo3D demo3D, Sheet sheet, int column, CellStyle style) {
        int row = 3, coltemp = column;
        String startcolumn = String.valueOf((char) (column + 'A'));
        String endcolumn = String.valueOf((char) (column + 'A' + 1));
        for (LogText text : demo3D.getList()) {
            Row r = ExcelUtil.getRow(sheet, row - 1);
            for (String s : text.getList()) {
                ExcelUtil.setValue(r, style, coltemp++).setCellValue(Integer.parseInt(s));
            }
            ExcelUtil.setValue(r, style, coltemp++).setCellFormula(endcolumn + row + "-" + startcolumn + row);
            if (row != 3) {
                ExcelUtil.setValue(r, style, coltemp).setCellFormula(startcolumn + row + "-" + endcolumn + (row - 1));
            }
            ++row;
            coltemp = column;
        }
    }
}