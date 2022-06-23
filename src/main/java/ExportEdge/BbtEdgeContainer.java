package ExportEdge;

import Utils.ExcelUtils;
import Utils.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class BbtEdgeContainer {

    public static void readEdgeExcel() throws IOException {
        List<String> output = new LinkedList<>();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook("C:\\Users\\Zsm\\Desktop\\Edge.xlsx");
        xssfWorkbook.getSheet("Sheet1").iterator().forEachRemaining(row -> {
                    if (ExcelUtils.notBlankCell(row.getCell(1))) {
                        output.add(String.format("insert into scc.bbt_edge (id_origin, id_successor,connection_point_id) values ('%s','%s','2') on conflict (id_origin, id_successor) do nothing;", ExcelUtils.readCell(row.getCell(0)), ExcelUtils.readCell(row.getCell(1))));
                    }
                    if (ExcelUtils.notBlankCell(row.getCell(2))) {
                        output.add(String.format("insert into scc.bbt_edge (id_origin, id_successor,connection_point_id) values ('%s','%s','3') on conflict (id_origin, id_successor) do nothing;", ExcelUtils.readCell(row.getCell(0)), ExcelUtils.readCell(row.getCell(2))));
                    }
                    if (ExcelUtils.notBlankCell(row.getCell(3))) {
                        output.add(String.format("insert into scc.bbt_edge (id_origin, id_successor,connection_point_id) values ('%s','%s','4') on conflict (id_origin, id_successor) do nothing;", ExcelUtils.readCell(row.getCell(0)), ExcelUtils.readCell(row.getCell(3))));
                    }
                }
        );

        FileUtils.writeFile("C:\\Users\\Zsm\\Desktop\\Edge2022-6-20.sql", output);
    }

    public static void replaceDB() throws IOException {
        List<String> res = new LinkedList<>();
        Sheet source = new XSSFWorkbook("C:\\Users\\Zsm\\Desktop\\source.xlsx").getSheet("Sheet1");
        Sheet target = new XSSFWorkbook("C:\\Users\\Zsm\\Desktop\\target.xlsx").getSheet("Sheet1");
        source.iterator().forEachRemaining(r -> {
                    String v1 = ExcelUtils.readCell(r.getCell(0));
                    String v2 = ExcelUtils.readCell(target.getRow(r.getRowNum()).getCell(0));
                    if (!v1.equals(v2)) {
                        res.add("update scc.bbt_edge set id_successor = replace(id_successor, '" + v1 + "', '" + v2 + "');");
                        res.add("update scc.bbt_edge set id_origin = replace(id_origin, '" + v1 + "', '" + v2 + "');");
                    }
                }
        );

        FileUtils.writeFile("C:\\Users\\Zsm\\Desktop\\resetValue.sql", res);
    }

    public static void main(String[] args) throws IOException {
        replaceDB();
    }
}
