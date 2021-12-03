import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ReadFile {
    /*Get file path*/
    public static String getPath(String message) {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /*Read file and return List*/
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
}