package ICSHelper.CatalogEntity;

import Utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class TURN extends Catalog {
    private final static String TURNXMLNAME = "TRAY_TURN.xml";
    private final Document document;
    private final String fileName;

    public TURN(
            String xStart, String yStart, String zStart,
            String xEnd, String yEnd, String zEnd,
            String radius, String angle, String width, String uniqueID
    ) {
        //设置读取的XML类型
        document = XMLUtils.getDocumentFromPath(Catalog.XMLRESOURCE + "\\" + TURNXMLNAME);
        this.fileName = uniqueID + ".xml";

        //设置item_name,将文件名称也设置成item_name
        XMLUtils.findNodeAndSet(document, "item_name", uniqueID);

        Node coordinates = XMLUtils.findNode(document, "coordinates");

        //设置turn的起始点
        Node coordinate = coordinates.getChildNodes().item(1);
        XMLUtils.setCoordinate(coordinate, xStart, yStart, zStart);
        //设置turn的终止点
        coordinate = coordinates.getChildNodes().item(3);
        XMLUtils.setCoordinate(coordinate, xEnd, yEnd, zEnd);
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
}
