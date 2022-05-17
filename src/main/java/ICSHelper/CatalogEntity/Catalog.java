package ICSHelper.CatalogEntity;

import Utils.XMLUtils;
import org.w3c.dom.Document;

import java.util.Objects;

public abstract class Catalog {
    public static final String XML_RESOURCE_PATH = ".\\src\\Files\\XML";
    public static final String EXPORT_PATH = "C:\\test\\data\\test";

    public abstract Document getDocument();

    public abstract String getFileName();

    public void export() {
        XMLUtils.exportXML(getDocument(), EXPORT_PATH + "\\" + getFileName());
        System.out.println(getFileName() + " export complete");
    }

    public static String getCatalogType(String catalog) {
        if (Objects.isNull(catalog)) return null;
        if (catalog.equals("BELT_STRAIGHT") || catalog.equals("TRAY_STRAIGHT") || catalog.equals("TRAY_TILTER_PLUS"))
            return "STRAIGHT";
        if (catalog.equals("TRAY_TURN") || catalog.equals("BELT_TURN")) return "TURN";
        return null;
    }
}
