package LogToExcel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.regex.Pattern;

class Demo3D extends Log implements LogToExcel<Demo3D> {
    public Demo3D(String title, String filepath ) {
        super(title, new String[]{"DStart", "DSstop", "包裹长度", "与上个包裹的间隔"}, filepath);
        super.setData("\\demo3dlog.txt");
    }

    @Override
    LogText getDataByRules(String s) {
        int temp = 1;
        LogText text = new LogText(super.getHeader().length);
        if (s.contains(super.getTitle())) {
            for (String number : s.split(",")) {
                if ((Pattern.compile("[0-9]*").matcher(number).matches()) && (--temp < 0)) {
                    text.addElement(number);
                }
            }
        }
        return text;
    }

    @Override
    public void setText(Sheet sheet, int column, CellStyle style) {
        int row = 3, coltemp = column;
        String startcolumn = String.valueOf((char) (column + 'A'));
        String endcolumn = String.valueOf((char) (column + 'A' + 1));
        for (LogText text : super.getList()) {

            Row r = ExcelUtil.getRow(sheet, row - 1);
            for(String s : text.getList()){
                ExcelUtil.setValue(r, style, coltemp++).setCellValue(Integer.parseInt(s));
            }
            ExcelUtil.setValue(r, style, coltemp++).setCellFormula(endcolumn + row + "-" + startcolumn + row);
            if(row != 3){
                ExcelUtil.setValue(r, style, coltemp).setCellFormula(endcolumn + row + "-" + startcolumn + (row - 1));
            }
            ++row;
            coltemp = column;
        }
    }
}