package FindXML;

import Utils.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FindType {
    private static final String PATH = "C:\\Users\\Zsm\\Desktop\\ICS\\data";
    //按照conv_type将文件分类，Key是conv_type，Value是类型为conv_type的XML文件路径
    private static final Map<String, List<String>> MAP;

    static {
        MAP = FileUtils.getAllFile(PATH).stream()
                .map(File::getPath)
                .filter(file -> Objects.nonNull(getType(file)))
                .collect(Collectors.toMap(
                        FindType::getType,
                        file -> {
                            List<String> list = new LinkedList<>();
                            list.add(file);
                            return list;
                        },
                        (oldV, newV) -> {
                            oldV.addAll(newV);
                            return oldV;
                        }));
    }


    public static String getType(String path) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // 创建一个DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 使用parse方法解析xml文件
            Document document = db.parse(path);
            NodeList nl = document.getElementsByTagName("OBJECT");
            return nl.item(0).getChildNodes().item(3).getChildNodes().item(11).getChildNodes().item(0).getNodeValue();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        long t1 = System.nanoTime();
        MAP.get("Straight").forEach(System.out::println);
        System.out.println((System.nanoTime() - t1) / 1000000);
    }
}
