package Utils;

import java.util.LinkedList;
import java.util.List;

public final class CollectionUtils {
    public static <T> List<T> releaseAll(List<?> lists) {
        List<T> res = new LinkedList<>();
        CollectionUtils.release(lists, res);
        return res;
    }

    @SuppressWarnings("unchecked")
    public static <T> void release(List<?> lists, List<? super T> res) {
        for (Object o : lists) {
            if (o instanceof List) {
                CollectionUtils.release((List<?>) o, res);
            } else {
                res.add((T) o);
            }
        }
    }
}
