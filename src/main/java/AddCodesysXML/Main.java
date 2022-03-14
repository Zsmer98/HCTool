package AddCodesysXML;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    static final String[] list = {
            "DS01.BC1002", "DS01.BC1004", "DS01.BV1006", "DS01.BC1008",
            "DS01.BC1010", "DS01.DV1012", "DS01.BC1014", "DS01.BV1016"
    };

    static final String alarm = """
            <route id="%s.Signals">
                    <from uri="{{PLC101_OPC_URI}}?node=RAW(ns=4;s=|var|CODESYS Control for Linux SL.Application.GVL.%s_%s_Signals_SCADA)&amp;samplingInterval=100" />
                    <log message="${body.value.value}" />
                    <setHeader name="CamelRedis.Command">
                        <constant>LPUSH</constant>
                    </setHeader>
                    <setHeader name="CamelRedis.Key">
                        <constant>ALARM_QUEUE</constant>
                    </setHeader>
                    <setHeader name="CamelRedis.Value">
                        <simple>%s.Signals:${date:now:yyyyMMddHHmmss.SSSZ}:${body.value.value}</simple>
                    </setHeader>
                    <to uri="{{REDIS_URI}}" />
                </route>""";

    static final String status = """
            <route id="%s.Status">
                    <from uri="{{PLC101_OPC_URI}}?node=RAW(ns=4;s=|var|CODESYS Control for Linux SL.Application.GVL.%s_%s_Status_SCADA)&amp;samplingInterval=100" />
                    <log message="${body.value.value}" />
                    <setHeader name="CamelRedis.Command">
                        <constant>HSET</constant>
                    </setHeader>
                    <setHeader name="CamelRedis.Key">
                        <constant>%s_status</constant>
                    </setHeader>
                    <setHeader name="CamelRedis.Field">
                        <constant>OldStatus</constant>
                    </setHeader>
                    <setHeader name="CamelRedis.Value">
                        <simple resultType="java.lang.String">${body.value.value}</simple>
                    </setHeader>
                    <to uri="{{REDIS_URI}}" />
            </route>""";

    public static void main(String[] args) {
        final List<String> alarmXML = new LinkedList<>();
        final List<String> statusXML = new LinkedList<>();

        Arrays.stream(list)
                .forEach(s -> {
                    String[] split = s.split("\\.");
                    alarmXML.add(String.format(alarm, s, split[0], split[1], s).replace(" for Linux SL", " Win V3 x64"));
                    statusXML.add(String.format(status, s, split[0], split[1], s).replace(" for Linux SL", " Win V3 x64"));
                });

        Utils.FileUtils.writeFile("C:\\Users\\Zsm\\Desktop\\alarm.txt", alarmXML);
        Utils.FileUtils.writeFile("C:\\Users\\Zsm\\Desktop\\status.txt", statusXML);
    }
}
