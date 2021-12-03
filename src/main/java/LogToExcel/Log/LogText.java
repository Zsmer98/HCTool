package LogToExcel;

import java.util.ArrayList;
import java.util.List;

class LogText implements Comparable<LogText> {
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
    public int compareTo(LogText logText) {
        return Integer.parseInt(list.get(0)) - Integer.parseInt(logText.getList().get(0));
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
