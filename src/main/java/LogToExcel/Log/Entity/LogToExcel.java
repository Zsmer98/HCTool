package LogToExcel.Log.Entity;

import org.apache.poi.ss.usermodel.*;

public interface LogToExcel {
    /**
     * 设置插入工作表的文本
     * @param book 需要插入的工作表
     * @param column 插入的列号
     */
    void setText(Workbook book, int column);
}
