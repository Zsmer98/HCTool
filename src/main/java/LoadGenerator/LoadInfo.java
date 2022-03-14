package LoadGenerator;

/**
 * 包裹所在的线路编号，包裹的PN码
 */
public class LoadInfo {
    private final int Line;
    private final String Number;
    private final Load load;

    public int getLine() {
        return Line;
    }

    public String getNumber() {
        return Number;
    }

    public Load getLoad() {
        return load;
    }

    @Override
    public String toString() {
        return "HMSET 000101_MN" +
                Line +
                Number +
                "_status LE " + (int) load.getLength() +
                " WI " + (int) load.getWidth() +
                " PO 0";
    }

    public LoadInfo(int Line, String Number, Load load) {
        this.Line = Line;
        this.Number = Number;
        this.load = load;
    }
}


