package ICSHelper.CatalogEntity;

import Utils.StringUtils;
import Utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.NoSuchElementException;
import java.util.Objects;

public class STRAIGHT extends Catalog {
    private final static String BELTXMLNAME = "BELT_STRAIGHT.xml";
    private final static String TRAYXMLNAME = "TRAY_STRAIGHT.xml";
    private final static String TILTERPLUS = "Tilter_TilterPlus.xml";
    private final Document document;
    private final String fileName;

    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    public STRAIGHT(
            String xStart, String yStart, String zStart,
            String xEnd, String yEnd, String zEnd,
            String length, String width, String speed, String uniqueID, String catalog
    ) {
        StringUtils.requireNonNull(xStart, yStart, zStart, xEnd, yEnd, zEnd, length, width, uniqueID, catalog);
        //设置读取的XML类型
        document = XMLUtils.getDocumentFromPath(Catalog.XMLRESOURCE + "\\" + getCatalogXML(catalog));
        this.fileName = uniqueID + ".xml";

        //设置item_name,将文件名称也设置成item_name
        XMLUtils.findNodeAndSet(document, "item_name", uniqueID);
        XMLUtils.findNodeAndSet(document, "uniqueID", uniqueID);

        Node coordinates = XMLUtils.findNode(document, "coordinates");

        //设置Straight的起始点
        Node coordinate = coordinates.getChildNodes().item(1);
        XMLUtils.setCoordinate(coordinate, xStart, yStart, zStart);
        //设置Straight的终止点
        coordinate = coordinates.getChildNodes().item(3);
        XMLUtils.setCoordinate(coordinate, xEnd, yEnd, zEnd);

        //设置速度
        XMLUtils.findNodeAndSet(document, "speed", speed);
        //设置Straight的长度
        XMLUtils.findNodeAndSet(document, "length", length);
        //设置Straight的宽度
        XMLUtils.findNodeAndSet(document, "width", width);
    }

    private String getCatalogXML(String catalog) {
        return switch (catalog) {
            case "BELT_STRAIGHT" -> BELTXMLNAME;
            case "TRAY_STRAIGHT" -> TRAYXMLNAME;
            case "TRAY_TILTER_PLUS" -> TILTERPLUS;
            default -> throw new NoSuchElementException();
        };
    }
}
