package LoadGenerator;

class Size {
    private final int length;
    private final int width;
    //随机生成包裹的比率
    private final int probability;

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getProbability() {
        return probability;
    }

    public Size(int length, int width, int probability) {
        this.length = length;
        this.width = width;
        this.probability = probability;
    }
}
