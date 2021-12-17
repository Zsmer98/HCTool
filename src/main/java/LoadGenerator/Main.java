package LoadGenerator;

import SqlGenerator.BarcodeSqlGenerator;
import Utils.CollectionUtils;
import Utils.WriteFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final int LINENUMBER = 4;//总共四条线路
    private final String path;
    private final List<List<LoadInfo>> lists = new ArrayList<>();//每条线路上Load的集合

    public Main(int number, String path) {
        this.path = path;
        LoadFactory factory = new LoadFactory();
        for (int l = 0; l < LINENUMBER; ++l) {
            List<LoadInfo> list = new LinkedList<>();
            for (int i = 0; i < number; ++i) {
                list.add(new LoadInfo(l + 1, String.format("%05d", i + 1), factory.getRandomLoad()));
            }
            lists.add(list);
        }
    }

    public void createLoadTXT() {
        WriteFile.writeFile(path + "\\loads.txt", CollectionUtils.release(lists));
    }

    public void createLoadExcel() {
        for (int i = 0; i < LINENUMBER; ++i) {
            HSSFWorkbook book = new HSSFWorkbook();
            new LoadToExcel(lists.get(i)).setText(book, 0);

            try (FileOutputStream out = new FileOutputStream(path + "\\LoadData" + (i + 1) + ".xls")) {
                book.write(out);
                System.out.printf("The file located in the %s was created successfull\n",
                        path + "\\LoadData" + (i + 1) + ".xls");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createBarcodeSQL() {
        List<BarcodeSqlGenerator> list = new LinkedList<>();
        int i = 0;
        for (LoadInfo loadInfo : CollectionUtils.release(lists)) {
            list.add(new BarcodeSqlGenerator(
                    loadInfo.getLoad().getPN(),
                    loadInfo.getLoad().getColor().ordinal() + 1));
        }

        WriteFile.writeFile(path + "\\BarcodeSQL.txt", list);
    }

    public static void main(String[] args) {
        Main m;

        System.out.println("请输入每条线上的包裹总数：");
        try (Scanner sc = new Scanner(System.in);) {
            m = new Main(sc.nextInt(), "C:\\Users\\Zsm\\Desktop\\test");
        } catch (InputMismatchException e) {
            System.out.println("输入格式有误：请重新输入");
            return;
        }

        m.createLoadTXT();
        m.createLoadExcel();
        m.createBarcodeSQL();
    }
}
