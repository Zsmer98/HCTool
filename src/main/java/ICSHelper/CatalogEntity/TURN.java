package ICSHelper.CatalogEntity;

import Utils.StringUtils;
import Utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.NoSuchElementException;

public class TURN extends Catalog {
    private final static String TRAYTURNXMLNAME = "TRAY_TURN.xml";
    private final static String BELTTURNXMLNAME = "BELT_TURN.xml";
    private final Document document;
    private final String fileName;

    public TURN(
            String xStart, String yStart, String zStart,
            String xEnd, String yEnd, String zEnd,
            String radius, String angle, String width,  String speed,String uniqueID, String catalog
    ) {
        StringUtils.requireNonEmpty(xStart, yStart, zStart, xEnd, yEnd, zEnd, radius, angle, width, uniqueID, catalog);
        //设置读取的XML类型
        document = XMLUtils.getDocumentFromPath(Catalog.XMLRESOURCE + "\\" + getCatalogXML(catalog));
        this.fileName = uniqueID + ".xml";

        //设置item_name,将文件名称也设置成item_name
        XMLUtils.findNodeAndSet(document, "item_name", uniqueID);
        XMLUtils.findNodeAndSet(document, "uniqueID", uniqueID);


        Node coordinates = XMLUtils.findNode(document, "coordinates");

        //设置turn的起始点
        Node coordinate = coordinates.getChildNodes().item(1);
        XMLUtils.setCoordinate(coordinate, xStart, yStart, zStart);
        //设置turn的终止点
        coordinate = coordinates.getChildNodes().item(3);
        XMLUtils.setCoordinate(coordinate, xEnd, yEnd, zEnd);

        //设置速度
        XMLUtils.findNodeAndSet(document, "speed", speed);
        //设置turn的转弯半径
        XMLUtils.findNodeAndSet(document, "curve_radius", radius);
        //设置turn的转弯角度
        XMLUtils.findNodeAndSet(document, "curve_angle", angle);
        //设置turn的宽度
        XMLUtils.findNodeAndSet(document, "width", width);
    }

    @Override
    public Document getDocument() {
        return this.document;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    private String getCatalogXML(String catalog) {
        return switch (catalog) {
            case "BELT_TURN" -> BELTTURNXMLNAME;
            case "TRAY_TURN" -> TRAYTURNXMLNAME;
            default -> throw new NoSuchElementException(catalog + " didn't found");
        };
    }
}
