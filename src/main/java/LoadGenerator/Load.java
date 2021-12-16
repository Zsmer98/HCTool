package LoadGenerator;

import java.util.Random;

public class Load implements Cloneable {
    private final int length;
    private final int width;
    private final int depth;
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

    public int getDepth() {
        return depth;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getProbability() {
        return probability;
    }

    public void setPNColor() {
        PN = random.nextLong((long) Math.pow(10,13));
        color = Color.values()[Math.floorMod(PN, Color.values().length)];
    }

    public Load(int length, int width, int depth, int probability) {
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
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return l;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
