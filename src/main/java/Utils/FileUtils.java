package Utils;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public final class FileUtils {
    /*从控制台读取输入，仅限一行输入*/
    public static String getPath(String message) {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /*读取文件，并将每一行读取成String返回*/
    public static List<String> readFile(String pathname) throws FileNotFoundException {
        List<String> list = new LinkedList<>();
        File file = new File(pathname);

        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                list.add(s.nextLine());
            }
        }
        return list;
    }

    /**
     * 读取输入的路径下所有最后一级的文件夹(该文件夹下不包含其他文件夹)
     *
     * @param path 文件路径
     * @return 返回所有最后一级文件夹
     */
    public static List<File> getAllDirectory(String path) {
        List<File> list = new ArrayList<>();
        findDirectory(new File(path), list);
        return list;
    }

    /**
     * 深度搜索最后一级文件夹
     *
     * @param f    当前文件夹路径
     * @param list 存储文件夹列表
     */
    public static void findDirectory(File f, List<File> list) {
        if (f.isDirectory()) {
            File[] fs = f.listFiles();
            if (fs != null && fs[0].isDirectory()) {
                for (File file : fs) {
                    findDirectory(file, list);
                }
            } else {
                list.add(f);
            }
        }
    }

    public static List<File> getAllFile(String directory) {
        return CollectionUtils.releaseAll(
                FileUtils.getAllDirectory(directory)
                        .stream().map(f ->
                                Arrays.stream(Objects.requireNonNull(f.listFiles()))
                                        .collect(Collectors.toList())
                        )
                        .collect(Collectors.toList())
        );
    }

    /**
     * 将list写入文件
     *
     * @param newfilename 文件路径
     * @param Rows        被写入的类的list
     * @param <T>         调用T的toString方法
     */
    public static <T> void writeFile(String newfilename, List<T> Rows) {
        if (Rows == null) return;

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newfilename))) {
            for (T t : Rows) {
                bufferedWriter.write(t.toString() + "\n");
            }

            System.out.printf("The file located in the %s was created successful\n", newfilename);
        } catch (IOException e) {
            System.out.println("Write failed. Please try again");
            e.printStackTrace();
        }
    }


    /**
     * 导出Excel至指定路径
     */
    public static void exportExcel(Workbook workbook, String exportPath) {
        try (FileOutputStream out = new FileOutputStream(exportPath)) {
            workbook.write(out);
            System.out.println("Excel文件写入成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
