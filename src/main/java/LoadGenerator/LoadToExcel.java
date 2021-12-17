package LoadGenerator;

import LogToExcel.Log.Entity.LogToExcel;
import Utils.ExcelUtil;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

public class LoadToExcel implements LogToExcel {
    private final List<LoadInfo> list;
    /**
     * Demand 是PN码
     */
    private final String[] header = {"Name", "Width", "Height", "Depth", "RelRotX", "RelRotY", "RelRotZ", "Color", "ID", "Demand"};

    public LoadToExcel(List<LoadInfo> list){
        this.list = list;
    }

    @Override
    public void setText(Workbook book, int column) {
        Sheet sheet = ExcelUtil.getSheet(book, "sheet1");
        //所有表格的数据均设置成上下居中，左右居中
        CellStyle style = book.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置列表标题
        int row = 0, col = column;
        for (String head : header) {
            sheet.setColumnWidth(col, 20 * 256);
            ExcelUtil.createCellSetStyle(ExcelUtil.getRow(sheet, 0), style, col++)
                    .setCellValue(head);
        }

        if(list.size() == 0) return;
        //写入Load信息
        for(LoadInfo loadInfo : list){
            Row r = ExcelUtil.getRow(sheet, ++row);

            ExcelUtil.createCellSetStyle(r, style, 0).setCellValue(loadInfo.getLoad().getColor().ordinal() + 1);
            ExcelUtil.createCellSetStyle(r, style, 1).setCellValue(loadInfo.getLoad().getWidth() / 1000);
            ExcelUtil.createCellSetStyle(r, style, 2).setCellValue(loadInfo.getLoad().getDepth() / 1000);
            ExcelUtil.createCellSetStyle(r, style, 3).setCellValue(loadInfo.getLoad().getLength() / 1000);
            ExcelUtil.createCellSetStyle(r, style, 4).setCellValue(0);
            ExcelUtil.createCellSetStyle(r, style, 5).setCellValue(0);
            ExcelUtil.createCellSetStyle(r, style, 6).setCellValue(0);
            ExcelUtil.createCellSetStyle(r, style, 7).setCellValue(loadInfo.getLoad().getColor().toString());
            ExcelUtil.createCellSetStyle(r, style, 8).setCellValue(loadInfo.getLoad().getColor().ordinal() + 1);
            ExcelUtil.createCellSetStyle(r, style, 9).setCellValue(loadInfo.getLoad().getPN());
        }
    }
}
