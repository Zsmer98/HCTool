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

    public void resetPNColor() {
        PN = random.nextLong((long) Math.pow(10,10));
        setNewColor();
        PN += (color.ordinal() + 191) * ((long) Math.pow(10,10));
    }

    public void setColor(int loc) {
        color = Color.values()[loc];
    }

    /**
     * 设置指定比例的出口
     */
    public void setNewColor() {
        int random = new Random().nextInt(9);
        random = random > 5 ? 0 : random;

        color = Color.values()[random];
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
            l.resetPNColor();
            return l;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}
