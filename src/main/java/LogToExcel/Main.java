package LogToExcel;

import LogToExcel.Log.Entity.Demo3D;
import LogToExcel.Log.Entity.LogToExcel;
import LogToExcel.Log.Entity.Master;
import LogToExcel.Log.Entity.OPCUA;
import Utils.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    XSSFWorkbook book = new XSSFWorkbook();

    public void process(List<LogToExcel> list, String path) {
        for (int i = 0; i < list.size(); ++i) {
            list.get(i).setText(book,0,  (i % 2) * 5);
        }

        try (FileOutputStream out = new FileOutputStream(path + "\\log.xlsx")) {
            book.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path + "文件夹下日志处理完成");
    }

    public static void main(String[] args) {
        String path = FileUtils.getPath("输入日志所在的文件夹路径");
        List<LogToExcel> list = new ArrayList<>();

        for (File f : FileUtils.getAllDirectory(path)) {
            list.add(new Demo3D("PE20", f.getPath()));
            list.add(new Demo3D("PE21", f.getPath()));
            list.add(new OPCUA("PE20", f.getPath()));
            list.add(new OPCUA("PE21", f.getPath()));
            list.add(new Master("StatCSV", f.getPath()));

            new Main().process(list, f.getPath());
            list.clear();
        }
    }
}