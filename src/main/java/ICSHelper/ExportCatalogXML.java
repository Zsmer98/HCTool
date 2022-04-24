package ICSHelper;

import ICSHelper.CatalogEntity.CatalogFactory;
import ICSHelper.CatalogEntity.Catalog;
import Utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.StreamSupport;

public class ExportCatalogXML {
    private static void foreach(Iterator<Row> iterator) {
        StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .map(ExcelUtils::rowToMap)
                .map(CatalogFactory::getCatalogFromExcel)
                .filter(Objects::nonNull)
                .forEach(Catalog::export);
    }


    public static void main(String[] args) {
        try (FileInputStream file = new FileInputStream(".\\src\\Files\\ICS测试环 allocationmap_V0.5.xlsx")) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);

            foreach(xssfWorkbook.getSheet("LC01").iterator());
            foreach(xssfWorkbook.getSheet("LC02").iterator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
