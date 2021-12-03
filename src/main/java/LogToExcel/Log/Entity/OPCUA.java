package LogToExcel.Log.Entity;

import LogToExcel.Log.Log;
import LogToExcel.Log.LogText;

import java.util.Comparator;
import java.util.regex.Pattern;

public class OPCUA extends Log {
    public OPCUA(String key, String path) {
        super(key, new String[]{"DStart", "DSstop", "包裹长度", "与上个包裹的间隔"}, path);
        super.setData("\\opcua-logger.log");
    }

    @Override
    public LogText getDataByRules(String s) {
        LogText text = new LogText(super.getHeader().length);
        if (s.contains(super.getKey())) {
            for (String number : s.split(",")) {
                if (Pattern.compile("[0-9]*").matcher(number).matches()) {
                    text.addElement(number);
                }
            }
        }
        return text;
    }

    @Override
    public Comparator<LogText> compare() {
        return (o1, o2) -> (int) (Long.parseLong(o1.getList().get(0)) - Long.parseLong(o2.getList().get(0)));
    }
}
