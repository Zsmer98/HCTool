package FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ReadFile {
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
}