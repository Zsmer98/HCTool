import LoadGenerator.Load;
import Utils.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;


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
        class T{
            int i = 0;

            @Override
            public boolean equals(Object o){
                return true;
            }

            @Override
            public int hashCode(){
                return new Random().nextInt(10000);
            }
        }
        T[] ts = {new T(), new T(), new T(), new T(), new T()};
        Arrays.stream(ts)
                .distinct()
                .peek(System.out::println)
                .skip(1)
                .forEach(t -> System.out.println(t.i));
        System.out.println(new T().equals(new T()));
    }

    @Test
    public void testFile() throws FileNotFoundException {
        List<String> texts = FileUtils.readFile("C:\\Users\\Zsm\\Desktop\\spring-boot-logger.log");

        texts.forEach(System.out::println);
    }

}
