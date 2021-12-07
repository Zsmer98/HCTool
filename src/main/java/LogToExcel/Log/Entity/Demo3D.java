package LogToExcel.Log.Entity;

import LogToExcel.Log.Log;
import LogToExcel.Log.LogText;
import LogToExcel.Log.LogUtils;

import java.util.Comparator;

public class Demo3D extends Log {
    public Demo3D(String key, String path) {
        super(key, new String[]{"DStart", "DStop", "包裹长度", "与上个包裹的间隔"}, path);
        super.setData("\\demo3dlog.txt");
    }

    @Override
    public LogText getDataByRules(String s) {
        String[] keys = {"DStart", "DStop"};
        return LogUtils.addListIntoText(s, super.getKey(), keys, super.getHeader().length);
    }

    @Override
    public Comparator<LogText> compare() {
        return Comparator.comparingInt(o -> Integer.parseInt(o.getList().get(0)));
    }
}