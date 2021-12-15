package LoadGenerator;

import Utils.WriteFile;

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

        System.out.println("请输入每条线上的包裹总数：");
        try (Scanner sc = new Scanner(System.in)) {
            number = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("输入格式有误：请重新输入");
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


