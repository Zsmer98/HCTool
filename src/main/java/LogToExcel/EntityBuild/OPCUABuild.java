package LogToExcel.EntityBuild;

import LogToExcel.Log.Entity.OPCUA;
import LogToExcel.Log.LogUtils;
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
            Row r = LogUtils.getRow(sheet, row - 1);
            for (String s : text.getList()) {
                LogUtils.createCellSetStyle(r, style, coltemp++)
                        .setCellValue(s);
            }
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
