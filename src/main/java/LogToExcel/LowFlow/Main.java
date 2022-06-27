package LogToExcel.LowFlow;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final String EXCEL_MODEL_PATH = "src\\Files\\ExcelModel\\LowFlowModel.xlsx";
    private static final String DATA_PATH = "C:\\Users\\Zsm\\Desktop\\opcua-logger.log";
    private static final String EXPORT_PATH = "C:\\Users\\Zsm\\Desktop\\data.xlsx";

    public static void main(String[] args) throws InterruptedException {
        long t1 = System.nanoTime();
        ExportPE export = new ExportPE();

        new ReadPE(DATA_PATH).peMap.values().forEach(export::processPE);
        export.exportExcel(EXPORT_PATH);

        System.out.println((System.nanoTime() - t1) / 1000000000);

    }
}
