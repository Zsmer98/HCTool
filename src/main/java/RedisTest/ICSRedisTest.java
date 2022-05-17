package RedisTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ICSRedisTest {
    public static List<String> nodeUnitList = Arrays.asList(
            "LC02.BTL02.BC02", "LC02.BTL02.BC04", "LC02.BTL02.BV06", "LC02.BTL02.BC08", "LC02.BTL02.BC10",
            "LC02.BTL02.BC12", "LC02.BTL02.BV14", "LC02.BTL02.BV16", "LC02.BTL02.BC18", "LC02.BTL02.BC20",
            "LC02.BTL02.BC24", "LC02.BTL02.BC26", "LC02.BTL02.BC28", "LC01.BTL01.BC02", "LC01.BTL01.BC04",
            "LC01.BTL01.BC06", "LC01.BTL01.BV08", "LC01.BTL01.BC10", "LC01.BTL01.BC12", "LC01.BTL01.BC14",
            "LC01.BTL01.BV16", "LC01.BTL01.BV18", "LC01.BTL01.BC20", "LC01.BTL01.BC22", "LC01.BTL01.BC26",
            "LC01.BTL01.BC28", "LC01.BTL01.BC30"
    );

//    public int getStatus(String oldBits) {
//        int[] bits = SCADAUtil.stringToBinary(oldBits, 32);
//
//        if (SCADAUtil.checkArrayValue(bits, 0)) {
//            return 3;
//        } else if (SCADAUtil.checkArrayValue(bits, 1, 4, 22, 27)) {
//            return 4;
//        } else if (SCADAUtil.checkArrayValue(bits, 2)) {
//            return 5;
//        } else if (SCADAUtil.checkArrayValue(bits, 24, 25, 26)) {
//            return 6;
//        } else if (SCADAUtil.checkArrayValue(bits, 7)) {
//            return 7;
//        } else if (SCADAUtil.checkArrayValue(bits, 5)) {
//            return 9;
//        } else if (SCADAUtil.checkArrayValue(bits, 6)) {
//            return 10;
//        } else if (SCADAUtil.checkArrayValue(bits, 8)) {
//            return 11;
//        } else if (SCADAUtil.checkArrayValue(bits, 30)) {
//            return 12;
//        } else {
//            return 1;
//        }
//    }

    public static void main(String[] args) {
        JedisPool pool = new JedisPool("192.168.0.192", 6379);
        Jedis jedis = pool.getResource();
        jedis.auth("test");
        nodeUnitList.forEach(s -> jedis.hset(s + "_status", Map.of("OldStatus", String.valueOf(1 << 30))));
    }
}
