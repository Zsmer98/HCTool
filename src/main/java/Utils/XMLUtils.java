package Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public final class XMLUtils {
    public static Document getDocumentFromPath(String path) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // 创建一个DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 使用parse方法解析xml文件
            return db.parse(path);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void exportXML(Document document, String path) {
        try {
            Objects.requireNonNull(path);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");    //字符编码
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");        //是否换行
            System.out.println(path + " export start");
            transformer.transform(new DOMSource(document), new StreamResult(new File(path)));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void setCoordinate(Node node, String x, String y, String z) {
        node.getChildNodes().item(5).getChildNodes().item(0).setNodeValue(x);
        node.getChildNodes().item(7).getChildNodes().item(0).setNodeValue(y);
        node.getChildNodes().item(9).getChildNodes().item(0).setNodeValue(z);
    }


    public static Node findNode(Node rootNode, String name) {
        if (rootNode.getNodeName().equals(name)) return rootNode;

        NodeList root = rootNode.getChildNodes();
        for (int i = 0; i < root.getLength(); ++i) {
            Node node = root.item(i);
            if (node.getNodeName().equals(name)) return node;
            Node node1 = findNode(node, name);
            if (Objects.nonNull(node1)) return node1;
        }
        return null;
    }

    public static void findNodeAndSet(Node root, String name, String value) {
        Node node = findNode(root, name);
        if (node != null) {
            node.getChildNodes().item(0).setNodeValue(value);
        } else {
            throw new NoSuchElementException(root.getNodeName() + "下没有" + name + "节点");
        }
    }

    /**
     * 广度优先搜索节点,与{@link #findNodeAndSet}不同的是findNodeAndSet是深度优先搜索节点并设置值
     */
    public static void DFSfindAndSet(Node root, String name, String value) {
        Node node = CollectionUtils.DFS(root,
                n -> {
                    List<Node> list = new LinkedList<>();
                    for (int i = 0; i < n.getChildNodes().getLength(); ++i) {
                        list.add(n.getChildNodes().item(i));
                    }
                    return list;
                },
                n -> n.getNodeName().equals(name)
        );

        if (node == null) throw new NoSuchElementException();
        node.getChildNodes().item(0).setNodeValue(value);
    }
}
