import FileUtils.WriteFile;

import java.text.SimpleDateFormat;
import java.util.*;

public class LoadGenerator {
    private final int Line;
    private final String Number;
    private final Size size;

    @Override
    public String toString() {
        return "HMSET 000101_MN" +
                Line +
                Number +
                "_status LE " + size.getLength() +
                " WI " + size.getWidth() +
                " PO 0";
    }

    public LoadGenerator(int Line, String Number, Size size) {
        this.Line = Line;
        this.Number = Number;
        this.size = size;
    }

    public static void main(String[] args) {
        //每条线上的包裹数量
        int number;

        System.out.println("Please enter the number of packages on each line.");
        try (Scanner sc = new Scanner(System.in)) {
            number = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Incorrect input, please input again");
            return;
        }

        List<LoadGenerator> list = new LinkedList<>();
        SizeFactory factory = new SizeFactory();

        for (int l = 0; l < 4; ++l) {
            for (int i = 0; i < number; ++i) {
                list.add(new LoadGenerator(l + 1, String.format("%05d", i + 1), factory.getRandomSize()));
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        String path = "./Load " + dateFormat.format(new Date()) + ".txt";
        WriteFile.writeFile(path, list);
    }
}

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

class SizeFactory {
    private final List<Size> sizelist;
    private int all;
    private final Random r = new Random();

    public SizeFactory() {
        sizelist = new ArrayList<>();
        //默认从这四个包裹中随机选取一个
        sizelist.add(new Size(800, 600, 3));
        sizelist.add(new Size(500, 500, 2));
        sizelist.add(new Size(300, 200, 2));
        sizelist.add(new Size(200, 200, 1));
        all = (int) sizelist.stream().mapToDouble(Size::getProbability).sum();
    }

    //从sizelist里返回一个随机包裹
    public Size getRandomSize() {
        int random = r.nextInt(all);
        for (Size size : sizelist) {
            random -= size.getProbability();
            if (random < 0) return size;
        }
        return null;
    }

    //增加包裹
    public void addSize(int length, int width, int probability) {
        sizelist.add(new Size(length, width, probability));
        ++all;
    }
}