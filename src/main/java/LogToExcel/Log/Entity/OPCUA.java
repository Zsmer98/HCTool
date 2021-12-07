package LogToExcel.Log.Entity;

import LogToExcel.Log.Log;
import LogToExcel.Log.LogText;
import LogToExcel.Log.LogUtils;

import java.util.Comparator;

public class OPCUA extends Log {
    public OPCUA(String key, String path) {
        super(key, new String[]{"CStart", "CSstop", "包裹长度", "与上个包裹的间隔"}, path);
        super.setData("\\opcua-logger.log");
    }

    @Override
    public LogText getDataByRules(String s) {
        String[] keys = {"CStart", "CStop"};
        return LogUtils.addListIntoText(s, super.getKey(), keys, super.getHeader().length);
    }

    @Override
    public Comparator<LogText> compare() {
        return (o1, o2) -> (int) (Long.parseLong(o1.getList().get(0)) - Long.parseLong(o2.getList().get(0)));
    }
}
