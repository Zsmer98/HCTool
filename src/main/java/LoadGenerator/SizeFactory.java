package LoadGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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