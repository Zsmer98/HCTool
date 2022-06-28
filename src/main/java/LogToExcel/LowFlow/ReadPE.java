package LogToExcel.LowFlow;

import Utils.ExcelUtils;
import Utils.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ReadPE {
    private Sheet SOURCE;
    private static final int PE_LOC = 1;
    public final Map<String, PE> peMap = new HashMap<>();

    public void setPeMap() {
        for (Row row : SOURCE) {
            String peName = ExcelUtils.readCell(row.getCell(PE_LOC));
            if (!peName.isEmpty()) {
                peMap.put(peName, new PE(peName));
            }
        }
    }

    public void readData(String datapath) throws FileNotFoundException {
        FileUtils.readFile(datapath)
                .stream()
                .filter(s -> s.contains("PE"))
                .forEach(s -> {
                    String[] strings = s.split(",");
                    String name = strings[0].substring(s.indexOf("P"));
                    PE pe = peMap.get(name);
                    if (pe != null) {
                        pe.add(strings[2], strings[4]);
                    }
                });
    }

    public ReadPE(String datapath) {
        try {
            SOURCE = new XSSFWorkbook(Main.EXCEL_MODEL_PATH).getSheet("source");
            setPeMap();
            readData(datapath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
