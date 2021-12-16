package Utils;

import java.util.LinkedList;
import java.util.List;

public class CollectionUtils {
    public static <T> List<T> release(List<List<T>> lists) {
        List<T> list = new LinkedList<>();
        for (List<T> l : lists) {
            list.addAll(l);
        }
        return list;
    }
}
