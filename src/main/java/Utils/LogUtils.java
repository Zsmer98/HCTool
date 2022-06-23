package Utils;

import LogToExcel.Log.LogText;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;

public final class LogUtils {
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

    public static String millsToDate(String mills) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");//将毫秒级long值转换成日期格式
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(Long.parseLong(mills));
        return dateformat.format(gc.getTime());
    }
}