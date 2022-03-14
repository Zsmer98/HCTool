package LoadGenerator;

import java.util.Random;

public class Load implements Cloneable {
    private final double length;
    private final double width;
    private final double depth;
    private long PN;
    private Color color;
    //随机生成包裹的比率
    private final int probability;
    private static final Random random = new Random();

    public String getPN() {
        return "PN" + String.format("%013d", PN);
    }

    public Color getColor() {
        return color;
    }

    public double getDepth() {
        return depth;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public int getProbability() {
        return probability;
    }

    public void setPNColor() {
        PN = random.nextLong((long) Math.pow(10,10));
        color = Color.values()[Math.floorMod(PN, Color.values().length)];
        PN += (color.ordinal() + 191) * ((long) Math.pow(10,10));
    }

    public Load(double length, double width, double depth, int probability) {
        this.length = length;
        this.width = width;
        this.depth = depth;
        this.probability = probability;
    }

    @Override
    public Load clone() {
        try {
            Load l = (Load) super.clone();
            l.setPNColor();
            return l;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
