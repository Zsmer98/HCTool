package ICSHelper;

import ICSHelper.CatalogEntity.STRAIGHT;
import ICSHelper.CatalogEntity.Catalog;
import ICSHelper.CatalogEntity.TURN;
import Utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExportCatalogXML {
    Set<String> title = Set.of(
            "设备编号",
            "Catalog",
            "有效宽度mm",
            "转弯半径",
            "Angle",
            "X.Start",
            "Y.Start",
            "X.End",
            "Y.End",
            "Z.Start",
            "Z.End"
    );
    Map<String, Integer> map = new HashMap<>();

    Iterator<Row> iterator;

    public ExportCatalogXML(Iterator<Row> iterator) {
        this.iterator = iterator;
        init();
    }

    private void init() {
        Row firstRow = iterator.next();
        title.forEach(key -> map.put(key, ExcelUtils.getIndexWithSpecifiedName(firstRow, key)));
    }

    private void foreach() {
        List<Catalog> list = new LinkedList<>();
        while (iterator.hasNext()) {
            Row row = iterator.next();

            System.out.println("---------------" + readMap(row, "设备编号") + " start" + "---------------");

            //读取Catalog类型
            String catalog = Catalog.getCatalogType(ExcelUtils.readCell(row.getCell(map.get("Catalog"))));
            Catalog c = null;

            //判定是否为Straight类型
            if (Objects.equals(catalog, "STRAIGHT")) {
                c = new STRAIGHT(
                        readMap(row, "X.Start"), readMap(row, "Y.Start"), readMap(row, "Z.Start"),
                        readMap(row, "X.End"), readMap(row, "Y.End"), readMap(row, "Z.End"),
                        "0", readMap(row, "有效宽度mm"), readMap(row, "设备编号"), ExcelUtils.readCell(row.getCell(map.get("Catalog")))
                );
            }
            //判定是否为Turn类型
            if (Objects.equals(catalog, "TURN")) {
                c = new TURN(
                        readMap(row, "X.Start"), readMap(row, "Y.Start"), readMap(row, "Z.Start"),
                        readMap(row, "X.End"), readMap(row, "Y.End"), readMap(row, "Z.End"),
                        readMap(row, "转弯半径"), readMap(row, "Angle"), readMap(row, "有效宽度mm"), readMap(row, "设备编号")
                );
            }

            list.add(c);
            System.out.println("---------------" + readMap(row, "设备编号") + "  end " + "---------------");
        }

        list.stream().filter(Objects::nonNull).forEach(Catalog::export);
    }

    private String readMap(Row row, String key) {
        return ExcelUtils.readCell(row.getCell(map.get(key)));
    }

    public static void main(String[] args) {
        try (FileInputStream file = new FileInputStream("C:\\Users\\Zsm\\Desktop\\ICS测试环 allocationmap_V0.3.xlsx")) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);

            ExportCatalogXML export = new ExportCatalogXML(xssfWorkbook.getSheet("LC02").iterator());
            export.foreach();

            export = new ExportCatalogXML(xssfWorkbook.getSheet("LC01").iterator());
            export.foreach();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
