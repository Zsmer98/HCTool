package Utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

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

    public static <T> T BFS(T root,
                            Function<T, ? extends Collection<T>> getChildNodes,
                            Predicate<T> predicate) {
        Queue<T> queue = new LinkedList<>();
        queue.add(root);

        while (root != null && !predicate.test(root)) {
            for (T t : getChildNodes.apply(root)) {
                queue.add(t);
            }
            root = queue.poll();
        }

        return root;
    }
}
