import LoadGenerator.Color;
import LoadGenerator.Load;
import Utils.CollectionUtils;
import Utils.StringUtils;
import LogToExcel.Log.Entity.Master;
import LogToExcel.Log.Log;
import org.junit.Test;

import java.util.*;


public class TestFile {

    @Test
    public void test() {

    }

    @Test
    public void test1() {
        Random random = new Random();
        long f7 = random.nextLong((long) Math.pow(10,13));
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
}
