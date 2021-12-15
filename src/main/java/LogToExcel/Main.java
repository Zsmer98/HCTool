package LogToExcel;

import LogToExcel.Log.Entity.Demo3D;
import LogToExcel.Log.Entity.Master;
import LogToExcel.Log.Entity.OPCUA;
import Utils.ReadFile;
import org.apache.batik.ext.awt.geom.RectListManager;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    XSSFWorkbook book = new XSSFWorkbook();

    public void process(String path) {
        //Demo3D的Log处理
        new Demo3D("PE20", path).setText(book, 0);
        new Demo3D("PE21", path).setText(book, 5);
        //OPCUA的Log处理
        new OPCUA("PE20", path).setText(book, 0);
        new OPCUA("PE21", path).setText(book, 5);
        //Master的Log处理
        new Master("StatCSV", path).setText(book, 0);

        try (FileOutputStream out = new FileOutputStream(path + "\\log.xlsx")) {
            book.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path + "文件夹下日志处理完成");
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\Zsm\\Desktop\\2021-12-08\\14.17.57 Test1";

        for(File f : ReadFile.getAllDirectory(path)){
            new Main().process(f.getPath());
        }
    }
}