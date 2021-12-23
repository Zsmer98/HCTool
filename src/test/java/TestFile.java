import LoadGenerator.Load;
import Utils.FileUtils;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class TestFile {

    @Test
    public void test() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println(ft.format(dNow));
    }

    @Test
    public void test1() {
        Random random = new Random();
        long f7 = random.nextLong((long) Math.pow(10, 13));
        System.out.println(f7);
        System.out.println(Math.floorMod(f7, 2));
    }

    @Test
    public void testCUtils() {
        Load l = new Load(2, 1, 1, 1);
        l.setPNColor();
        System.out.println(l.getColor());
        Load clone = l.clone();
        System.out.println(clone.getColor());
    }

    @Test
    public void testStream() {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8);
        final AtomicInteger j = new AtomicInteger();
        list.stream()
                .peek(i-> System.out.println(i + " " + (j.incrementAndGet())))
                .skip(1)
                .limit(4)
                .forEach(i-> System.out.println("ss"));
    }

    @Test
    public void testFile() throws FileNotFoundException {
        List<String> texts = FileUtils.readFile("C:\\Users\\Zsm\\Desktop\\spring-boot-logger.log");

        texts.forEach(System.out::println);
    }

}
