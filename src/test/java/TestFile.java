import ICSHelper.CatalogEntity.Catalog;
import ICSHelper.CatalogEntity.STRAIGHT;
import ICSHelper.ExportCatalogXML;
import LoadGenerator.Color;
import LoadGenerator.Load;
import Utils.CollectionUtils;
import Utils.ExcelUtils;
import Utils.XMLUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class TestFile {

    @Test
    public void test() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println(ft.format(dNow));
    }

    @Test
    public void test1() {
        Random random = new Random();
        long f7 = random.nextLong((long) Math.pow(10, 13));
        System.out.println(f7);
        System.out.println(Math.floorMod(f7, 2));
    }

    @Test
    public void testCUtils() {
        Load l = new Load(2, 1, 1, 1);
        l.setPNColor();
        System.out.println(l.getColor());
        Load clone = l.clone();
        System.out.println(clone.getColor());
    }

    @Test
    public void testStream() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        final AtomicInteger j = new AtomicInteger();
        list.stream()
                .peek(i -> System.out.println(i + " " + (j.incrementAndGet())))
                .forEach(i -> System.out.println("ss"));
    }

    @Test
    public void testFile() {
        System.out.println(Arrays.toString(getBinary(16777472, 32)));
    }

    @Test
    public void testCol() {
        List<String> l2 = List.of("s", "d");
        List<Integer> l1 = List.of(1, 2, 3, 4, 5);
        List<Integer> l = CollectionUtils.releaseAll(List.of(1, 2, 3, 4, l1));
        l.forEach(System.out::print);
    }

    public static int[] getBinary(long input, int length) throws NumberFormatException {
        if (input < 0 || input > ((1L << length) - 1))
            throw new NumberFormatException();

        int[] result = new int[length];
        for (int i = 0; i < length; ++i) {
            result[i] = ((input & (1L << i)) == 0 ? 0 : 1);
        }
        return result;
    }

    @Test
    public void joining() throws Exception {
        List<String> list = List.of("a", "b", "c");
        System.out.println(String.join(",", list));
        System.out.println(list.stream().collect(Collectors.joining(",")));
        throw new Exception();
    }

    @Test
    public void genSQL() {
        List<String> nodeUnitList = Arrays.asList(
                "DS01.BC1002", "DS01.BC1004", "DS01.BV1006", "DS01.BC1008",
                "DS01.BC1010", "DS01.DV1012", "DS01.BC1014", "DS01.BV1016"
        );

        //nodeUnitList.forEach(node -> System.out.println("INSERT INTO scc.cau_all_unit VALUES ('101','2" + node.subSequence(7, 11) + "','" + node + "','406')"));
        nodeUnitList.forEach(node -> System.out.println("INSERT INTO scc.plc_unit_info (" +
                "unit_id, plc_node_id, unit_alarm_message_start_address, unit_name, unit_status_start_address, unit_type_id) VALUES " +
                "('2" + node.subSequence(7, 11) + "','101', '" + node + ".Signals', '" + node + "', '" + node + ".Status', '406')"));
    }

    @Test
    public void testWrite() {
        int loop = 100000;
        int max = 1000;
        String newfilename = "C:\\Users\\Zsm\\Desktop\\test.csv";
        List<String> res = new LinkedList<>();
        while (loop-- > 0) {
            Random r = new Random();
            List<Integer> list = List.of(r.nextInt(max), r.nextInt(max), r.nextInt(max), r.nextInt(max), r.nextInt(max), r.nextInt(max));
            res.add(list.stream().map(Object::toString).collect(Collectors.joining(",")));
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newfilename), 1);
            for (String s : res) {
                bufferedWriter.write(s + "\n");
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public int getStatus(String oldBits) {
        int[] bits = stringToBinary(oldBits, 32);

        if (checkArrayValue(bits, 0)) {
            return 3;
        } else if (checkArrayValue(bits, 1, 4, 22, 27)) {
            return 4;
        } else if (checkArrayValue(bits, 2)) {
            return 5;
        } else if (checkArrayValue(bits, 24, 25, 26)) {
            return 6;
        } else if (checkArrayValue(bits, 7)) {
            return 7;
        } else if (checkArrayValue(bits, 5)) {
            return 9;
        } else if (checkArrayValue(bits, 6)) {
            return 10;
        } else if (checkArrayValue(bits, 8)) {
            return 11;
        } else if (checkArrayValue(bits, 30)) {
            return 12;
        } else {
            return 1;
        }
    }

    public static int[] stringToBinary(String string, int length) {
        return string == null || string.isEmpty() ?
                new int[length] :
                getBinary(Long.parseLong(string), length);
    }


    public static boolean checkArrayValue(int[] array, int... locations) {
        for (int loc : locations) {
            if (loc < 0 || loc > array.length - 1)
                throw new IndexOutOfBoundsException("输入的数组下标存在越界");
            if (array[loc] != 1) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void t32() {
        System.out.println(getStatus("128"));
    }


    @Test
    public void TestPN2() {
        Random random = new Random();
        long PN = random.nextLong((long) Math.pow(10, 10));
        System.out.println(PN);
        Color color = Color.values()[Math.floorMod(PN, Color.values().length)];
        PN += (color.ordinal() + 191) * ((long) Math.pow(10, 10));
        System.out.println(PN);
    }

    @Test
    public void testGetList() {
        Integer i = 1;
        String s = "s";
        List<Object> res = getList(i, s);
    }

    private static <T> List<T> getList(T e1, T e2) {
        List<T> res = new ArrayList<>();
        res.add(e1);
        res.add(e2);
        return res;
    }

    @Test
    public void TestCollectionutils() {
        String s = "abc";
        s.chars().forEach(System.out::println);
        System.out.println(97 == 'a');
    }

    @Test
    public void testXML() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // 创建一个DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 使用parse方法解析xml文件
            Document document = db.parse("C:\\Users\\Zsm\\Desktop\\SP211152866.xml");
            NodeList nl = document.getElementsByTagName("OBJECT");
            System.out.println(nl.item(0).getChildNodes().item(3).getChildNodes().item(11).getChildNodes().item(0).getNodeValue());
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadCell() {
        List<String> list = List.of("1", "2", "TRUE", "test");

        get(list, "1");
        get(list, "2");
        get(list, 2);
        get(list, 3);
        get(list, 2.0D);
        get(list, true);
        get(list, false);
    }

    public static <T> void get(List<String> row, T name) {
        for (String cell : row) {
            if (Objects.equals(rreadCell(cell), name)) System.out.println(name);
        }
        System.out.println("not found");
    }

    @SuppressWarnings("unchecked")
    public static <T> T rreadCell(String cell) {
        switch (cell) {
            case "1" -> {
                return (T) "1";
            }
            case "2" -> {
                return (T) Integer.valueOf(2);
            }
            case "TRUE" -> {
                return (T) Boolean.TRUE;
            }
        }
        return null;
    }

    @Test
    public void testExcelICS() {
        try (FileInputStream file = new FileInputStream("C:\\Users\\Zsm\\Desktop\\ICS测试环 allocationmap_V0.4.xlsx")) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
            Map<String, String> map = ExcelUtils.rowToMap(xssfWorkbook.getSheet("LC02").getRow(2));
            map.forEach((k, v) -> System.out.println("k for: " + k + ", v for " + v));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDFS() {
        Document node = XMLUtils.getDocumentFromPath("C:\\Users\\Zsm\\Desktop\\TRAY_STRAIGHT.xml");
        XMLUtils.DFSfindAndSet(node, "uniqueID", "newID");
        XMLUtils.exportXML(node,"C:\\Users\\Zsm\\Desktop\\nwe.xml");
    }
}
