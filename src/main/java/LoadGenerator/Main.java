package LoadGenerator;

import SqlGenerator.BarcodeSqlGenerator;
import Utils.CollectionUtils;
import Utils.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final int LINENUMBER = 4;//总共四条线路
    private static final int LOADS = 8000;//loads文件循环次数
    private final int pkgnumber;
    private final String path;
    private final List<List<LoadInfo>> lists = new ArrayList<>(LINENUMBER);//每条线路上Load的集合

    public Main(int number, String path) {
        this.path = path;
        this.pkgnumber = number;
        LoadFactory factory = new LoadFactory();
        for (int l = 0; l < Main.LINENUMBER; ++l) {
            List<LoadInfo> list = new LinkedList<>();
            for (int i = 0; i < number; ++i) {
                list.add(new LoadInfo(l + 1, String.format("%05d", i + 1), factory.getRandomLoad()));
            }
            lists.add(list);
        }
    }

    /**
     * loads文件创建，从LOADS和输入的pkgnumber取最大的数作为循环次数
     */
    public void createLoadTXT() {
        List<LoadInfo> list = new LinkedList<>();
        for (int i = 0; i < Main.LINENUMBER; i++) {
            for (int j = 0; j < Math.max(LOADS, pkgnumber); j++) {
                list.add(new LoadInfo(i + 1, String.format("%05d", j + 1), lists.get(i).get(j % pkgnumber).getLoad()));
            }
        }
        FileUtils.writeFile(path + "\\loads.txt", list);
    }

    public void createLoadExcel() {
        for (int i = 0; i < Main.LINENUMBER; ++i) {
            HSSFWorkbook book = new HSSFWorkbook();
            new LoadToExcel(lists.get(i)).setText(book, 0, 0);

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
        List<LoadInfo> loadInfos = CollectionUtils.releaseAll(lists);
        for (LoadInfo loadInfo : loadInfos) {
            list.add(new BarcodeSqlGenerator(
                    loadInfo.getLoad().getPN(),
                    loadInfo.getLoad().getColor().ordinal() + 1)
            );
        }

        FileUtils.writeFile(path + "\\BarcodeSQL.txt", list);
    }

    public static void main(String[] args) {
        int pkgnumber;

        System.out.println("请输入每条线上的包裹总数：");
        try (Scanner sc = new Scanner(System.in)) {
            pkgnumber = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("输入格式有误：请重新输入");
            return;
        }

        Main m = new Main(pkgnumber, "C:\\Users\\Zsm\\Desktop");
        m.createLoadTXT();
        m.createLoadExcel();
        m.createBarcodeSQL();
    }
}
