package Utils;

import java.util.HashMap;

public final class StringUtils {

    /**
     * 将要获取的key list放进hashMap中返回
     *
     * @param list 需要读取的key
     * @param text 以","分割，list里面的key后一个String作为value放进map返回
     * @return list里的元素对应的key
     */
    public static HashMap<String, String> getValue(String[] list, String text) {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] strs = text.split(",");
        for (String key : list) {
            for (int i = 0; i < strs.length; ++i) {
                if (key.equals(strs[i])) {
                    hashMap.put(key, strs[i + 1]);
                }
            }
        }
        return hashMap;
    }

    public static void requireNonEmpty(String... args) {
        for (String s : args) {
            if (s == null || s.isEmpty()) throw new IllegalArgumentException("Input contains empty value");
        }
    }
}
