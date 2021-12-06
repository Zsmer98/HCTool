package LogToExcel;

import FileUtils.ReadFile;
import LogToExcel.EntityBuild.Demo3DBuild;
import LogToExcel.EntityBuild.MasterBuild;
import LogToExcel.EntityBuild.OPCUABuild;
import LogToExcel.Log.Entity.Demo3D;
import LogToExcel.Log.Entity.Master;
import LogToExcel.Log.Entity.OPCUA;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            book.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path + "文件夹下日志处理完成");
    }

    public static void main(String[] args) throws InterruptedException {
        String path = "C:\\Users\\Zsm\\Desktop\\Log";

        List<Thread> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (File f : ReadFile.getAllDirectory(path)) {
            Thread thread = new Thread(() -> new Main().process(f.getPath()));
            list.add(thread);
            thread.start();
            //new Main().process(f.getPath());
        }

        for(Thread thread : list){
            thread.join();
        }
        System.out.println((System.currentTimeMillis() - start) / 1000 + "seconds");
    }
}