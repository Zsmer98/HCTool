package LogToExcel.Log;

import org.apache.commons.math3.util.Pair;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PE {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public List<Pair<String, String>> getList() {
        return list;
    }

    private final List<Pair<String, String>> list = new LinkedList<>();
    private Comparator<Pair<String, String>> comparator = Comparator.comparingLong(o -> Long.parseLong(o.getFirst()));

    public PE(String name) {
        this.name = name;
    }

    public PE(String name, Comparator<Pair<String, String>> comparator) {
        this.name = name;
        this.comparator = comparator;
    }

    public void add(String val1, String val2) {
        list.add(new Pair<>(val1, val2));
    }

    public void sort() {
        list.sort(comparator);
    }
}
