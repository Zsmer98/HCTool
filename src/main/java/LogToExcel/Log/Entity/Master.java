package LogToExcel.Log.Entity;

import FileUtils.StringUtils;
import LogToExcel.Log.Log;
import LogToExcel.Log.LogText;

import java.util.Comparator;
import java.util.HashMap;

public class Master extends Log {
    public Master(String key, String path) {
        super(key, new String[]{"newStart", "newEnd", "MergeTime", "包裹长度", "与上个包裹的间隔"}, path);
        super.setData("\\master-logger.log");
    }

    @Override
    public LogText getDataByRules(String s) {
        String[] keys = {"newStart", "newEnd", "MergeTime", "MergeTimeStamp"};
        HashMap<String, String> hashMap = StringUtils.getValue(keys, s);

        LogText logText = new LogText(super.getHeader().length);
        if (s.contains(super.getKey())) {
            logText.addElement(String.valueOf(
                    Long.parseLong(hashMap.get(keys[0])) +
                            Long.parseLong((hashMap.get(keys[3])))
            ));
            logText.addElement(String.valueOf(
                    Long.parseLong(hashMap.get(keys[1])) +
                            Long.parseLong((hashMap.get(keys[3])))
            ));
            logText.addElement(hashMap.get(keys[2]));
        }
        return logText;
    }

    @Override
    public Comparator<LogText> compare() {
        return (o1, o2) -> (int)
                (Long.parseLong(o1.getList().get(0)) -
                        Long.parseLong(o2.getList().get(0)));
    }
}
