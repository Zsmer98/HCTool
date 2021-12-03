package LogToExcel;

import FileUtils.ReadFile;
import LogToExcel.HelpBuild.Demo3DBuild;
import LogToExcel.HelpBuild.MasterBuild;
import LogToExcel.HelpBuild.OPCUABuild;
import LogToExcel.Log.Entity.Demo3D;
import LogToExcel.Log.Entity.Master;
import LogToExcel.Log.Entity.OPCUA;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    XSSFWorkbook book = new XSSFWorkbook();

    public void process(String path) {
        //Demo3D的Log处理
        new Demo3DBuild().insertIntoExcel(book, "Demo3D", new Demo3D("PE20", path), 0);
        new Demo3DBuild().insertIntoExcel(book, "Demo3D", new Demo3D("PE21", path), 5);
        //OPCUA的Log处理
        new OPCUABuild().insertIntoExcel(book, "OPCUA", new OPCUA("PE20", path), 0);
        new OPCUABuild().insertIntoExcel(book, "OPCUA", new OPCUA("PE21", path), 5);
        //Master的Log处理
        new MasterBuild().insertIntoExcel(book, "Master", new Master("StatCSV", path), 0);

        try (FileOutputStream out = new FileOutputStream(path + "\\log.xlsx")) {
            System.out.println("write");
            book.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path + "文件夹下日志处理完成");
    }

    public static void main(String[] args) {
        String path = ReadFile.getPath("请输入你要处理的日志所在的文件夹");

        for (File f : ReadFile.getAllDirectory(path)) {
            new Main().process(f.getPath());
        }
    }
}
