package LogToExcel.Log.Entity;
import LogToExcel.Log.Log;
import LogToExcel.Log.LogText;

import java.util.Comparator;
import java.util.regex.Pattern;

public class Demo3D extends Log{
    public Demo3D(String key, String path ) {
        super(key, new String[]{"DStart", "DSstop", "包裹长度", "与上个包裹的间隔"}, path);
        super.setData("\\demo3dlog.txt");
    }

    @Override
    public LogText getDataByRules(String s) {
        int temp = 1;
        LogText text = new LogText(super.getHeader().length);
        if (s.contains(super.getKey())) {
            for (String number : s.split(",")) {
                if ((Pattern.compile("[0-9]*").matcher(number).matches()) && (--temp < 0)) {
                    text.addElement(number);
                }
            }
        }
        return text;
    }

    @Override
    public Comparator<LogText> compare() {
        return Comparator.comparingInt(o -> Integer.parseInt(o.getList().get(0)));
    }
}