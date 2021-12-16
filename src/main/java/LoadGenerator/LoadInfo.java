package LoadGenerator;

public class LoadInfo {
    private final int Line;
    private final String Number;
    private final Load load;

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
                "_status LE " + load.getLength() +
                " WI " + load.getWidth() +
                " PO 0";
    }

    public LoadInfo(int Line, String Number, Load load) {
        this.Line = Line;
        this.Number = Number;
        this.load = load;
    }
}


