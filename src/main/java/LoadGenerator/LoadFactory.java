package LoadGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class LoadFactory {
    private final List<Load> sizelist;
    private final int all;
    private static final Random r = new Random();

    public LoadFactory() {
        sizelist = new ArrayList<>();
        //默认从这四个包裹中随机选取一个
        sizelist.add(new Load(200, 180, 100, 10));
        sizelist.add(new Load(250, 200, 180, 10));
        sizelist.add(new Load(300, 250, 200, 22));
        sizelist.add(new Load(360, 300, 250, 2));
        sizelist.add(new Load(530, 320, 230, 2));
        sizelist.add(new Load(700, 400, 320, 4));
        sizelist.add(new Load(800, 800, 530, 10));
        all = (int) sizelist.stream().mapToDouble(Load::getProbability).sum();
    }

    //从sizelist里返回一个随机包裹
    public Load getRandomLoad() {
        int random = r.nextInt(all);
        for (Load load : sizelist) {
            random -= load.getProbability();
            if (random < 0) return load.clone();
        }
        return null;
    }
}