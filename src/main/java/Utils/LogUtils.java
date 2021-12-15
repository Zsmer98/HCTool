package Utils;

import LogToExcel.Log.LogText;

import java.util.HashMap;

public class LogUtils {
    public static LogText addListIntoText(String s, String key, String[] keys, int textlength) {
        LogText logText = new LogText(textlength);
        if (s.contains(key)) {
            HashMap<String, String> res = StringUtils.getValue(keys, s);
            if (res.size() != 0) {
                for (String k : keys) {
                    logText.addElement(res.get(k));
                }
            }
        }

        return logText;
    }
}