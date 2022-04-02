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
import java.util.stream.Collectors;

public class FindType {

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
        return "";
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\Zsm\\Desktop\\ICS\\data";
        FileUtils.getAllFile(path).stream()
                .map(File::getPath)
                .map(FindType::getType)
                .collect(Collectors.toSet())
                .forEach(System.out::println);

    }
}
