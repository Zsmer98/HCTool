package LogToExcel.EntityBuild;

import LogToExcel.Log.Entity.Master;
import LogToExcel.Log.LogUtils;
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
            Row r = LogUtils.getRow(sheet, row - 1);
            for (int i = 0; i < 2; ++i) {
                LogUtils.createCellSetStyle(r, style, coltemp++)
                        .setCellValue(text.getList().get(i));
            }
            LogUtils.createCellSetStyle(r, style, coltemp++)
                    .setCellValue(Integer.parseInt(text.getList().get(2)));
            LogUtils.createCellSetStyle(r, style, coltemp++)
                    .setCellFormula(endcolumn + row + "-" + startcolumn + row);
            if (row != 3) {
                LogUtils.createCellSetStyle(r, style, coltemp)
                        .setCellFormula(startcolumn + row + "-" + endcolumn + (row - 1));
            }
            ++row;
            coltemp = column;
        }
    }
}