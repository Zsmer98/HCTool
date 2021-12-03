package LogToExcel.Log;

import java.util.ArrayList;
import java.util.List;

public class LogText {
    private final List<String> list;

    public List<String> getList() {
        return list;
    }

    public void addElement(String l) {
        list.add(l);
    }

    public LogText(int size) {
        list = new ArrayList<>(size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String l : list) {
            sb.append(l).append(", ");
        }
        return sb.toString();
    }
}
