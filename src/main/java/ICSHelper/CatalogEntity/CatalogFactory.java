package ICSHelper.CatalogEntity;

import java.util.Map;
import java.util.Objects;

public final class CatalogFactory {
    private CatalogFactory() {
    }

    public static Catalog getCatalogFromExcel(Map<String, String> dataMap) {
        String type = Catalog.getCatalogType(dataMap.get("Catalog"));

        if (Objects.equals(type, "STRAIGHT")) {
            return getStraight(dataMap);
        }

        if (Objects.equals(type, "TURN")) {
            return getTurn(dataMap);
        }

        return null;
    }

    private static Catalog getStraight(Map<String, String> dataMap) {
        return new STRAIGHT(
                dataMap.get("X.Start"), dataMap.get("Y.Start"), dataMap.get("Z.Start"),
                dataMap.get("X.End"), dataMap.get("Y.End"), dataMap.get("Z.End"),
                "0", dataMap.get("有效宽度mm"), dataMap.get("设备编号"), dataMap.get("Catalog")
        );
    }

    private static Catalog getTurn(Map<String, String> dataMap) {
        return new TURN(
                dataMap.get("X.Start"), dataMap.get("Y.Start"), dataMap.get("Z.Start"),
                dataMap.get("X.End"), dataMap.get("Y.End"), dataMap.get("Z.End"),
                dataMap.get("转弯半径"), dataMap.get("Angle"), dataMap.get("有效宽度mm"), dataMap.get("设备编号"), dataMap.get("Catalog")
        );
    }
}
